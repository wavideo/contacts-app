package com.example.nugo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.ActivityContactListItemBinding
import com.example.nugo.databinding.FragmentContactListBinding

class ContactListAdapter(
    private val mItems:List<ContactListItem>,
    private val itemClickListener: (ContactListItem, Int) -> Unit
    ) : RecyclerView.Adapter<ContactListAdapter.Holder>() {

        inner class Holder(private val binding: ActivityContactListItemBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: ContactListItem){
                binding.tvName.text = item.tvName
        }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ActivityContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(mItems[position])

    }

    override fun getItemCount(): Int {
        return mItems.size

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


}