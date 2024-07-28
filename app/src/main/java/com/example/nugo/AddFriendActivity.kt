package com.example.nugo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.nugo.contact.ContactData
import com.example.nugo.contact.ContactManager
import com.example.nugo.contact.sampleBitmap
import com.example.nugo.databinding.ActivityAddFriendBinding

const val EXTRA_NUMBER_LIST = "NUMBER_LIST"
const val EXTRA_NEW_USER_NAME = "NEW_USER_NAME"
const val EXTRA_NEW_USER_NUMBER = "NEW_USER_NUMBER"

class AddFriendActivity : AppCompatActivity() {

    private lateinit var user: ContactData
    private lateinit var numberList: MutableList<String>
    private var name_ = ""
    private var number_ = ""
    private var email_ = ""
    private lateinit var photo_: Bitmap

    private val getImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // 선택한 이미지를 ImageView에 저장
                val bitmap = uriToBitmap(it)
                binding?.ivAddFriendProfile?.setImageBitmap(bitmap)
                photo_ = bitmap
            }
        }

    private val binding by lazy { ActivityAddFriendBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.ivAddFriendProfile.setImageBitmap(sampleBitmap)
        numberList = intent.getStringArrayListExtra(EXTRA_NUMBER_LIST) ?: mutableListOf()
        user = ContactData(name_, number_, email_)

        // 사진 추가하기
        binding.ivAddFriendPhotoEdit.setOnClickListener {
            Toast.makeText(this, "갤러리에서 사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
            photo_ = sampleBitmap
            getImageFromGallery.launch("image/*")
            user.photo = photo_
        }

        // 사진 삭제하기
//        binding.ivAddFriendPhotoDelete.setOnClickListener{
//            AlertDialog.Builder(this)
//                .setMessage("사진을 삭제하시겠습니까?")
//                .setPositiveButton("확인") { dialog, which ->
//                    binding?.ivAddFriendProfile?.setImageBitmap(sampleBitmap)
//                    binding?.ivAddFriendAvatar?.visibility = View.VISIBLE
//                    user.photo = sampleBitmap
//                }
//                .setNegativeButton("취소") { dialog, which ->
//                }
//                .show()
//        }

        // 이메일 드롭다운 설정
        val spinnerItems =
            arrayOf("@naver.com", "@daum.net", "@gmail.com", "@hanmail.net", "@nate.com", "직접입력")
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.spinner.adapter = spinnerAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
//                Toast.makeText(this@AddFriendActivity, "${selectedItem}이 선택되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // 연락처 저장하기
        binding.btnAddFriendSave.setOnClickListener {
            if (binding.etAddFriendNumber.text.toString() in numberList) {
                Toast.makeText(this, "중복된 연락처가 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "연락처가 저장되었습니다.", Toast.LENGTH_SHORT).show()
                name_ = binding.etAddFriendName.text.toString()
                number_ = binding.etAddFriendNumber.text.toString()
                if (binding.spinner.selectedItem.toString() != "직접입력") {
                    email_ =
                        binding.etAddFriendEmail.text.toString() + binding.spinner.selectedItem.toString()
                } else {
                    email_ = binding.etAddFriendEmail.text.toString()
                }

                // onCreate() 안에서 lateinit 변수 초기화해줘야 함.
                user = ContactData(name_, number_, email_)
                user.photo = photo_
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_NEW_USER_NAME, user.name)
                    putExtra(EXTRA_NEW_USER_NUMBER, user.number)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        // 뒤로 가기
        binding.ivAddFriendBack.setOnClickListener {
            finish()
        }

    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(this.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
    }

}