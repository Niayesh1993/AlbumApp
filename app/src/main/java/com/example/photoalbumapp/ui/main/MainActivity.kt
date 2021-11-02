package com.example.photoalbumapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.photoalbumapp.databinding.ActivityMainBinding
import com.example.photoalbumapp.ui.adaptor.RecyclerAdapter
import com.example.photoalbumapp.utils.observeUiState
import com.example.photoalbumapp.utils.viewbinding.viewBindings
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photoalbumapp.R
import com.example.photoalbumapp.data.api.stringRes
import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.ui.widget.recyclerview.BaseViewHolder


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()
    private val binding: ActivityMainBinding by viewBindings()
    private val adapter = RecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeUiState(viewModel.uiState)

        adapter.listener = BaseViewHolder.OnItemClickListener { _, item ->
            binding.container.visibility = View.VISIBLE
            showDetailFragment(item)
        }

        with(binding){

            recycler.adapter = adapter
            recycler.itemAnimator = DefaultItemAnimator()
            recycler.layoutManager = GridLayoutManager(this@MainActivity, 3)

            toolbar.apply {
                title.text = R.string.album_page.stringRes()
            }

        }

        with(viewModel) {
            uiState.observe(this@MainActivity, Observer {
                val uiModel = it ?: return@Observer

            })

            products.observe(this@MainActivity, Observer {
               adapter.insertItems(it!!)
            })

            loadPhotos()
        }
    }

    fun showDetailFragment(item: Photo) {
        val detailFragment: DetailFragment
        binding.recycler.visibility = View.GONE
        binding.appbarLayout.visibility = View.GONE


        if (supportFragmentManager.findFragmentByTag(DETAIL_FRAGMENT_TAG) != null) {
            detailFragment = DetailFragment.newInstance(item)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        } else {
            detailFragment = DetailFragment.newInstance(item)
//            val bundle = Bundle()
//            bundle.putParcelable("param1",item)
//            detailFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .add(R.id.container, detailFragment, DETAIL_FRAGMENT_TAG)
                .commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        binding.container.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
        binding.appbarLayout.visibility = View.VISIBLE

    }

    companion object {

        const val DETAIL_FRAGMENT_TAG = "detail"
    }
}