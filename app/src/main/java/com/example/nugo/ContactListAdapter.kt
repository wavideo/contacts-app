package com.example.nugo

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.ContactManager.makeCall
import com.example.nugo.databinding.ActivityContactListItemBinding
import com.example.nugo.databinding.FragmentContactListBinding

class ContactListAdapter(
    private var Contacts: List<ContactData>,
    private val mItems:MutableList<ContactData>,
    ) : RecyclerView.Adapter<ContactListAdapter.Holder>() {

        inner class Holder(private val binding: ActivityContactListItemBinding): RecyclerView.ViewHolder(binding.root){
            val tvName = binding.tvName
            val ivCall: ImageView = itemView.findViewById(R.id.iv_call)

            init {
                ivCall.setOnClickListener {
                    // 클릭 시 통화
                    val contact = Contacts[adapterPosition] // Contacts의 연락처 가져오기
                    makeCall(binding.root.context, contact.name) // 해당 연락처의 번호로 통화 (binding.root.context, contact.name을 불러와야한다)
                }
            }
        }


    interface ItemClick{
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ActivityContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
    holder.tvName.text = ContactManager.Contacts[position].name
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