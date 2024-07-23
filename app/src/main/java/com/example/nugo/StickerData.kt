package com.example.nugo

/* [ 김은택 파트 ]
스티커에 대한 data class 입니다*/


data class StickerData(var name:String, var icon:Int, var isDelete:Boolean){
    var contactSize = 0 // 등록된 연락처 수

    // .findDrawable() 으로 icon에 들어갈 drawable 이미지를 찾을 수 있습니다
    fun findDrawable(){
        StickerManager.icons[icon]
    }

    // .delete() 로 스티커 정보를 지웁니다
    fun delete(){
        isDelete = true
        name = "스티커 추가하기"
        icon = 0
        contactSize = 0
    }

    // .edit() 로 스티커 정보를 추가/수정합니다
    fun edit(_name:String, _icon:Int){
        isDelete = false
        name = _name
        icon = _icon
    }
}

object StickerManager{

    // 데이터 리스트입니다. StickerManager.Stickers[0] 으로 n번째 스티커를 호출할 수 있습니다
    val stickers = mutableListOf<StickerData>()

    /* MainActivity 상단에 StrickerManager.loading()을 입력해주세요
    기본 스티커 4종과 빈 스티커 자리 1종, 총 5개의 슬롯이 추가됩니다 */
    fun loading(){
        stickers.add(StickerData("고마워",0, false))
        stickers.add(StickerData("경고",0, false))
        stickers.add(StickerData("밥 사주신 분들",0, false))
        stickers.add(StickerData("지각한 사람",0, false))
        stickers.add(StickerData("",0, true))
    }

    // icon 이미지를 IntArray로 관리합니다
    val icons = intArrayOf(
        // drawable이 들어갑니다
    )
}