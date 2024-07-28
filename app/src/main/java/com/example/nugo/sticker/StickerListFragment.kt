package com.example.nugo.sticker

/* [ 정호정 파트 ]
연락처 상세정보에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 StickerData를 통해 연락처 정보를 읽고 씁니다
2. 스티커 디테일 (StickerDetailFragment)로 선택한 스티커를 넘겨줍니다*/

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.R
import com.example.nugo.SharedViewModel
import com.example.nugo.databinding.FragmentStickerListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StickerListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StickerListFragment : Fragment() {

    //    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    private val binding by lazy { FragmentStickerListBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
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




            val contacts = viewModel.getContactList()
            val stickerAdapter = StickerAdapter(viewModel.getStickerList(), contacts, viewModel, viewLifecycleOwner)
            binding.rvStickerList.adapter = stickerAdapter
            binding.rvStickerList.layoutManager = LinearLayoutManager(requireContext())

            // itemClick.onClick 추상메서드 정의
            stickerAdapter.itemClick = object : StickerAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
                    // StickerDetailFragment로 넘어가야합니다

                        if (viewModel.getStickerList()[position].isDelete == true) {
                            val fragment = NewStickerDialogueFragment.newInstance(position)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.cv_popup_container, fragment)
                                .addToBackStack(null)
                                .commit()

                        } else {
                            val fragment = StickerDetailFragment.newInstance(position)

                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frameLayout, fragment)
                                .addToBackStack(null)
                                .commit()
                        }
                        setFragmentResultListener("dataSend") { key, bundle ->
                            stickerAdapter.updateData(viewModel.getStickerList())
                        }
                }
            }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StickerListFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            StickerListFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
    }
}