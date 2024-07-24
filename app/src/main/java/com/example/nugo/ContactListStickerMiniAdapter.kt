package com.example.nugo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ItemContactWithStickerBinding
import com.example.nugo.databinding.ItemStickerForContactListMiniBinding

class ContactListStickerMiniAdapter(val items: MutableList<StickerData>):RecyclerView.Adapter<ContactListStickerMiniAdapter.Holder>() {
    inner class Holder (val binding : ItemStickerForContactListMiniBinding):RecyclerView.ViewHolder(binding.root){
        val img = binding.ivSticker
        val text = binding.tvSticker
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemStickerForContactListMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.img.setImageResource(StickerManager.stickers[position].findDrawable())
    }
}