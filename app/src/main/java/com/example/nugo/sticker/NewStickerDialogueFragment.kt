package com.example.nugo.sticker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nugo.GridItemAdapter
import com.example.nugo.SharedViewModel
import com.example.nugo.databinding.FragmentNewStickerDialogueBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_STICKER_INDEX = "stickerIndex"

class NewStickerDialogueFragment() : DialogFragment() {
    private var stickerIndex: Int? = null
    private val binding by lazy { FragmentNewStickerDialogueBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<SharedViewModel>()

    data class IconPicker(val index: Int, var isSelected: Boolean)

    val iconPickers: MutableList<IconPicker> = mutableListOf<IconPicker>()

    private fun iconPickersadd() {
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

        if (viewModel.getStickerList()[stickerIndex].isDelete == false) {
            binding.etStickerName.setText(viewModel.getStickerList()[stickerIndex].name)
        } else {
            binding.etStickerName.setText("")
        }
        val recentIconImg = viewModel.getStickerList()[stickerIndex].icon

        iconPickersadd()
        val adapter = GridItemAdapter(iconPickers, recentIconImg)
        binding.rvStickerList.adapter = adapter
        binding.rvStickerList.layoutManager = GridLayoutManager(requireContext(), 5)

        var sellectName: String = viewModel.getStickerList()[stickerIndex].name
        var sellectIconImg: Int = recentIconImg

        adapter.itemClick = object : GridItemAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                sellectIconImg = position + 1
            }
        }

        binding.btnEdit.setOnClickListener {

            if (binding.etStickerName.text.isEmpty()) {
                Toast.makeText(requireContext(), "스티커 이름을 입력하세요", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            } else if (binding.etStickerName.text.toString().length > 9) {
                Toast.makeText(requireContext(), "최대 8글자 까지 입력할 수 있습니다", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            } else if (sellectIconImg == 0) {
                Toast.makeText(requireContext(), "스티커 아이콘을 선택하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            sellectName = binding.etStickerName.text.toString()
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->
                stickers[stickerIndex].name = sellectName
                stickers[stickerIndex].icon = sellectIconImg
                stickers[stickerIndex].isDelete = false
            })
            viewModel.updateStickers()

            val resultBundle = bundleOf("dataSend" to "dataSend")
            setFragmentResult("dataSend", resultBundle)

            requireActivity().onBackPressed()
        }

        binding.ivIcClose.setOnClickListener {
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