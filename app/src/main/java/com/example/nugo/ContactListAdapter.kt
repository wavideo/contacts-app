package com.example.nugo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ActivityContactListItemBinding
import com.example.nugo.databinding.FragmentContactListBinding

class ContactListAdapter(
    private val mItems: MutableList<ContactData>,
) : RecyclerView.Adapter<ContactListAdapter.Holder>() {

    inner class Holder(private val binding: ActivityContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val rvStickerMini = binding.rvStickerMini
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }


    var myStickers = mutableListOf<StickerData>()

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ActivityContactListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvName.text = ContactManager.Contacts[position].name
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

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


        val adapter = ContactListStickerMiniAdapter(StickerManager.stickers)
        holder.rvStickerMini.adapter = adapter
        holder.rvStickerMini.layoutManager = LinearLayoutManager(holder.rvStickerMini.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateData(newItems: MutableList<ContactData>){
        mItems = newItems
        notifyDataSetChanged()
    }

}