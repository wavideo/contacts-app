package com.example.nugo.contact

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.R
import com.example.nugo.sticker.StickerData
import com.example.nugo.sticker.StickerManager
import com.example.nugo.databinding.ActivityContactListItemBinding

private const val TAG = "ContactListAdapter"

class ContactListAdapter(
    val contacts: MutableList<ContactData> = mutableListOf<ContactData>()
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
                ContactManager.makeCall(
                    binding.root.context,
                    contact.name
                ) // 해당 연락처의 번호로 통화 (binding.root.context, contact.name을 불러와야한다)
            }

        }
    }

    interface ItemClick {
        fun onClick(position: Int, contact: ContactData)
    }


    var myStickers = mutableListOf<StickerData>()

    var itemClick: ItemClick? = null
    var itemClick2: ItemClick? = null
    var recentStickerClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ActivityContactListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val contact = contacts[position]
        holder.tvName.text = contact.name
        holder.itemView.setOnClickListener {
            itemClick?.onClick(position, contact)
        }

        holder.ivProfile.setImageBitmap(contact.photo)

        val recentIndex = contact.recentSticker
        val recentSticker = StickerManager.stickers[recentIndex]

        val recentStickerNum = when (recentIndex) {
            0 -> contact.sticker0
            1 -> contact.sticker1
            2 -> contact.sticker2
            3 -> contact.sticker3
            else -> contact.sticker4
        }
        holder.tvStickerRecently.text = recentStickerNum.toString()

        if (recentStickerNum == 0) {
            holder.ivStickerRecently.setImageResource(StickerManager.icons[0])
            holder.tvStickerRecently.isVisible = false
            holder.ivStickerRecently.setOnClickListener {
                itemClick2?.onClick(position, contact)
            }
        } else {
            holder.ivStickerRecently.setImageResource(recentSticker.findDrawable())
            holder.tvStickerRecently.isVisible = true
            holder.tvStickerRecently.text = recentStickerNum.toString()
            holder.ivStickerRecently.setOnClickListener {
                Log.d(
                    TAG,
                    "Recent Sticker is clicked. position:$position, current contact:$contact"
                )
                when (recentIndex) {
                    0 -> contact.sticker0++
                    1 -> contact.sticker1++
                    2 -> contact.sticker2++
                    3 -> contact.sticker3++
                    else -> contact.sticker4++
                }
                Log.d(TAG, "Recent Sticker is clicked. changed contact:$contact")
                recentStickerClick?.onClick(position, contact)
            }
        }

        val copyStickers = StickerManager.stickers.toMutableList()
        val adapter = ContactListStickerMiniAdapter(copyStickers, contact)
        holder.rvStickerMini.adapter = adapter
        holder.rvStickerMini.layoutManager =
            LinearLayoutManager(holder.rvStickerMini.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateData(newItems: List<ContactData>) {
        contacts.clear()
        contacts.addAll(newItems)
        notifyDataSetChanged()
    }

}