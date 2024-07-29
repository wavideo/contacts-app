package com.example.nugo.contact

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.SharedViewModel
import com.example.nugo.sticker.StickerDetailFragment
import com.example.nugo.sticker.StickerManager
import com.example.nugo.databinding.ItemContactWithStickerBinding
import com.example.nugo.sticker.StickerData
import com.example.nugo.sticker.StickerDetailFragment.Companion.ContactOfStickers

class ContactOfStickerAdapter(private val items:MutableList<ContactData>, private val viewModel: SharedViewModel):
    RecyclerView.Adapter<ContactOfStickerAdapter.Holder>() {
    inner class Holder(val binding : ItemContactWithStickerBinding) : RecyclerView.ViewHolder(binding.root){
        val tvName = binding.tvName
        val ivProfile = binding.ivProfile
        val ivStickerIcon = binding.ivStickerIcon
        val tvStickerNum = binding.tvStickerNum
        val clStickerBackground = binding.clStickerBackground
        val ivIcClose = binding.ivIcClose
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemContactWithStickerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return StickerDetailFragment.ContactOfStickers.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateData(newData: List<ContactData>) {
        notifyDataSetChanged()
    }

    interface ItemClick {
        fun onClick(view : View, position : Int)
    }

    var itemClick : ItemClick? = null
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.binding.clContactBackground.setOnClickListener{
            itemClick?.onClick(it,position)
        }

        holder.ivIcClose.setOnClickListener{
            when (StickerManager.detailPicker) {
                0 -> StickerDetailFragment.ContactOfStickers[position].sticker0 = 0
                1 -> StickerDetailFragment.ContactOfStickers[position].sticker1 = 0
                2 -> StickerDetailFragment.ContactOfStickers[position].sticker2 = 0
                3 -> StickerDetailFragment.ContactOfStickers[position].sticker3 = 0
                else -> StickerDetailFragment.ContactOfStickers[position].sticker4 = 0
            }
            StickerDetailFragment.ContactOfStickers.removeAt(position)
            viewModel.updateContactList()
            notifyDataSetChanged()
        }

        val myContact = StickerDetailFragment.ContactOfStickers[position]

        holder.clStickerBackground.setOnClickListener{
            holder.tvStickerNum.text = when (StickerManager.detailPicker) {
                0 -> (++StickerDetailFragment.ContactOfStickers[position].sticker0).toString()
                1 -> (++StickerDetailFragment.ContactOfStickers[position].sticker1).toString()
                2 -> (++StickerDetailFragment.ContactOfStickers[position].sticker2).toString()
                3 -> (++StickerDetailFragment.ContactOfStickers[position].sticker3).toString()
                else -> (++StickerDetailFragment.ContactOfStickers[position].sticker4).toString()
            }
            myContact.recentSticker = StickerManager.detailPicker

            viewModel.editContactData(ContactOfStickers[position])
        }




        holder.tvName.text = myContact.name
        holder.ivProfile.setImageBitmap(myContact.photo)
        holder.tvStickerNum.text = myContact.getStickerCount(StickerManager.detailPicker).toString()

        val stickers = viewModel.getStickerList()
        holder.ivStickerIcon.setImageResource(stickers[StickerManager.detailPicker].findResId())
    }

}
fun ContactData.getStickerCount(index:Int):Int{
    return when (index){
        0 -> sticker0
        1 -> sticker1
        2 -> sticker2
        3 -> sticker3
        else -> sticker4
    }
}