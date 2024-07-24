package com.example.nugo

/* [ 정호정 파트 ]
연락처 상세정보에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 ContactData를 통해 연락처 정보를 읽고 씁니다
2. 연락처 리스트 (ContactListFragment)와 스티커 디테일 (StickerDetailFragment)에서 선택한 연락처를 넘겨받습니다*/

import android.net.Uri
import android.os.Bundle
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

    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri: Uri? -> uri?.let {
            // 선택한 이미지를 ImageView에 저장
            binding.ivDetailProfile.setImageURI(it)
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
        binding.ivDetailProfile.setImageResource(ContactManager.Contacts[position].photo)
        binding.etDetailName.setText(ContactManager.Contacts[position].name)
        binding.etDetailNumber.setText(ContactManager.Contacts[position].number)
        binding.etDetailEmail.setText(ContactManager.Contacts[position].email)

        binding.ivDetailSticker1.setImageResource(StickerManager.stickers[0].findDrawable())
        binding.ivDetailSticker2.setImageResource(StickerManager.stickers[1].findDrawable())
        binding.ivDetailSticker3.setImageResource(StickerManager.stickers[2].findDrawable())
        binding.ivDetailSticker4.setImageResource(StickerManager.stickers[3].findDrawable())
        binding.ivDetailSticker5.setImageResource(StickerManager.stickers[4].findDrawable())

        binding.ivDetailSticker1Count.setText(ContactManager.Contacts[position].sticker0.toString())
        binding.ivDetailSticker2Count.setText(ContactManager.Contacts[position].sticker1.toString())
        binding.ivDetailSticker3Count.setText(ContactManager.Contacts[position].sticker2.toString())
        binding.ivDetailSticker4Count.setText(ContactManager.Contacts[position].sticker3.toString())
        binding.ivDetailSticker5Count.setText(ContactManager.Contacts[position].sticker4.toString())

        binding.tvDetailSticker1Name.setText(StickerManager.stickers[0].name)
        binding.tvDetailSticker2Name.setText(StickerManager.stickers[1].name)
        binding.tvDetailSticker3Name.setText(StickerManager.stickers[2].name)
        binding.tvDetailSticker4Name.setText(StickerManager.stickers[3].name)
        binding.tvDetailSticker5Name.setText(StickerManager.stickers[4].name)

        if (ContactManager.Contacts[position].sticker0 == 0){
            binding.ivDetailSticker1.alpha = 0.2F
            binding.ivDetailSticker1Count.isVisible = false
        } else {
            binding.ivDetailSticker1.alpha = 1F
            binding.ivDetailSticker1Count.isVisible = true
        }

        if (ContactManager.Contacts[position].sticker1 == 0){
            binding.ivDetailSticker2.alpha = 0.2F
            binding.ivDetailSticker2Count.isVisible = false
        } else {
            binding.ivDetailSticker2.alpha = 1F
            binding.ivDetailSticker2Count.isVisible = true
        }

        if (ContactManager.Contacts[position].sticker2 == 0){
            binding.ivDetailSticker3.alpha = 0.2F
            binding.ivDetailSticker3Count.isVisible = false
        } else {
            binding.ivDetailSticker3.alpha = 1F
            binding.ivDetailSticker3Count.isVisible = true
        }

        if (ContactManager.Contacts[position].sticker3 == 0){
            binding.ivDetailSticker4.alpha = 0.2F
            binding.ivDetailSticker4Count.isVisible = false
        } else {
            binding.ivDetailSticker4.alpha = 1F
            binding.ivDetailSticker4Count.isVisible = true
        }
        if (ContactManager.Contacts[position].sticker4 == 0){
            binding.ivDetailSticker5.alpha = 0.2F
            binding.ivDetailSticker5Count.isVisible = false
        } else {
            binding.ivDetailSticker5.alpha = 1F
            binding.ivDetailSticker5Count.isVisible = true
        }

        binding.ivDetailProfile.setImageResource(ContactManager.Contacts[position].photo)

        // 뒤로가기 버튼
        binding.ivDetailBack.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        var isEditClicked = false

        // 수정 버튼 누르면 저장 버튼, EditText 활성화
        binding.ivDetailEdit.setOnClickListener{
            isEditClicked = true
            binding.ivDetailSave.visibility = View.VISIBLE
            binding.ivDetailEdit.visibility = View.GONE
            binding.ivDetailAddSticker.visibility = View.VISIBLE

            binding.etDetailName.isEnabled = true
            binding.etDetailNumber.isEnabled = true
            binding.etDetailEmail.isEnabled = true

            // 사진 추가 버튼
            binding.ivDetailAddSticker.setOnClickListener{
                Toast.makeText(this.requireContext(), "갤러리에서 사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
                getImageFromGallery.launch("image/*")
            }

            binding.ivDetailSave.setOnClickListener{
                ContactManager.Contacts[position].name = binding.etDetailName.text.toString()
                ContactManager.Contacts[position].number = binding.etDetailNumber.text.toString()
                ContactManager.Contacts[position].email = binding.etDetailEmail.text.toString()
//                ContactManager.Contacts[position].photo = binding.ivDetailProfile.imageAlpha

                Toast.makeText(this.requireContext(), "수정사항이 저장되었습니다.", Toast.LENGTH_SHORT).show()

                isEditClicked = false
                binding.ivDetailSave.visibility = View.GONE
                binding.ivDetailEdit.visibility = View.VISIBLE
                binding.ivDetailAddSticker.visibility = View.GONE

                binding.etDetailName.isEnabled = false
                binding.etDetailNumber.isEnabled = false
                binding.etDetailEmail.isEnabled = false
            }
        }

        // 삭제하기 버튼
        binding.btnDetailDelete.setOnClickListener{
            AlertDialog.Builder(this.requireContext())
                .setMessage("연락처를 삭제하시겠습니까?")
                .setPositiveButton("확인") { dialog, which ->
                    ContactManager.delete(ContactManager.Contacts[position].name.toString())
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ContactListFragment())
                        .addToBackStack(null)
                        .commit()
                }
                .setNegativeButton("취소") { dialog, which ->
                }
                .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}