package com.example.photoalbumapp.ui.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoalbumapp.R
import com.example.photoalbumapp.data.model.Photo
import com.example.photoalbumapp.ui.widget.recyclerview.BaseRecyclerAdapter
import com.example.photoalbumapp.ui.widget.recyclerview.BaseViewHolder
import com.example.photoalbumapp.utils.ImageLoader
import kotlinx.android.synthetic.main.item_photo.view.*

class RecyclerAdapter:
    BaseRecyclerAdapter<Photo,
            RecyclerAdapter.ViewHolder,
            BaseViewHolder.OnItemClickListener<Photo>>(){


    override fun viewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.item_photo,
                parent,
                false
            ),
            this
        )
    }

    override fun onBindView(holder: ViewHolder?, position: Int) {
        holder?.bind(data[position])
    }


    class ViewHolder(itemView: View,
                     adapter: RecyclerAdapter,)
        : BaseViewHolder<Photo>(itemView, adapter){



        override fun bind(t: Photo) {

            t.getPhoto()?.let { it1 ->
                ImageLoader.loadImageWithCircularCrop(
                    itemView.context,
                    it1,
                    itemView.item_photo
                )
            }

            itemView.setOnClickListener { adapter!!.listener!!.onItemClick(-1, t) }

        }

    }

}