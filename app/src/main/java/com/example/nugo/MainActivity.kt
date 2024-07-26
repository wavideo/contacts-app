package com.example.nugo //메인
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
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.nugo.MainPageradapter
import com.example.nugo.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StickerManager.loading()
        ContactManager.loading()
        binding.viewpager.apply {
            adapter = MainPageradapter(context as FragmentActivity)
            TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
                tab.text = when (position) {
                    0 -> "연락처" // 첫 번째 탭 텍스트
                    1 -> "스티커" // 두 번째 탭 텍스트
                    else -> null
                }
                when (position) {
                    0 -> tab.setIcon(R.drawable.ic_launcher_background)
                    1 -> tab.setIcon(R.drawable.ic_launcher_background)
                    2 -> tab.setIcon(R.drawable.ic_launcher_background)
                }
            }.attach()
        }
    }
}