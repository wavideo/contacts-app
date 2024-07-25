package com.example.nugo

/* [ 허민 파트 ]
연락처 리스트(ContactListFragment)와 스티커 리스트(StickerListFragment)를 하단탭 레이아웃으로 연결합니다 */

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.nugo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fragment1Btn.setOnClickListener{
                setFragment(ContactListFragment())
            }
            fragment2Btn.setOnClickListener {
                setFragment(StickerListFragment())
            }
        }
        setFragment(ContactListFragment())
        StickerManager.loading()

        ContactManager.loading()
    }

    private fun setFragment(frag : Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    fun getBinding():ActivityMainBinding{
        return binding
    }

}