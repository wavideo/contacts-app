package com.example.nugo

/* [ 허민 파트 ]
연락처 리스트에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 ContactData를 통해 연락처 정보를 읽고 씁니다
2. 연락처 상세정보 (ContactDetailFragment)로 선택한 연락처를 넘겨줍니다*/

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.databinding.FragmentContactListBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ContactListFragment : Fragment() {
    private val binding by lazy { FragmentContactListBinding.inflate(layoutInflater) }
    private lateinit var adapter: ContactListAdapter
    private val PICK_IMAGE_REQUEST = 1 // 추가된 부분: 이미지 선택 요청 코드
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactListAdapter(ContactManager.Contacts) // adapter 초기화
        binding.recycleListView.adapter = adapter
        binding.recycleListView.layoutManager = LinearLayoutManager(requireContext())

        binding.ivMyInfo.setOnClickListener {  // 추가된 부분: 이미지 선택 버튼 클릭 리스너
            openImageChooser()  // 추가된 부분: 이미지 선택 메소드 호출
        }

        adapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val dataToSend = position
                val fragmentContactDetail = ContactDetailFragment.newInstance(dataToSend.toString())
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragmentContactDetail)
                    .addToBackStack(null)
                    .commit()
            }
        }

        adapter.itemClick2 = object : ContactListAdapter.ItemClick2 {
            override fun onClick(view: View, position: Int) {
                val dataToSend = position
                val fragmentListAddFirstSticker = ListAddFirstStickerFragment.newInstance(dataToSend.toString())
//                Toast.makeText(binding.root.context, "새 스티커를 추가합니다.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragmentListAddFirstSticker)
                    .addToBackStack(null)
                    .commit()
            }
        }


        // 연락처 추가 intent
        binding.addFriend.setOnClickListener {
            val intent_addFriend = Intent(requireContext(), AddFriendActivity::class.java)
            startActivity(intent_addFriend)
        }

        var editableText:Boolean = false
        binding.edit.setOnClickListener{
            if (editableText){
                binding.tvMyName.isEnabled = false
                binding.edit.setImageResource(R.drawable.ic_edit)
            } else {
                binding.tvMyName.isEnabled = true
                binding.edit.setImageResource(R.drawable.ic_done)
            }
            editableText = !editableText
        }
    }

    private fun openImageChooser() {  // 멤버 함수로 정의
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            binding.ivMyInfo.setImageURI(imageUri)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.updateData(ContactManager.Contacts)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
