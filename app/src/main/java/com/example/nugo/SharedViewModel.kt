package com.example.nugo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nugo.contact.ContactData
import com.example.nugo.sticker.StickerData

class SharedViewModel : ViewModel() {
    private val contactList = mutableListOf<ContactData>()
    private val _contacts = MutableLiveData<List<ContactData>>()
    val contacts: LiveData<List<ContactData>> = _contacts

    private val stickerList = mutableListOf<StickerData>()
    private val _stickers = MutableLiveData<List<StickerData>>()
    val stickers: LiveData<List<StickerData>> = _stickers


    init {
        initContact()
        initSticker()
    }

    fun getContactList(): List<ContactData> = contactList
    fun getStickerList(): List<StickerData> = stickerList

    fun updateContactList(){
        _stickers.value = stickerList
        _contacts.value = contactList
    }
    fun findContactDataByName(name: String) : ContactData? {
        return contactList.find { it.name == name }
    }

    fun findStickerDataByIndex(index: Int) : StickerData? {
        return stickerList[index]
    }


    fun addContactData(data: ContactData) {
        contactList.add(0, data)
        _contacts.value = contactList.toList()
    }

    fun editContactDataByName(position: Int, name: String) {
        contactList[position].name = name
        _contacts.value = contactList.toList()
    }

    fun editContactData(data: ContactData) {
        contactList.indexOfFirst { it.name == data.name }.takeIf { it != -1 }?.let {
            contactList[it] = data
            _contacts.value = contactList.toList() // LiveData 업데이트
        }
    }

    fun editContactDataBySticker0(position: Int, sticker0: Int) {
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList[position].sticker0 = sticker0
        _contacts.value = contactList.toList()
    }


    fun editStickerDataByIndex(index: Int, data: StickerData) {
        stickerList[index] = data
        _stickers.value = stickerList.toList()
    }




    fun editContactData(position: Int, data: ContactData) {
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList[position] = data
        _contacts.value = contactList.toList()
    }

    fun removeContactDataByName(name: String) {
        contactList.remove(contactList.find { it.name == name })
        _contacts.value = contactList.toList()
    }

//    private val _count = MutableLiveData<Int>()
//    val count: LiveData<Int> = _count
//    fun setCount(count: Int) {
//        _count.value = count
//    }

    private fun initContact() {
        contactList.addAll(
            listOf(
                ContactData("김은택", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("정호정", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("허민", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("전은혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("한혜림", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("이태우", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("유건희", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("문지혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("최어진", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("박혜민", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("이종범", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("황성희", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("신성휘", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("성희영", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("정용현", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("이수빈", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("김린아", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("장세진", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("조 바이든", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("도널드 트럼프", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("박근혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("문재인", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0),
                ContactData("윤석열", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0)
            )
        )
        _contacts.value = contactList.toList()
    }
    private fun initSticker() {
        stickerList.addAll(
            listOf(
                StickerData("고마워", 1, false),
                StickerData ("경고", 33, false),
                StickerData ("밥 사주신 분들", 16, false),
                StickerData("지각한 사람", 13, false),
                StickerData("새 스티커 생성", 0, true)
            )
        )
        _stickers.value = stickerList.toList()
    }
}
