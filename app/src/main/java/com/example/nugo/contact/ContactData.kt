package com.example.nugo.contact

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/* [ 정호정 파트 ]
연락처에 대한 data class 입니다*/

// 예시 bitmap 이미지 생성
fun createSampleBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    paint.color = Color.WHITE
    canvas.drawCircle(500f, 450f, 150f, paint)

    paint.color = Color.WHITE
    val path = android.graphics.Path()
    path.addCircle(500f, 1000f, 350f, Path.Direction.CCW)
    canvas.drawPath(path, paint)

    return bitmap
}

val sampleBitmap = createSampleBitmap()

@Parcelize
data class ContactData(
    var name: String,
    var number: String,
    var email: String = "",
    var sticker0: Int = 0,
    var sticker1: Int = 0,
    var sticker2: Int = 0,
    var sticker3: Int = 0,
    var sticker4: Int = 0,
    var recentSticker: Int = 0,
    var photo: Bitmap = sampleBitmap
) : Parcelable {
    // 연락처 인스턴스에 .stickerUp(3) 하면 스티커 등록 갯수가 1개 늘어납니다
    fun stickerUp(index: Int) {
        when (index) {
            0 -> sticker0++
            1 -> sticker1++
            2 -> sticker2++
            3 -> sticker3++
            else -> sticker4++
        }
    }

    fun stickerDown(index: Int) {
        when (index) {
            0 -> sticker0--
            1 -> sticker1--
            2 -> sticker2--
            3 -> sticker3--
            else -> sticker4--
        }
    }
}

object ContactManager {

    val sampleBitmap = createSampleBitmap()

    // 데이터 리스트입니다. ContactManager.Contacts[0] 으로 n번째 연락처를 호출할 수 있습니다
//    var contacts = mutableListOf<ContactData>()

    /*앱에 넣어둘 더미데이터입니다.
    MainActivity 상단에 ContactManager.loading()으로 더미데이터를 등록해주세요 */

    fun makeCall(context: Context, name: String) {
//        //앱의 현재 상태를 불러옴 Context, name은 전화할 사람
//        val contact = contacts.find { it.name == name }
//        //Contacts에서 name과 일치하는 연락처를 찾음, find는 name을 반환.
//        contact?.let {
//            val number = it.number
//            //연락처가 ? = null이 아닐 경우, 연락처의 number를 가져옴
//            val intent = Intent(Intent.ACTION_DIAL).apply {
//                //Intent를 생성 ACTION_DIAL로 다이얼을 열게함,apply를 사용해 가독성을 높임.
//                data = Uri.parse("tel:$number")
//            }
//            context.startActivity(intent)
//            //context를 통해 다이얼 시작
//        }
    }

    fun makeSMS(context: Context, name: String) {
//        val contact = contacts.find { it.name == name }
//        contact?.let {
//            val number = it.number
//            val intentMessage = Intent(Intent.ACTION_SENDTO).apply {
//                data = Uri.parse("smsto:$number")
//            }
//            context.startActivity(intentMessage)
//        }
    }

//    fun getData(): MutableList<ContactData> {
//        return contacts
//    }
}