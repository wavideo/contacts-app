package com.example.nugo

/* [ 정호정 파트 ]
연락처 상세정보에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 ContactData를 통해 연락처 정보를 읽고 씁니다
2. 연락처 리스트 (ContactListFragment)와 스티커 디테일 (StickerDetailFragment)에서 선택한 연락처를 넘겨받습니다*/

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.nugo.databinding.FragmentContactDetailBinding
import com.example.nugo.databinding.FragmentStickerDetailBinding

private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class ContactDetailFragment : Fragment() {
    private var param1: String? = null
//    private var param2: String? = null

    private val binding by lazy { FragmentContactDetailBinding.inflate(layoutInflater) }

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // 선택한 이미지를 ImageView에 저장

                val bitmap = uriToBitmap(it)
                binding?.ivDetailProfile?.setImageBitmap(bitmap)
                ContactManager.Contacts[param1!!.toInt()].photo = bitmap
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object {
        fun newInstance(param1: String) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = param1?.toInt()!!
        val myContact = ContactManager.Contacts[position]

        binding.etDetailName.setText(myContact.name)
        binding.tvDetailName.setText(myContact.name)
        binding.etDetailNumber.setText(myContact.number)
        binding.etDetailEmail.setText(myContact.email)
        binding.ivDetailProfile.setImageBitmap(myContact.photo)




        fun updateStickerNum(index: Int) {

            when (index) {
                0 -> {
                    binding.ivDetailSticker1.setImageResource(StickerManager.stickers[0].findDrawable())
                    binding.tvDetailSticker1Name.setText(StickerManager.stickers[0].name)
                }

                1 -> {
                    binding.ivDetailSticker2.setImageResource(StickerManager.stickers[1].findDrawable())
                    binding.tvDetailSticker2Name.setText(StickerManager.stickers[1].name)
                }

                2 -> {
                    binding.ivDetailSticker3.setImageResource(StickerManager.stickers[2].findDrawable())
                    binding.tvDetailSticker3Name.setText(StickerManager.stickers[2].name)
                }

                3 -> {
                    binding.ivDetailSticker4.setImageResource(StickerManager.stickers[3].findDrawable())
                    binding.tvDetailSticker4Name.setText(StickerManager.stickers[3].name)
                }

                else -> {
                    binding.ivDetailSticker5.setImageResource(StickerManager.stickers[4].findDrawable())
                    binding.tvDetailSticker5Name.setText(StickerManager.stickers[4].name)
                }
            }

            val count = when (index) {
                0 -> binding.ivDetailSticker1Count
                1 -> binding.ivDetailSticker2Count
                2 -> binding.ivDetailSticker3Count
                3 -> binding.ivDetailSticker4Count
                else -> binding.ivDetailSticker5Count
            }
            val num: Int = when (index) {
                0 -> myContact.sticker0
                1 -> myContact.sticker1
                2 -> myContact.sticker2
                3 -> myContact.sticker3
                else -> myContact.sticker4
            }
            val sticker = when (index) {
                0 -> binding.ivDetailSticker1
                1 -> binding.ivDetailSticker2
                2 -> binding.ivDetailSticker3
                3 -> binding.ivDetailSticker4
                else -> binding.ivDetailSticker5
            }

            count.setText(num.toString())
            if (num == 0) {
                sticker.alpha = 0.2F
                count.isVisible = false
            } else {
                sticker.alpha = 1F
                count.isVisible = true
            }
        }

        for (i in 0..4) {
            updateStickerNum(i)
        }

        binding.ivDetailSticker1.setOnClickListener {
            val mySticker = StickerManager.stickers[0]
            if (mySticker.isDelete == true) {
                mySticker.name = "수정테스트"
                mySticker.icon = 3
                mySticker.isDelete = false
                myContact.sticker0++
                myContact.recentSticker = 0
                updateStickerNum(0)
            } else {
                myContact.sticker0++
                myContact.recentSticker = 0
                updateStickerNum(0)
            }
        }

        binding.ivDetailSticker2.setOnClickListener {
            val mySticker = StickerManager.stickers[1]
            if (mySticker.isDelete == true) {
                mySticker.name = "수정테스트"
                mySticker.icon = 33
                mySticker.isDelete = false
                myContact.sticker1++
                myContact.recentSticker = 1
                updateStickerNum(1)
            } else {
                myContact.sticker1++
                myContact.recentSticker = 1
                updateStickerNum(1)
            }
        }

        binding.ivDetailSticker3.setOnClickListener {
            val mySticker = StickerManager.stickers[2]
            if (mySticker.isDelete == true) {
                mySticker.name = "수정테스트"
                mySticker.icon = 33
                mySticker.isDelete = false
                myContact.sticker2++
                myContact.recentSticker = 2
                updateStickerNum(2)
            } else {
                myContact.sticker2++
                myContact.recentSticker = 2
                updateStickerNum(2)
            }
        }

        binding.ivDetailSticker4.setOnClickListener {
            val mySticker = StickerManager.stickers[3]
            if (mySticker.isDelete == true) {
                mySticker.name = "수정테스트"
                mySticker.icon = 33
                mySticker.isDelete = false
                myContact.sticker3++
                myContact.recentSticker = 3
                updateStickerNum(3)
            } else {
                myContact.sticker3++
                myContact.recentSticker = 3
                updateStickerNum(3)
            }
        }

        binding.ivDetailSticker5.setOnClickListener {
            val mySticker = StickerManager.stickers[4]
            if (mySticker.isDelete == true) {
                mySticker.name = "수정테스트"
                mySticker.icon = 33
                mySticker.isDelete = false
                myContact.sticker4++
                myContact.recentSticker = 4
                updateStickerNum(4)
            } else {
                myContact.sticker4++
                myContact.recentSticker = 4
                updateStickerNum(4)
            }
        }

        binding.ivDetailProfile.setImageBitmap(ContactManager.Contacts[position].photo)

        // 통화 버튼
        binding.btnDetailCall.setOnClickListener {
            ContactManager.makeCall(this.requireContext(), ContactManager.Contacts[position].name)
        }

        // 메시지 버튼
        binding.btnDetailMessage.setOnClickListener {
            ContactManager.makeSMS(this.requireContext(), ContactManager.Contacts[position].name)
        }

        // 뒤로가기 버튼
        binding?.ivDetailBack?.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        var isEditClicked = false


        // 수정 버튼 누르면 저장 버튼, 사진 추가 버튼, 사진 삭제 버튼, 각 EditText 활성화
        binding.ivDetailEdit.setOnClickListener {
            isEditClicked = true

            binding.ivDetailEdit.visibility = View.GONE
            binding.ivDetailSave.visibility = View.VISIBLE

//            binding.ivDetailPhotoDelete.visibility = View.VISIBLE
            binding.ivDetailPhotoEdit.visibility = View.VISIBLE

            binding.etDetailName.isEnabled = true
            binding.etDetailNumber.isEnabled = true
            binding.etDetailEmail.isEnabled = true

            // 사진 삭제 버튼
//            binding.ivDetailPhotoDelete.setOnClickListener {
//                AlertDialog.Builder(this.requireContext())
//                    .setMessage("사진을 삭제하시겠습니까?")
//                    .setPositiveButton("확인") { dialog, which ->
//                        binding.ivDetailProfile.setImageBitmap(sampleBitmap)
//                        ContactManager.Contacts[position].photo = sampleBitmap
//                        binding.ivDetailAvatar.visibility = View.VISIBLE
//                    }
//                    .setNegativeButton("취소") { dialog, which ->
//                    }
//                    .show()
//            }

            // 사진 추가 버튼
            binding.ivDetailPhotoEdit.setOnClickListener {
                binding.ivDetailAvatar.visibility = View.GONE
                Toast.makeText(this.requireContext(), "갤러리에서 사진을 선택해주세요.", Toast.LENGTH_SHORT)
                    .show()
                getImageFromGallery.launch("image/*")
            }

            binding.ivDetailSave.setOnClickListener {
                ContactManager.Contacts[position].name = binding.etDetailName.text.toString()
                binding.tvDetailName.setText(myContact.name)
                ContactManager.Contacts[position].number = binding.etDetailNumber.text.toString()
                ContactManager.Contacts[position].email = binding.etDetailEmail.text.toString()
//                ContactManager.Contacts[position].photo = binding.ivDetailProfile.imageAlpha

                Toast.makeText(this.requireContext(), "수정사항이 저장되었습니다.", Toast.LENGTH_SHORT).show()

                isEditClicked = false
                binding.ivDetailEdit.visibility = View.VISIBLE
                binding.ivDetailSave.visibility = View.GONE
//                binding.ivDetailPhotoDelete.visibility = View.GONE
                binding.ivDetailPhotoEdit.visibility = View.GONE

                binding.etDetailName.isEnabled = false
                binding.etDetailNumber.isEnabled = false
                binding.etDetailEmail.isEnabled = false
            }
        }

        // 스티커 클릭 시 +1, 롱클릭 시 -1
        // 삭제하기 버튼
//        binding.btnDetailDelete.setOnClickListener {
//            AlertDialog.Builder(this.requireContext())
//                .setMessage("연락처를 삭제하시겠습니까?")
//                .setPositiveButton("확인") { dialog, which ->
//                    ContactManager.delete(ContactManager.Contacts[position].name.toString())
//                    requireActivity().supportFragmentManager.beginTransaction()
//                        .replace(R.id.frameLayout, ContactListFragment())
//                        .addToBackStack(null)
//                        .commit()
//                }
//                .setNegativeButton("취소") { dialog, which ->
//                }
//                .show()
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        }
    }

}