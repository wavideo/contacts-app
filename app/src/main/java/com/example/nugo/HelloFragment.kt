package com.example.nugo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.nugo.databinding.FragmentHelloBinding
import com.example.nugo.sticker.StickerData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HelloFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelloFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val binding by lazy { FragmentHelloBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

        binding.rvStickerList.setOnClickListener{
            viewModel.editStickerDataByIndex(0, StickerData("고마워",1, false))
            viewModel.editStickerDataByIndex(1, StickerData("밥 사주신 분들",16, false))
            viewModel.editStickerDataByIndex(2, StickerData("생일 챙겨주신분",19, false))
            viewModel.editStickerDataByIndex(3, StickerData("도움을 준 은인",20, false))
            viewModel.editStickerDataByIndex(4, StickerData("새 스티커 생성",0, true))
            parentFragmentManager.popBackStack()
        }

        binding.rvStickerList2.setOnClickListener{
            viewModel.editStickerDataByIndex(0, StickerData("경고",33, false))
            viewModel.editStickerDataByIndex(1, StickerData("지각한 사람",13, false))
            viewModel.editStickerDataByIndex(2, StickerData("기분 나쁜 말투",7, false))
            viewModel.editStickerDataByIndex(3, StickerData("스타일이 별로야",34, false))
            viewModel.editStickerDataByIndex(4, StickerData("새 스티커 생성",0, true))
            parentFragmentManager.popBackStack()
        }

        binding.rvStickerList3.setOnClickListener{
            viewModel.editStickerDataByIndex(0, StickerData("고마워",1, false))
            viewModel.editStickerDataByIndex(1, StickerData("경고",33, false))
            viewModel.editStickerDataByIndex(2, StickerData("밥 사주신 분들",16, false))
            viewModel.editStickerDataByIndex(3, StickerData("지각한 사람",13, false))
            viewModel.editStickerDataByIndex(4, StickerData("새 스티커 생성",0, true))
            parentFragmentManager.popBackStack()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HelloFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HelloFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}