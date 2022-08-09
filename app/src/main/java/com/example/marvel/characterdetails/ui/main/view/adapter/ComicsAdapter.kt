package com.example.marvel.characterdetails.ui.main.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.characterdetails.data.model.ComicsResult
import com.example.marvel.core.utils.Constants

class ComicsAdapter : RecyclerView.Adapter<ComicsAdapter.ViewHolder>() {

    private var mList: List<ComicsResult> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comics, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = mList[position]

        var thumbnailUrl = result.thumbnail.path + "." + result.thumbnail.extension
        thumbnailUrl = thumbnailUrl.replace(Constants.KEY_HTTP, Constants.KEY_HTTPS)

        Glide.with(holder.itemView.context)
            .load(thumbnailUrl)
            .error(R.drawable.image_broken_variant)
            .into(holder.imageView)
    }

    override fun getItemCount() = mList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_comics)
    }

    fun passDataToAdapter(mList: List<ComicsResult>) {
        this.mList = mList
        notifyDataSetChanged()
    }
}