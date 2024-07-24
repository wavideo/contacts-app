package com.example.nugo

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.ContactManager.makeCall
import com.example.nugo.databinding.ActivityContactListItemBinding
import com.example.nugo.databinding.FragmentContactListBinding

class ContactListAdapter(
    private var contacts: List<ContactData>
) : RecyclerView.Adapter<ContactListAdapter.Holder>() {

    inner class Holder(private val binding: ActivityContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val rvStickerMini = binding.rvStickerMini
        val ivProfile = binding.ivProFill
        val ivStickerRecently = binding.ivStickerRecently
        val tvStickerRecently = binding.tvStickerRecently

        val ivCall: ImageView = itemView.findViewById(R.id.iv_call)

        init {
            ivCall.setOnClickListener {
                // 클릭 시 통화
                val contact = contacts[adapterPosition] // contacts의 연락처 가져오기
                makeCall(
                    binding.root.context,
                    contact.name
                ) // 해당 연락처의 번호로 통화 (binding.root.context, contact.name을 불러와야한다)
            }
        }
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
        holder.tvName.text = contacts[position].name
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.ivProfile.setImageBitmap(contacts[position].photo)

        var recentIndex = contacts[position].recentSticker
        var recentSticker = StickerManager.stickers[recentIndex]

        var recentStickerNum = when (recentIndex){
            0 -> contacts[position].sticker0
            1 -> contacts[position].sticker1
            2 -> contacts[position].sticker2
            3 -> contacts[position].sticker3
            else -> contacts[position].sticker4
        }

        holder.ivStickerRecently.setImageResource(recentSticker.findDrawable())
        holder.tvStickerRecently.text = recentStickerNum.toString()

        val copyStickers = StickerManager.stickers.toMutableList()
        val adapter = ContactListStickerMiniAdapter(copyStickers, position)
        holder.rvStickerMini.adapter = adapter
        holder.rvStickerMini.layoutManager = LinearLayoutManager(holder.rvStickerMini.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateData(newItems: MutableList<ContactData>){
        contacts = newItems
        notifyDataSetChanged()
    }

}