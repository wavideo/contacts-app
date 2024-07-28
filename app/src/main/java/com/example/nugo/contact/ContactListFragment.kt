package com.example.nugo.contact

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.AddFriendActivity
import com.example.nugo.EXTRA_NEW_USER_NAME
import com.example.nugo.EXTRA_NEW_USER_NUMBER
import com.example.nugo.EXTRA_NUMBER_LIST
import com.example.nugo.ListAddFirstStickerFragment
import com.example.nugo.R
import com.example.nugo.SharedViewModel
import com.example.nugo.databinding.FragmentContactListBinding
import com.example.nugo.sticker.StickerAdapter

class ContactListFragment : Fragment() {
    private val binding by lazy { FragmentContactListBinding.inflate(layoutInflater) }
    private val contactAdapter by lazy { ContactListAdapter(mutableListOf<ContactData>(), viewModel) }
    private val PICK_IMAGE_REQUEST = 1 // 추가된 부분: 이미지 선택 요청 코드
    private val viewModel by activityViewModels<SharedViewModel>()
    private lateinit var addFriendLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        recycleListView.adapter = contactAdapter
        recycleListView.layoutManager = LinearLayoutManager(requireContext())

        contactAdapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(position: Int, contact: ContactData) {
                val fragmentContactDetail = ContactDetailFragment.newInstance(contact)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragmentContactDetail)
                    .addToBackStack(null)
                    .commit()
            }
        }

        contactAdapter.itemClick2 = object : ContactListAdapter.ItemClick {
            override fun onClick(position: Int, contact: ContactData) {
                val fragmentListAddFirstSticker =
                    ListAddFirstStickerFragment.newInstance(contact)
//                Toast.makeText(binding.root.context, "새 스티커를 추가합니다.", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.cv_popup_container, fragmentListAddFirstSticker)
                    .addToBackStack(null)
                    .commit()
                val resultBundle = bundleOf("dataSend" to "dataSend")
                setFragmentResult("dataSend", resultBundle)
            }
        }

        contactAdapter.recentStickerClick = object : ContactListAdapter.ItemClick {
            override fun onClick(position: Int, contact: ContactData) {
                viewModel.editContactData(position, contact)
            }
        }

        ivMyInfo.setOnClickListener {  // 추가된 부분: 이미지 선택 버튼 클릭 리스너
            openImageChooser()  // 추가된 부분: 이미지 선택 메소드 호출
        }

        addFriendLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val name = data?.getStringExtra(EXTRA_NEW_USER_NAME) ?: return@registerForActivityResult
                val number = data.getStringExtra(EXTRA_NEW_USER_NUMBER) ?: return@registerForActivityResult
                viewModel.addContactData(ContactData(name, number))
            }
        }
        // 연락처 추가 intent
        addFriend.setOnClickListener {
            val intent_addFriend = Intent(requireContext(), AddFriendActivity::class.java).apply {
                val numberList = ArrayList(viewModel.getContactList().map { it.number })
                putStringArrayListExtra(EXTRA_NUMBER_LIST, numberList)
            }
            addFriendLauncher.launch(intent_addFriend)
        }

        var editableText: Boolean = false
        edit.setOnClickListener {
            if (editableText) {
                tvMyName.isEnabled = false
                edit.setImageResource(R.drawable.ic_edit)
            } else {
                tvMyName.isEnabled = true
                edit.setImageResource(R.drawable.ic_done)
            }
            editableText = !editableText
        }
    }

    private fun initViewModel() = with(viewModel) {
        contacts.observe(viewLifecycleOwner) {
            Log.d(TAG, "Contact list is changed. ${it.size}")
            contactAdapter.updateData(it)
            contactAdapter.notifyDataSetChanged()
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

    companion object {
        private const val TAG = "ContactList"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
            }
    }

}