package com.example.nugo.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.SharedViewModel
import com.example.nugo.sticker.StickerData
import com.example.nugo.sticker.StickerManager
import com.example.nugo.databinding.ItemStickerForContactListMiniBinding

class ContactListStickerMiniAdapter(
    private val items: MutableList<StickerData>,
    private val contactData: ContactData, private val viewModel: SharedViewModel,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<ContactListStickerMiniAdapter.Holder>() {
    inner class Holder(val binding: ItemStickerForContactListMiniBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.ivSticker
        val text = binding.tvSticker
        val box = binding.clBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemStickerForContactListMiniBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        viewModel.stickers.observe( lifecycleOwner, Observer { stickers ->
            holder.img.setImageResource(stickers[position].findDrawable())
        })

        val myContact = contactData
        val mySticker = viewModel.getStickerList()[position]
        val myStickerNum = when (position) {
            0 -> myContact.sticker0
            1 -> myContact.sticker1
            2 -> myContact.sticker2
            3 -> myContact.sticker3
            else -> myContact.sticker4
        }

        fun checkStickerNum() {
            if (myStickerNum != 0 && mySticker.isDelete == false && position != myContact.recentSticker) {
                holder.itemView.visibility = View.VISIBLE
                holder.box.maxWidth = 10000
            } else {
                holder.itemView.visibility = View.GONE
                holder.box.maxWidth = 0
            }
        }

        checkStickerNum()
        holder.text.text = myStickerNum.toString()

    }
}