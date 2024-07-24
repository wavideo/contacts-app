package com.example.nugo

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ItemContactWithStickerBinding
import com.example.nugo.databinding.ItemStickerForContactListMiniBinding

class ContactListStickerMiniAdapter(val items: MutableList<StickerData>, val parentPosition :Int):RecyclerView.Adapter<ContactListStickerMiniAdapter.Holder>() {
    inner class Holder (val binding : ItemStickerForContactListMiniBinding, parentPosition :Int):RecyclerView.ViewHolder(binding.root){
        val img = binding.ivSticker
        val text = binding.tvSticker
        val box = binding.clBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemStickerForContactListMiniBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding, parentPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.img.setImageResource(StickerManager.stickers[position].findDrawable())

        val myContact = ContactManager.Contacts[parentPosition]
        val mySticker = StickerManager.stickers[position]
        val myStickerNum = when (position){
            0 -> myContact.sticker0
            1 -> myContact.sticker1
            2 -> myContact.sticker2
            3 -> myContact.sticker3
            else -> myContact.sticker4
        }

        fun checkStickerNum(){
            if (myStickerNum != 0 && mySticker.isDelete == false && position != myContact.recentSticker) {
                holder.itemView.visibility = VISIBLE
                holder.box.maxWidth = 10000
            } else {
                holder.itemView.visibility = GONE
                holder.box.maxWidth = 0
            }
        }

        checkStickerNum()
        holder.text.text = myStickerNum.toString()

    }
}