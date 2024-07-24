package com.example.nugo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.content.Intent // 안드로이드 Intent 임포트
import android.net.Uri
import android.content.Context
import android.graphics.BitmapFactory
import kotlinx.coroutines.currentCoroutineContext
import java.io.File
import kotlin.coroutines.coroutineContext
import android.graphics.Path

/* [ 정호정 파트 ]
연락처에 대한 data class 입니다*/

// 예시 bitmap 이미지 생성
fun createSampleBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    paint.color = R.color.white
    canvas.drawCircle(500f, 450f, 150f, paint)

    paint.color = R.color.white
    val path = android.graphics.Path()
    path.addCircle(500f, 1000f, 350f, Path.Direction.CCW)
    canvas.drawPath(path, paint)

    return bitmap
}

val sampleBitmap = createSampleBitmap()

data class ContactData(
    var name: String,
    var number: String,
    var email: String,
    var sticker0: Int,
    var sticker1: Int,
    var sticker2: Int,
    var sticker3: Int,
    var sticker4: Int,
    var recentSticker : Int,
    var photo: Bitmap
) {
    constructor(name: String, number: String, email: String) : this(
        name,
        number,
        email,
        0,
        0,
        0,
        0,
        0,
        0,
        sampleBitmap
    )

    constructor(
        name: String,
        number: String,
        email: String,
        sticker0: Int,
        sticker1: Int,
        sticker2: Int,
        sticker3: Int,
        sticker4: Int,
        recentSticker: Int
    ) : this(
        name,
        number,
        email,
        sticker0,
        sticker1,
        sticker2,
        sticker3,
        sticker4,
        recentSticker,
        sampleBitmap
    )

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
    val Contacts = mutableListOf<ContactData>()

    /*앱에 넣어둘 더미데이터입니다.
    MainActivity 상단에 ContactManager.loading()으로 더미데이터를 등록해주세요 */
    fun loading() {
        Contacts.add(ContactData("김은택", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("정호정", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("허민", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("전은혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("한혜림", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("이태우", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("유건희", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("문지혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("최어진", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("박혜민", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("이종범", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("황성희", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("신성휘", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("성희영", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("정용현", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("이수빈", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("김린아", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("장세진", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("조 바이든", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("도널드 트럼프", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("박근혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("문재인", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        Contacts.add(ContactData("윤석열", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
    }

    fun delete(name_: String) {
        Contacts.remove(Contacts.find { it.name == name_ })
    }
    fun makeCall(context: Context, name: String) {
        //앱의 현재 상태를 불러옴 Context, name은 전화할 사람
        val contact = Contacts.find { it.name == name }
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
        val contact = Contacts.find { it.name == name }
        contact?.let {
            val number = it.number
            val intentMessage = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:$number")
            }
            context.startActivity(intentMessage)
        }
    }
}