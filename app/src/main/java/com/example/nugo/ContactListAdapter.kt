package com.example.nugo

import android.content.ContentValues.TAG
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
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
    private val mItems:MutableList<ContactData>,
    ) : RecyclerView.Adapter<ContactListAdapter.Holder>() {

        inner class Holder(private val binding: ActivityContactListItemBinding): RecyclerView.ViewHolder(binding.root){
        val tvName = binding.tvName
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.i("바인드뷰홀더","onCreateViewHolder정상작동 ")
        val binding = ActivityContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.i("바인드뷰홀더","onBindViewHolder정상작동() ")
    holder.tvName.text = ContactManager.Contacts[position].name
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}