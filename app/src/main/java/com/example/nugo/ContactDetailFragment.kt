package com.example.nugo

/* [ 정호정 파트 ]
연락처 상세정보에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 ContactData를 통해 연락처 정보를 읽고 씁니다
2. 연락처 리스트 (ContactListFragment)와 스티커 디테일 (StickerDetailFragment)에서 선택한 연락처를 넘겨받습니다*/

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nugo.databinding.FragmentContactDetailBinding

private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class ContactDetailFragment : Fragment() {
    private var param1: String? = null
//    private var param2: String? = null

    private var _binding: FragmentContactDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    companion object {
        fun newInstance(param1: String) =
            ContactDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = param1?.toInt()!!
        binding?.ivDetailProfile?.setImageResource(ContactManager.Contacts[position].photo)
        binding?.etDetailName?.setText(ContactManager.Contacts[position].name)
        binding?.etDetailNumber?.setText(ContactManager.Contacts[position].number)
        binding?.etDetailEmail?.setText(ContactManager.Contacts[position].email)
        binding?.ivDetailSticker1?.setImageResource(ContactManager.Contacts[position].sticker0)
        binding?.ivDetailSticker2?.setImageResource(ContactManager.Contacts[position].sticker1)
        binding?.ivDetailSticker3?.setImageResource(ContactManager.Contacts[position].sticker2)
        binding?.ivDetailSticker4?.setImageResource(ContactManager.Contacts[position].sticker3)
        binding?.ivDetailSticker5?.setImageResource(ContactManager.Contacts[position].sticker4)
        binding?.ivDetailProfile?.setImageResource(ContactManager.Contacts[position].photo)

    }

}