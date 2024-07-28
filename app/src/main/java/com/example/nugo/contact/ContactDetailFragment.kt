package com.example.nugo.contact

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import com.example.nugo.sticker.NewStickerDialogueFragment
import com.example.nugo.R
import com.example.nugo.SharedViewModel
import com.example.nugo.sticker.StickerManager
import com.example.nugo.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {
    private val binding by lazy { FragmentContactDetailBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<SharedViewModel>()
    private lateinit var contact: ContactData
    private var editStickerCountNumber: Int = 99

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // 선택한 이미지를 ImageView에 저장

                val bitmap = uriToBitmap(it)
                binding?.ivDetailProfile?.setImageBitmap(bitmap)
                contact.photo = bitmap
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact = it.getParcelable<ContactData>(ARG_DETAIL_DATA) ?: run {
                Log.e(TAG, "contact is null")
                parentFragmentManager.popBackStack()
                return
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    companion object {
        private const val TAG = "ContactDetail"
        private const val ARG_DETAIL_DATA = "DETAIL_DATA"
        fun newInstance(data: ContactData) =
            ContactDetailFragment().apply {
                arguments = bundleOf(ARG_DETAIL_DATA to data)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etDetailName.setText(contact.name)
        binding.tvDetailName.setText(contact.name)
        binding.etDetailNumber.setText(contact.number)
        binding.etDetailEmail.setText(contact.email)
        binding.ivDetailProfile.setImageBitmap(contact.photo)


        for (i in 0..4) {
            updateStickerNum(i)
        }


        binding.ivDetailSticker1.setOnClickListener {
            stickerClickListener(0)
        }
        binding.ivDetailSticker1.setOnLongClickListener {
            showCustomDialog(0)
            true
        }

        binding.ivDetailSticker2.setOnClickListener {
            stickerClickListener(1)
        }
        binding.ivDetailSticker2.setOnLongClickListener {
            showCustomDialog(1)
            true
        }

        binding.ivDetailSticker3.setOnClickListener {
            stickerClickListener(2)
        }
        binding.ivDetailSticker3.setOnLongClickListener {
            showCustomDialog(2)
            true
        }

        binding.ivDetailSticker4.setOnClickListener {
            stickerClickListener(3)
        }
        binding.ivDetailSticker4.setOnLongClickListener {
            showCustomDialog(3)
            true
        }

        binding.ivDetailSticker5.setOnClickListener {
            stickerClickListener(4)
        }
        binding.ivDetailSticker5.setOnLongClickListener {
            showCustomDialog(4)
            true
        }



        binding.ivDetailProfile.setImageBitmap(contact.photo)

        // 통화 버튼
        binding.btnDetailCall.setOnClickListener {
            makeCall(this.requireContext(), contact.name)
        }

        // 메시지 버튼
        binding.btnDetailMessage.setOnClickListener {
            makeSMS(this.requireContext(), contact.name)
        }

        // 뒤로가기 버튼
        binding?.ivDetailBack?.setOnClickListener {
            requireActivity().onBackPressed()
        }

        var isEditClicked = false


        // 수정 버튼 누르면 저장 버튼, 사진 추가 버튼, 사진 삭제 버튼, 각 EditText 활성화
        binding.ivDetailEdit.setOnClickListener {
            isEditClicked = true

            binding.ivDetailEdit.visibility = View.GONE
            binding.ivDetailSave.visibility = View.VISIBLE

            binding.ivDetailPhotoEdit.visibility = View.VISIBLE

            binding.etDetailName.isEnabled = true
            binding.etDetailNumber.isEnabled = true
            binding.etDetailEmail.isEnabled = true

            // 사진 추가 버튼
            binding.ivDetailPhotoEdit.setOnClickListener {
                binding.ivDetailAvatar.visibility = View.GONE
                Toast.makeText(this.requireContext(), "갤러리에서 사진을 선택해주세요.", Toast.LENGTH_SHORT)
                    .show()
                getImageFromGallery.launch("image/*")
            }

            binding.ivDetailSave.setOnClickListener {
                contact.name = binding.etDetailName.text.toString()
                binding.tvDetailName.setText(contact.name)
                contact.number = binding.etDetailNumber.text.toString()
                contact.email = binding.etDetailEmail.text.toString()
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

        // 삭제하기 버튼
        binding.btnDetailDelete.setOnClickListener {
            AlertDialog.Builder(this.requireContext())
                .setMessage("연락처를 삭제하시겠습니까?")
                .setPositiveButton("확인") { dialog, which ->
                    viewModel.removeContactDataByName(contact.name)
                    requireActivity().onBackPressed()
                }
                .setNegativeButton("취소") { dialog, which ->
                }
                .show()
        }

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

    private fun showCustomDialog(i: Int) {
        if (viewModel.getStickerList()[i].isDelete) {
            return
        }

        editStickerCountNumber = i
        val dialogView = LayoutInflater.from(binding.root.context)
            .inflate(R.layout.dialog_edit_sticker_number, null)
        val ivStickerIcon = dialogView.findViewById<ImageView>(R.id.iv_dialog_sticker_icon)
        val btnMinus = dialogView.findViewById<Button>(R.id.btn_minus)
        val btnPlus = dialogView.findViewById<Button>(R.id.btn_plus)
        val tvCount = dialogView.findViewById<TextView>(R.id.tv_editted_count)

        // 다이얼로그의 스티커 아이콘 설정
        when (editStickerCountNumber) {
            0 -> ivStickerIcon.setImageResource(viewModel.getStickerList()[0].findDrawable())
            1 -> ivStickerIcon.setImageResource(viewModel.getStickerList()[1].findDrawable())
            2 -> ivStickerIcon.setImageResource(viewModel.getStickerList()[2].findDrawable())
            3 -> ivStickerIcon.setImageResource(viewModel.getStickerList()[3].findDrawable())
            4 -> ivStickerIcon.setImageResource(viewModel.getStickerList()[4].findDrawable())
        }

        // 다이얼로그의 초기 카운트 값 설정
        when (editStickerCountNumber) {
            0 -> tvCount.text = contact.sticker0.toString()
            1 -> tvCount.text = contact.sticker1.toString()
            2 -> tvCount.text = contact.sticker2.toString()
            3 -> tvCount.text = contact.sticker3.toString()
            4 -> tvCount.text = contact.sticker4.toString()
        }

        var count2 = tvCount.text.toString().toInt()

        // 빼기 버튼 클릭 시 동작
        btnMinus.setOnClickListener {
            if (count2 > 0) {
                count2 -= 1
                tvCount.text = count2.toString()
            }
        }
        // 더하기 버튼 클릭 시 동작
        btnPlus.setOnClickListener {
            if (count2 < 100) {
                count2 += 1
                tvCount.text = count2.toString()
            }
        }

        val dialogBuilder = AlertDialog.Builder(binding.root.context)
            .setView(dialogView)
            .setPositiveButton("확인") { dialog, _ ->
                when (editStickerCountNumber) {
                    0 -> {
                        contact.sticker0 = tvCount.text.toString().toInt()
                        binding.ivDetailSticker1Count.text = contact.sticker0.toString()
                    }

                    1 -> {
                        contact.sticker1 = tvCount.text.toString().toInt()
                        binding.ivDetailSticker2Count.text =
                            contact.sticker1.toString()
                    }

                    2 -> {
                        contact.sticker2 = tvCount.text.toString().toInt()
                        binding.ivDetailSticker3Count.text = contact.sticker2.toString()
                    }

                    3 -> {
                        contact.sticker3 = tvCount.text.toString().toInt()
                        binding.ivDetailSticker4Count.text = contact.sticker3.toString()
                    }

                    4 -> {
                        contact.sticker4 = tvCount.text.toString().toInt()
                        binding.ivDetailSticker5Count.text = contact.sticker4.toString()
                    }
                }
                updateStickerNum(i)
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = dialogBuilder.create()
        dialog.show()
    }

    fun updateStickerNum(index: Int) {
        when (index) {
            0 -> {
                binding.ivDetailSticker1.setImageResource(viewModel.getStickerList()[0].findDrawable())
                binding.tvDetailSticker1Name.setText(viewModel.getStickerList()[0].name)
            }

            1 -> {
                binding.ivDetailSticker2.setImageResource(viewModel.getStickerList()[1].findDrawable())
                binding.tvDetailSticker2Name.setText(viewModel.getStickerList()[1].name)
            }

            2 -> {
                binding.ivDetailSticker3.setImageResource(viewModel.getStickerList()[2].findDrawable())
                binding.tvDetailSticker3Name.setText(viewModel.getStickerList()[2].name)
            }

            3 -> {
                binding.ivDetailSticker4.setImageResource(viewModel.getStickerList()[3].findDrawable())
                binding.tvDetailSticker4Name.setText(viewModel.getStickerList()[3].name)
            }

            else -> {
                binding.ivDetailSticker5.setImageResource(viewModel.getStickerList()[4].findDrawable())
                binding.tvDetailSticker5Name.setText(viewModel.getStickerList()[4].name)
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
            0 -> contact.sticker0
            1 -> contact.sticker1
            2 -> contact.sticker2
            3 -> contact.sticker3
            else -> contact.sticker4
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

    fun updateIsDelete(index: Int) {
        val count = when (index) {
            0 -> binding.ivDetailSticker1Count
            1 -> binding.ivDetailSticker2Count
            2 -> binding.ivDetailSticker3Count
            3 -> binding.ivDetailSticker4Count
            else -> binding.ivDetailSticker5Count
        }
        val num: Int = when (index) {
            0 -> contact.sticker0
            1 -> contact.sticker1
            2 -> contact.sticker2
            3 -> contact.sticker3
            else -> contact.sticker4
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

    fun stickerClickListener(i: Int) {
        fun stickerPlus() {
            when (i) {
                0 -> contact.sticker0++
                1 -> contact.sticker1++
                2 -> contact.sticker2++
                3 -> contact.sticker3++
                else -> contact.sticker4++
            }
            viewModel.updateContactList()
        }

        val mySticker = viewModel.getStickerList()[i]
        if (mySticker.isDelete) {

            val fragment = NewStickerDialogueFragment.newInstance(i)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.cv_popup_container, fragment)
                .addToBackStack(null)
                .commit()
            setFragmentResultListener("dataSend") { key, bundle ->
                stickerPlus()
                contact.recentSticker = i
                updateStickerNum(i)
                viewModel.editContactData(contact)
            }

        } else {
            stickerPlus()
            contact.recentSticker = i
            updateStickerNum(i)
            viewModel.editContactData(contact)
        }

    }

    fun makeCall(context: Context, name: String) {
        //앱의 현재 상태를 불러옴 Context, name은 전화할 사람
        val contact = viewModel.findContactDataByName(name)
        //Contacts에서 name과 일치하는 연락처를 찾음, find는 name을 반환.
        contact?.let {
            val number = it.number
            //연락처가 ? = null이 아닐 경우, 연락처의 number를 가져옴
            val intent = Intent(Intent.ACTION_DIAL).apply {
                //Intent를 생성 ACTION_DIAL로 다이얼을 열게함,apply를 사용해 가독성을 높임.
                data = Uri.parse("tel:$number")
            }
            context.startActivity(intent)
            //context를 통해 다이얼 시작
        }
    }

    fun makeSMS(context: Context, name: String) {
        val contact = viewModel.findContactDataByName(name)
        contact?.let {
            val number = it.number
            val intentMessage = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:$number")
            }
            context.startActivity(intentMessage)
        }
    }


}