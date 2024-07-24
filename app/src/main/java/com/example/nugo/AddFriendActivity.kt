package com.example.nugo

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nugo.databinding.ActivityAddFriendBinding

class AddFriendActivity : AppCompatActivity() {

    private lateinit var user: ContactData
    private var name_ = ""
    private var number_ = ""
    private var email_ = ""

    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? -> uri?.let {
        // 선택한 이미지를 ImageView에 저장
        val bitmap = uriToBitmap(it)
        binding?.ivAddFriendProfile?.setImageBitmap(bitmap)
        user.photo = bitmap
    }
    }

    private val binding by lazy { ActivityAddFriendBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)





        // 사진 추가하기
        binding.ivAddFriendPhotoEdit.setOnClickListener{
            binding?.ivAddFriendAvatar?.visibility = View.GONE
            Toast.makeText(this, "갤러리에서 사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
            getImageFromGallery.launch("image/*")
        }

        // 사진 삭제하기
        binding.ivAddFriendPhotoDelete.setOnClickListener{
            AlertDialog.Builder(this)
                .setMessage("사진을 삭제하시겠습니까?")
                .setPositiveButton("확인") { dialog, which ->
                    binding?.ivAddFriendProfile?.setImageBitmap(sampleBitmap)
                    binding?.ivAddFriendAvatar?.visibility = View.VISIBLE
                    user.photo = sampleBitmap
                }
                .setNegativeButton("취소") { dialog, which ->
                }
                .show()
        }

        // 연락처 저장하기
        binding.btnAddFriendSave.setOnClickListener{
            Toast.makeText(this, "연락처가 저장되었습니다.", Toast.LENGTH_SHORT).show()
            name_ = binding.etAddFriendName.text.toString()
            number_ = binding.etAddFriendNumber.text.toString()
            email_ = binding.etAddFriendEmail.text.toString()

            // onCreate() 안에서 lateinit 변수 초기화해줘야 함.
            user = ContactData(name_, number_, email_)
            // 데이터에 신규 연락처 정보 추가, 리스트 맨 위에 표시
            ContactManager.Contacts.add(0, user)

            finish()
        }

        // 뒤로 가기
        binding.ivAddFriendBack.setOnClickListener{
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