package com.example.nugo

/* [ 정호정 파트 ]
연락처에 대한 data class 입니다*/

data class ContactData(val name:String, var number:String, var email:String, var sticker0:Int, var sticker1:Int, var sticker2:Int, var sticker3:Int, var sticker4:Int){
    constructor( name:String,  number:String,  email:String) : this (name, number, email, 0, 0, 0, 0, 0)

    // 연락처 인스턴스에 .sickerUp(3) 하면 스티커 등록 갯수가 1개 늘어납니다
    fun stickerUp(index:Int){
        when(index){
            0 -> sticker0++
            1 -> sticker1++
            2 -> sticker2++
            3 -> sticker3++
            else -> sticker4++
        }
    }

    fun stickerDown(index:Int){
        when(index){
            0 -> sticker0--
            1 -> sticker1--
            2 -> sticker2--
            3 -> sticker3--
            else -> sticker4--
        }
    }

}

object ContactManager{

    // 데이터 리스트입니다. ContactManager.Contacts[0] 으로 n번째 연락처를 호출할 수 있습니다
    val Contacts = mutableListOf<ContactData>()

    /*앱에 넣어둘 더미데이터입니다.
    MainActivity 상단에 ContactManager.loading()으로 더미데이터를 등록해주세요 */
    fun loading(){
        Contacts.add (ContactData("나 (본인)", "", ""))
        Contacts.add (ContactData ("김은택", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("정호정", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("허민", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("전은혜", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("한혜림", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("이태우", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("유건희", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("문지혜", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("최어진", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("박혜민", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("이종범", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("황성희", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("신성휘", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("성희영", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("정용현", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("이수빈", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("김린아", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("장세진", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("조 바이든", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("도널드 트럼프", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("박근혜", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("문재인", "010-1111-1111", "aaa@naver.com"))
        Contacts.add (ContactData("윤석열", "010-1111-1111", "aaa@naver.com"))
    }
}