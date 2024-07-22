package com.example.nugo

/* [ 김은택 파트 ]
스티커에 대한 data class 입니다*/


data class StickerData(var name: String, var icon: Int, var isDelete: Boolean) {
    var contactSize = 0 // 등록된 연락처 수

    // .findDrawable() 으로 icon에 들어갈 drawable 이미지를 찾을 수 있습니다
    fun findDrawable(): Int {
        return StickerManager.icons[icon]
    }

    // .delete() 로 스티커 정보를 지웁니다
    fun delete() {
        isDelete = true
        name = "스티커 추가하기"
        icon = 0
        contactSize = 0
    }

    // .edit() 로 스티커 정보를 추가/수정합니다
    fun edit(_name: String, _icon: Int) {
        isDelete = false
        name = _name
        icon = _icon
    }
}

object StickerManager {

    // 데이터 리스트입니다. StickerManager.Stickers[0] 으로 n번째 스티커를 호출할 수 있습니다
    val stickers = mutableListOf<StickerData>()

    /* MainActivity 상단에 StrickerManager.loading()을 입력해주세요
    기본 스티커 4종과 빈 스티커 자리 1종, 총 5개의 슬롯이 추가됩니다 */
    fun loading() {
        stickers.add(StickerData("고마워", 1, false))
        stickers.add(StickerData("경고", 33, false))
        stickers.add(StickerData("밥 사주신 분들", 16, false))
        stickers.add(StickerData("지각한 사람", 13, false))
        stickers.add(StickerData("스티커 추가하기", 0, true))
    }

    // icon 이미지를 IntArray로 관리합니다
    val icons = intArrayOf(
        R.drawable.ic_add_circle,
        R.drawable.imoge01_thumbsup,
        R.drawable.imoge02_slightlysmilingface,
        R.drawable.imoge03_smilingfacewithhearts,
        R.drawable.imoge04_smilingfacewithsunglasses,
        R.drawable.imoge05_partyingface,
        R.drawable.imoge06_thumbsdown,
        R.drawable.imoge07_confusedface,
        R.drawable.imoge08_grinningfacewithsweat,
        R.drawable.imoge09_loudlycryingface,
        R.drawable.imoge10_poutingface,
        R.drawable.imoge11_telephonereceiver,
        R.drawable.imoge12_speechballoon,
        R.drawable.imoge13_alarmclock,
        R.drawable.imoge14_moneywithwings,
        R.drawable.imoge15_handshake,
        R.drawable.imoge16_cookedrice,
        R.drawable.imoge17_hotbeverage,
        R.drawable.imoge18_shoppingbags,
        R.drawable.imoge19_partypopper,
        R.drawable.imoge20_foldedhands,
        R.drawable.imoge21_house,
        R.drawable.imoge22_automobile,
        R.drawable.imoge23_babylightskintone,
        R.drawable.imoge24_dogface,
        R.drawable.imoge25_catface,
        R.drawable.imoge26_hollowredcircle,
        R.drawable.imoge27_star,
        R.drawable.imoge28_redheart,
        R.drawable.imoge29_lightbulb,
        R.drawable.imoge30_fire,
        R.drawable.imoge31_crossmark,
        R.drawable.imoge32_warning,
        R.drawable.imoge33_policecarlight,
        R.drawable.imoge34_pileofpoo,
        R.drawable.imoge35_bomb
    )
}