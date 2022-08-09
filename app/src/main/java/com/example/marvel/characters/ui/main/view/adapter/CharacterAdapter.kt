package com.example.marvel.characters.ui.main.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.characters.data.model.Result
import com.example.marvel.characters.ui.main.view.CharactersOnItemClickListener
import com.example.marvel.core.utils.Constants

class CharacterAdapter(
    private val mContext: Context,
    private val mIsBookmarkActivity: Boolean,
    private val mCharactersOnItemClickListener: CharactersOnItemClickListener
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var mList: List<Result> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = mList[position]
        val thumbnailUrl = result.thumbnail.path + "." + result.thumbnail.extension
        Glide.with(mContext)
            .load(thumbnailUrl.replace(Constants.KEY_HTTP, Constants.KEY_HTTPS))
            .error(R.drawable.image_broken_variant)
            .into(holder.ivCharacter)

        holder.tvCharacterName.text = result.name

        holder.itemView.setOnClickListener {
            mCharactersOnItemClickListener.onItemClicked(result.id)
        }

        if (result.bookmark) {
            holder.ivCharacterBookmark.setImageDrawable(
                AppCompatResources.getDrawable(
                    mContext,
                    R.drawable.cards_heart
                )
            )
        } else {
            holder.ivCharacterBookmark.setImageDrawable(
                AppCompatResources.getDrawable(
                    mContext,
                    R.drawable.cards_heart_outline
                )
            )
        }

        holder.ivCharacterBookmark.setOnClickListener {
            if (!mIsBookmarkActivity) {
                if (!result.bookmark) {
                    holder.ivCharacterBookmark.setImageDrawable(
                        AppCompatResources.getDrawable(
                            mContext,
                            R.drawable.cards_heart
                        )
                    )
                } else {
                    holder.ivCharacterBookmark.setImageDrawable(
                        AppCompatResources.getDrawable(
                            mContext,
                            R.drawable.cards_heart_outline
                        )
                    )
                }
                result.apply {
                    bookmark = !bookmark
                }
            }
            mCharactersOnItemClickListener.updateCharacter(result)
        }
    }

    override fun getItemCount() = mList.size

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    fun passDataToAdapter(mList: List<Result>) {
        this.mList = mList
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val ivCharacter: ImageView = itemView.findViewById(R.id.iv_character)
        val tvCharacterName: TextView = itemView.findViewById(R.id.tv_character_name)
        val ivCharacterBookmark: ImageView = itemView.findViewById(R.id.iv_character_bookmark)
    }
}