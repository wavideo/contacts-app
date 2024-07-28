package com.example.nugo.sticker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.R
import com.example.nugo.SharedViewModel
import com.example.nugo.contact.ContactData
import com.example.nugo.databinding.ItemStickerBinding

class StickerAdapter(private var items: List<StickerData>, private val contacts: List<ContactData>, private val viewModel : SharedViewModel) :
    RecyclerView.Adapter<StickerAdapter.Holder>() {

    inner class Holder(val binding: ItemStickerBinding) : RecyclerView.ViewHolder(binding.root) {
        val ivStickerIcon = binding.ivStickerIcon
        val tvStickerName = binding.tvStickerName
        val tvContactSizeNum = binding.tvContactSizeNum
        val clContactSize = binding.clContactSize
        val clStickerBackground = binding.clStickerBackground
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var binding = ItemStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // itemClick.onClick 추상메서드 선언
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val mySticker = viewModel.getStickerList()[position]

        // itemClick.onClick 추상메서드 사용
        holder.clStickerBackground.setOnClickListener() {
            itemClick?.onClick(it, position)
        }

        fun contactSize(index:Int):Int = when (index) {
            0 -> (viewModel.getContactList().filter{ it.sticker0 != 0 }).size
            1 -> (viewModel.getContactList().filter{  it.sticker1 != 0 }).size
            2 -> (viewModel.getContactList().filter{  it.sticker2 != 0 }).size
            3 -> (viewModel.getContactList().filter{  it.sticker3 != 0 }).size
            else -> (viewModel.getContactList().filter{  it.sticker4 != 0 }).size
        }
        viewModel.getStickerList()[position].contactSize = contactSize(position)
        viewModel.editStickerDataByIndex(position, viewModel.getStickerList()[position])

        holder.ivStickerIcon.setImageResource(viewModel.getStickerList()[position].findDrawable())
        holder.tvStickerName.text = viewModel.getStickerList()[position].name
        holder.tvContactSizeNum.text = viewModel.getStickerList()[position].contactSize.toString()

        if (viewModel.findStickerDataByIndex(position)?.isDelete == true) {
            holder.clContactSize.isVisible = false
            holder.clStickerBackground.backgroundTintList = ContextCompat.getColorStateList(
                holder.itemView.getContext(),
                R.color.background_basic
            );

        } else {
            holder.clContactSize.isVisible = true
            holder.clStickerBackground.backgroundTintList = ContextCompat.getColorStateList(
                holder.itemView.getContext(),
                R.color.white
            );
        }

    }

    fun updateData(newItems: List<StickerData>) {
        items = newItems
        notifyDataSetChanged()
    }

}