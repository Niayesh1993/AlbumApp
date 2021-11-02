package com.example.photoalbumapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.choco.utils.viewbinding.viewBindings
import com.example.photoalbumapp.R
import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.databinding.FragmentDetailBinding
import com.example.photoalbumapp.utils.ImageLoader
import com.example.photoalbumapp.utils.observeUiState
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBindings()

    private var photo = Photo()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            photo = it.getParcelable(ARG_PARAM1)!!
        }

        with(binding) {
            txtCreatedAt.text = photo?.getDate()

            photo?.getPhoto()?.let { it1 ->
                activity?.let {
                    ImageLoader.loadImageWithCircularCrop(
                        it,
                        it1,
                        imgFullScreen
                    )
                }
            }

            btnClose.setOnClickListener {
                activity?.onBackPressed() }
        }

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: Photo) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}