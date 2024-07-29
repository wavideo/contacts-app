package com.example.nugo.sticker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.R
import com.example.nugo.databinding.ItemStickerBinding

class StickerAdapter( private var stickers: List<StickerData>) :
    RecyclerView.Adapter<StickerAdapter.Holder>() {

    inner class Holder(val binding: ItemStickerBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivStickerIcon = binding.ivStickerIcon
        val tvStickerName = binding.tvStickerName
        val tvContactSizeNum = binding.tvContactSizeNum
        val clContactSize = binding.clContactSize
        val clStickerBackground = binding.clStickerBackground
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return stickers.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // itemClick.onClick 추상메서드 선언
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: Holder, position: Int) {

        // itemClick.onClick 추상메서드 사용
        with(holder) {
            val mySticker = stickers[position]
            clStickerBackground.setOnClickListener() {
                itemClick?.onClick(it, position)
            }
            ivStickerIcon.setImageResource(mySticker.findResId())
            tvStickerName.text = mySticker.name
            tvContactSizeNum.text = mySticker.contactSize.toString()

            clContactSize.isVisible = !mySticker.isDelete
            clStickerBackground.backgroundTintList = ContextCompat.getColorStateList(
                itemView.context,
                if (mySticker.isDelete) R.color.background_basic else R.color.white
            )
        }


    }

    fun updateData(newItems: List<StickerData>) {
        stickers = newItems
        notifyDataSetChanged()
    }

}