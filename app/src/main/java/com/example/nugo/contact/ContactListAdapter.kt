package com.example.nugo.contact

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.R
import com.example.nugo.SharedViewModel
import com.example.nugo.sticker.StickerData
import com.example.nugo.sticker.StickerManager
import com.example.nugo.databinding.ActivityContactListItemBinding

private const val TAG = "ContactListAdapter"

class ContactListAdapter(
    var contacts: MutableList<ContactData>,
    private val viewModel: SharedViewModel,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ContactListAdapter.Holder>() {

    inner class Holder(private val binding: ActivityContactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val rvStickerMini = binding.rvStickerMini
        val ivProfile = binding.ivProFill
        val ivStickerRecently = binding.ivStickerRecently
        val tvStickerRecently = binding.tvStickerRecently

        val ivCall: ImageView = itemView.findViewById(R.id.iv_call)
    }

    interface ItemClick {
        fun onClick(position: Int, contact: ContactData)
    }

    init {
        viewModel.contacts.observe(lifecycleOwner, Observer { newContacts ->
            contacts = newContacts.toMutableList()
            notifyDataSetChanged()
        })

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

        holder.ivCall.setOnClickListener {
            contact.let {
                val number = it.number
                //연락처가 ? = null이 아닐 경우, 연락처의 number를 가져옴
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    //Intent를 생성 ACTION_DIAL로 다이얼을 열게함,apply를 사용해 가독성을 높임.
                    data = Uri.parse("tel:$number")
                }
                holder.itemView.context.startActivity(intent)
                //context를 통해 다이얼 시작
            }
        }


        viewModel.contacts.observe(lifecycleOwner, Observer { NewContacts ->

            val recentIndex = NewContacts[position].recentSticker
            val recentSticker = viewModel.getStickerList()[recentIndex]

            val recentStickerNum = when (recentIndex) {
                0 -> NewContacts[position].sticker0
                1 -> NewContacts[position].sticker1
                2 -> NewContacts[position].sticker2
                3 -> NewContacts[position].sticker3
                else -> NewContacts[position].sticker4
            }
            holder.tvStickerRecently.text = recentStickerNum.toString()

            if (recentStickerNum == 0) {
                holder.ivStickerRecently.setImageResource(StickerManager.icons[0])
                holder.tvStickerRecently.isVisible = false
                holder.ivStickerRecently.setOnClickListener {
                    itemClick2?.onClick(position, contact)
                }
            } else {
                viewModel.stickers.observe(lifecycleOwner, Observer { stickers ->
                    holder.ivStickerRecently.setImageResource(stickers[recentIndex].findResId())
                })
                holder.tvStickerRecently.isVisible = true

                holder.tvStickerRecently.text = recentStickerNum.toString()
                holder.ivStickerRecently.setOnClickListener {
                    when (recentIndex) {
                        0 -> NewContacts[position].sticker0++
                        1 -> NewContacts[position].sticker1++
                        2 -> NewContacts[position].sticker2++
                        3 -> NewContacts[position].sticker3++
                        else -> NewContacts[position].sticker4++
                    }
                    recentStickerClick?.onClick(position, contact)
                    contacts = NewContacts.toMutableList()
                }
            }
        })

        val copyStickers = viewModel.getStickerList().toMutableList()
        val adapter =
            ContactListStickerMiniAdapter(copyStickers, contact, viewModel, lifecycleOwner)
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