package com.example.nugo.contact

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.AddFriendActivity
import com.example.nugo.ListAddFirstStickerFragment
import com.example.nugo.R
import com.example.nugo.SharedViewModel
import com.example.nugo.databinding.FragmentContactListBinding
import kotlin.random.Random

class ContactListFragment : Fragment() {
    private val binding by lazy { FragmentContactListBinding.inflate(layoutInflater) }
    private lateinit var adapter: ContactListAdapter
    private val PICK_IMAGE_REQUEST = 1 // 추가된 부분: 이미지 선택 요청 코드
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ContactListAdapter(ContactManager.contacts) // adapter 초기화
        binding.recycleListView.adapter = adapter
        binding.recycleListView.layoutManager = LinearLayoutManager(requireContext())

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
                val fragmentListAddFirstSticker =
                    ListAddFirstStickerFragment.newInstance(dataToSend.toString())
//                Toast.makeText(binding.root.context, "새 스티커를 추가합니다.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.cv_popup_container, fragmentListAddFirstSticker)
                    .addToBackStack(null)
                    .commit()
                val resultBundle = bundleOf("dataSend" to "dataSend")
                setFragmentResult("dataSend", resultBundle)
            }
        }

        setFragmentResultListener("dataSend") { key, bundle ->
            adapter.updateData(ContactManager.contacts)
        }

        binding.ivMyInfo.setOnClickListener {  // 추가된 부분: 이미지 선택 버튼 클릭 리스너
            openImageChooser()  // 추가된 부분: 이미지 선택 메소드 호출
        }




        // 연락처 추가 intent
        binding.addFriend.setOnClickListener {
            val intent_addFriend = Intent(requireContext(), AddFriendActivity::class.java)
            startActivity(intent_addFriend)
        }


        binding.edit.setOnClickListener{
            viewModel.setCount( Random.nextInt() )
        }

        viewModel.count.observe(viewLifecycleOwner){
            binding.tvMyName.setText(it.toString())
        }

//        var editableText:Boolean = false
//        binding.edit.setOnClickListener{
//            if (editableText){
//                binding.tvMyName.isEnabled = false
//                binding.edit.setImageResource(R.drawable.ic_edit)
//            } else {
//                binding.tvMyName.isEnabled = true
//                binding.edit.setImageResource(R.drawable.ic_done)
//            }
//            editableText = !editableText
//        }
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
        adapter.updateData(ContactManager.contacts)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
            }
    }
}