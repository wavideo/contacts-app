package com.example.nugo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.nugo.ContactListFragment
import com.example.nugo.StickerDetailFragment
import com.example.nugo.StickerListFragment
class MainPageradapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    private val NUM_PAGES = 2
    override fun getItemCount(): Int = NUM_PAGES
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> { ContactListFragment.newInstance("Page 1","")}
            1 -> { StickerListFragment()}
            else -> { ContactListFragment.newInstance("Page 3","")}
        }
    }
}