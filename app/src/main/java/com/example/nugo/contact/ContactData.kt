package com.example.nugo.contact

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Parcelable
import com.example.nugo.SharedViewModel
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
}