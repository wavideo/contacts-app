package com.example.nugo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nugo.databinding.FragmentContactDetailBinding
import com.example.nugo.databinding.FragmentListAddFirstStickerBinding

private const val ARG_PARAM1 = "param1"

class ListAddFirstStickerFragment : Fragment() {
    private var param1: String? = null

    private val binding by lazy { FragmentListAddFirstStickerBinding.inflate(layoutInflater) }
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var position = param1?.toInt()!!
        val myContact = ContactManager.Contacts[position]

        // 닫기 버튼
        binding.ivCancel.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivListAddFirstSticker1.setImageResource(StickerManager.icons[StickerManager.stickers[0].icon])
        binding.ivListAddFirstSticker2.setImageResource(StickerManager.icons[StickerManager.stickers[1].icon])
        binding.ivListAddFirstSticker3.setImageResource(StickerManager.icons[StickerManager.stickers[2].icon])
        binding.ivListAddFirstSticker4.setImageResource(StickerManager.icons[StickerManager.stickers[3].icon])
        binding.ivListAddFirstSticker5.setImageResource(StickerManager.icons[StickerManager.stickers[4].icon])

        binding.tvListAddFirstSticker1Name.text = StickerManager.stickers[0].name
        binding.tvListAddFirstSticker2Name.text = StickerManager.stickers[1].name
        binding.tvListAddFirstSticker3Name.text = StickerManager.stickers[2].name
        binding.tvListAddFirstSticker4Name.text = StickerManager.stickers[3].name
        binding.tvListAddFirstSticker5Name.text = StickerManager.stickers[4].name

        binding.ivListAddFirstSticker1.setOnClickListener{
            myContact.recentSticker = 0
            myContact.sticker0 += 1
            Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivListAddFirstSticker2.setOnClickListener{
            myContact.recentSticker = 1
            myContact.sticker1 += 1
            Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivListAddFirstSticker3.setOnClickListener{
            myContact.recentSticker = 2
            myContact.sticker2 += 1
            Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivListAddFirstSticker4.setOnClickListener{
            myContact.recentSticker = 3
            myContact.sticker3 += 1
            Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivListAddFirstSticker5.setOnClickListener{
            myContact.recentSticker = 4
            myContact.sticker4 += 1
            Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, ContactListFragment())
                .addToBackStack(null)
                .commit()
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ListAddFirstStickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}