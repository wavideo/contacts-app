package com.example.nugo

import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ItemContactWithStickerBinding
import com.example.nugo.databinding.ItemStickerForContactListMiniBinding

class ContactListStickerMiniAdapter(val items: MutableList<StickerData>, val parentPosition :Int):RecyclerView.Adapter<ContactListStickerMiniAdapter.Holder>() {
    inner class Holder (val binding : ItemStickerForContactListMiniBinding, parentPosition :Int):RecyclerView.ViewHolder(binding.root){
        val img = binding.ivSticker
        val text = binding.tvSticker
        val parentPosition = parentPosition
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
        val myStickerNum = when (position){
            0 -> myContact.sticker0
            1 -> myContact.sticker1
            2 -> myContact.sticker2
            3 -> myContact.sticker3
            else -> myContact.sticker4
        }

        holder.text.text = myStickerNum.toString()

        //        myStickers = StickerManager.stickers.map { it.copy() }.toMutableList()
//        val myContact = ContactManager.Contacts[position]
//        val stickerCount = intArrayOf(myContact.sticker0, myContact.sticker1, myContact.sticker2, myContact.sticker3, myContact.sticker4)
//        for (i in stickerCount.indices){
//            if (stickerCount[i] == 0){
//                myStickers.removeAt(i)
//                notifyDataSetChanged()
//            }
//        }
//        myStickers.removeAt(myContact.recentSticker)
    }
}