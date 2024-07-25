package com.example.nugo

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nugo.databinding.FragmentNewStickerDialogueBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_STICKER_INDEX = "stickerIndex"

class NewStickerDialogueFragment() : DialogFragment() {
    private var stickerIndex: Int? = null
    private val binding by lazy { FragmentNewStickerDialogueBinding.inflate(layoutInflater) }

    data class IconPicker(val index: Int, var isSelected: Boolean)
    val iconPickers:MutableList<IconPicker> = mutableListOf<IconPicker>()

    private fun iconPickersadd(){
        for (i in 0..34) {
            iconPickers.add(IconPicker(i, false))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            stickerIndex = it.getInt(ARG_STICKER_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val stickerIndex = stickerIndex?.toInt() ?: -1 // 널체크

        if (StickerManager.stickers[stickerIndex].isDelete == false) {
            binding.etStickerName.setText(StickerManager.stickers[stickerIndex].name)
        } else {
            binding.etStickerName.setText("")
        }
        val recentIconImg = StickerManager.stickers[stickerIndex].icon

        iconPickersadd()
        val adapter = GridItemAdapter(iconPickers, recentIconImg)
        binding.rvStickerList.adapter = adapter
        binding.rvStickerList.layoutManager = GridLayoutManager(requireContext(), 5)

        var sellectName: String = StickerManager.stickers[stickerIndex].name
        var sellectIconImg: Int = recentIconImg

        adapter.itemClick = object : GridItemAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                sellectIconImg = position + 1
            }
        }

        binding.btnEdit.setOnClickListener {

            if (binding.etStickerName.text.isEmpty()){
                Toast.makeText(requireContext(), "스티커 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            } else if (binding.etStickerName.text.toString().length > 9){
                Toast.makeText(requireContext(), "최대 8글자 까지 입력할 수 있습니다", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            } else if (sellectIconImg == 0){
                Toast.makeText(requireContext(), "스티커 아이콘을 선택하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            sellectName = binding.etStickerName.text.toString()
            StickerManager.stickers[stickerIndex].name = sellectName
            StickerManager.stickers[stickerIndex].icon = sellectIconImg
            StickerManager.stickers[stickerIndex].isDelete = false

            val resultBundle = bundleOf("dataSend" to "dataSend")
            setFragmentResult("dataSend", resultBundle)

            requireActivity().onBackPressed()
        }

        binding.ivIcClose.setOnClickListener{
            requireActivity().onBackPressed()
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewStickerDialogueFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(stickerIndex: Int) =
            NewStickerDialogueFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_STICKER_INDEX, stickerIndex)
                }
            }
    }
}