package com.example.nugo

/* [ 김은택 파트 ]
스티커 상세정보에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 StickerData를 통해 스티커 정보를 읽고 씁니다
2. 스티커 리스트 (StickerListFragment)에서 선택한 스티커를 넘겨받습니다
3. 연락처 상세정보 (ContactDetailFragment)로 선택한 연락처를 넘겨줍니다*/

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.databinding.FragmentStickerDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM_STICKER = "paramSticker"

/**
 * A simple [Fragment] subclass.
 * Use the [StickerDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StickerDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var paramSticker: Int? = null
    private val binding by lazy { FragmentStickerDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramSticker = it.getInt(ARG_PARAM_STICKER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ContactOfStickers.clear()

        var stickerIndex: Int = 0
        paramSticker?.let { stickerIndex = it }

        when (stickerIndex) {
            0 -> {
                ContactOfStickers.addAll(ContactManager.Contacts.filter { it.sticker0 != 0 })
                ContactOfStickers.sortByDescending { it.sticker0 }
            }

            1 -> {
                ContactOfStickers.addAll(ContactManager.Contacts.filter { it.sticker1 != 0 })
                ContactOfStickers.sortByDescending { it.sticker1 }
            }

            2 -> {
                ContactOfStickers.addAll(ContactManager.Contacts.filter { it.sticker2 != 0 })
                ContactOfStickers.sortByDescending { it.sticker2 }
            }

            3 -> {
                ContactOfStickers.addAll(ContactManager.Contacts.filter { it.sticker3 != 0 })
                ContactOfStickers.sortByDescending { it.sticker3 }
            }

            else -> {
                ContactOfStickers.addAll(ContactManager.Contacts.filter { it.sticker4 != 0 })
                ContactOfStickers.sortByDescending { it.sticker4 }
            }
        }


        val adapter = ContactOfStickerAdapter(ContactOfStickers)
        binding.rvStickerList.adapter = adapter
        binding.rvStickerList.layoutManager = LinearLayoutManager(requireContext())

        adapter.itemClick = object : ContactOfStickerAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

                val myIndex = ContactManager.Contacts.indexOf(ContactOfStickers[position])

                val fragment = ContactDetailFragment.newInstance(myIndex.toString())
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.ivBtnBack.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.tvBtnEdit.setOnClickListener {
            val fragment = NewStickerDialogueFragment.newInstance(stickerIndex)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.cv_popup_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        setFragmentResultListener("dataSend") { key, bundle ->
            binding.tvTitle.text = StickerManager.stickers[stickerIndex].name
            binding.ivStickerIcon.setImageResource(StickerManager.stickers[stickerIndex].findDrawable())
            adapter.updateData(ContactOfStickers)
        }


        binding.tvBtnDelete.setOnClickListener {

            AlertDialog.Builder(this.requireContext())
                .setMessage("스티커를 삭제하시겠습니까?")
                .setPositiveButton("확인") { dialog, which ->
                    StickerManager.stickers[stickerIndex].delete()
                    when (stickerIndex){
                        0 -> ContactOfStickers.forEach{
                            it.sticker0 = 0
                        }
                        1 -> ContactOfStickers.forEach{
                            it.sticker1 = 0
                        }
                        2 -> ContactOfStickers.forEach{
                            it.sticker2 = 0
                        }
                        3 -> ContactOfStickers.forEach{
                            it.sticker3 = 0
                        }
                        else -> ContactOfStickers.forEach{
                            it.sticker4 = 0
                        }
                    }
                    StickerManager.stickers[stickerIndex].delete()
                    requireActivity().onBackPressed()
                }
                .setNegativeButton("취소") { dialog, which ->
                }
                .show()
        }

        StickerManager.detailPicker = stickerIndex
        val mySticker = StickerManager.stickers[stickerIndex]
        
        binding.tvTitle.text = mySticker.name
        binding.ivStickerIcon.setImageResource(mySticker.findDrawable())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StickerDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(paramSticker: Int) =
            StickerDetailFragment().apply {
                arguments = Bundle().apply {
                    paramSticker?.let { putInt(ARG_PARAM_STICKER, it) }
                }
            }

        val ContactOfStickers = mutableListOf<ContactData>()
    }
}

private fun <E> MutableList<E>.all(predicate: (E) -> Unit) {

}
