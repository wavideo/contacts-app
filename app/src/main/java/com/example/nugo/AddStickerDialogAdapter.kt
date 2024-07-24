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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.FragmentAddStickerDialogBinding
import com.example.nugo.databinding.ItemAddStickerRecyclerviewBinding

class AddStickerDialogAdapter (
    private var mItems:MutableList<StickerData>,
    ) : RecyclerView.Adapter<AddStickerDialogAdapter.Holder>() {
        inner class Holder(private val binding: ItemAddStickerRecyclerviewBinding ): RecyclerView.ViewHolder(binding.root){
            val stickerImage = binding.ivDetailAddsticker
            val stickerName = binding.tvDetailAddsticker
        }

        interface ItemClick{
            fun onClick(view: View, position: Int)
        }

        var itemClick: ItemClick? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val binding = ItemAddStickerRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
            return Holder(binding)
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            StickerManager.icons[position]
            holder.stickerImage.setImageResource(StickerManager.icons[position])
            holder.stickerName.text = StickerManager.stickers[position].name
            holder.itemView.setOnClickListener{
                itemClick?.onClick(it, position)
            }
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }