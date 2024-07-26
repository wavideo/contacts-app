package com.example.nugo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nugo.contact.ContactData
import com.example.nugo.sticker.StickerManager

class SharedViewModel : ViewModel() {

    init {
        initContact()
    }

    private val _contacts = MutableLiveData<List<ContactData>>()
    val contacts:LiveData<List<ContactData>> = _contacts
    fun addContactData(data: ContactData){
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList.add(0, data)
//        currentList.get(5).name = ""
        _contacts.value = currentList
    }

    fun editContactDataByName(position:Int, name:String){
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList.get(position).name = name
        _contacts.value = currentList
    }

    fun editContactDataBySticker0(position:Int,  sticker0:Int){
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList.get(position).sticker0 = sticker0
        _contacts.value = currentList
    }

    fun editContactData(position:Int, data: ContactData){
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList[position] = data
        _contacts.value = currentList
    }

    fun removeContactDataByName(name: String) {
        val currentList = _contacts.value?.toMutableList() ?: mutableListOf()
        currentList.remove( currentList.find { it.name == name } )
       _contacts.value = currentList
    }

    private fun initContact() {
        addContactData(ContactData("김은택", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("정호정", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("허민", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("전은혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("한혜림", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("이태우", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("유건희", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("문지혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("최어진", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("박혜민", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("이종범", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("황성희", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("신성휘", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("성희영", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("정용현", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("이수빈", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("김린아", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("장세진", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("조 바이든", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("도널드 트럼프", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("박근혜", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("문재인", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
        addContactData(ContactData("윤석열", "010-1111-1111", "aaa@naver.com", 0, 0, 0, 0, 0, 0))
    }




    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count
    fun setCount(count:Int){
        _count.value = count
    }
}
