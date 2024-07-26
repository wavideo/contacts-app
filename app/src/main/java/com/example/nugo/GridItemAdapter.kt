package com.example.nugo

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ItemGridBinding
import com.example.nugo.sticker.NewStickerDialogueFragment
import com.example.nugo.sticker.StickerManager

class GridItemAdapter(val iconPickers: MutableList<NewStickerDialogueFragment.IconPicker>, var sellectIconImg: Int) :
    RecyclerView.Adapter<GridItemAdapter.Holder>() {
    class Holder(val binding: ItemGridBinding) : RecyclerView.ViewHolder(binding.root) {
        val gridItem = binding.ivStickerGriditem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return iconPickers.size
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.gridItem.setImageResource(StickerManager.icons[position + 1])

        if (position+1 == sellectIconImg){
            iconPickers[position].isSelected = true
        }

        holder.gridItem.backgroundTintList =
            if (iconPickers[position].isSelected) ColorStateList.valueOf(Color.parseColor("#C7D3F3")) else ColorStateList.valueOf(
                Color.parseColor("#ffffff")
            )

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)

            for (i in iconPickers){
                if(iconPickers[position].index != i.index){
                    i.isSelected = false
                } else {
                    i.isSelected = true
                    sellectIconImg = i.index+1
                }
            }
            notifyDataSetChanged()
        }

    }
}


