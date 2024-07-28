package com.example.nugo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nugo.contact.ContactListFragment
import com.example.nugo.sticker.StickerListFragment
class MainPageradapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    private val NUM_PAGES = 2
    override fun getItemCount(): Int = NUM_PAGES
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { ContactListFragment.newInstance("Page 1","")}
            1 -> { StickerListFragment()
            }
            else -> { ContactListFragment.newInstance("Page 3","")}
        }
    }
}