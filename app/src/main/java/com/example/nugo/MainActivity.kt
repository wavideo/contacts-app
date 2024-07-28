package com.example.nugo //메인
/* [ 허민 파트 ]
연락처 리스트(ContactListFragment)와 스티커 리스트(StickerListFragment)를 하단탭 레이아웃으로 연결합니다 */
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.nugo.contact.ContactManager
import com.example.nugo.databinding.ActivityMainBinding
import com.example.nugo.sticker.StickerManager
import com.google.android.material.tabs.TabLayoutMediator
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // 뷰모델
    private val viewModel: SharedViewModel by viewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Log.i ("포지션","포지션 $position")
                    if (position == 0){

                    } else {

                    }
                }

            })
        }
    }
}