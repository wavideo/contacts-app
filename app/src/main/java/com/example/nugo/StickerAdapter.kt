package com.example.nugo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ItemStickerBinding

class StickerAdapter (val items:MutableList<StickerData>) :RecyclerView.Adapter<StickerAdapter.Holder>(){
        inner class Holder (val binding: ItemStickerBinding):RecyclerView.ViewHolder(binding.root){
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
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // itemClick.onClick 추상메서드 선언
    interface ItemClick {
        fun onClick(view : View, position : Int)
    }
    var itemClick : ItemClick? = null

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val mySticker = StickerManager.stickers[position]

        // itemClick.onClick 추상메서드 사용
        holder.clStickerBackground.setOnClickListener() {
            itemClick?.onClick(it, position)
        }

        holder.ivStickerIcon.setImageResource(mySticker.findDrawable())
        holder.tvStickerName.text = mySticker.name

        if (mySticker.isDelete == true) {
            holder.clContactSize.isVisible = false
            holder.clStickerBackground.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.background_basic);

        } else {
            holder.clContactSize.isVisible = true
            holder.clStickerBackground.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.getContext(), R.color.white);
            holder.tvContactSizeNum.text = mySticker.contactSize.toString()
        }
    }

}