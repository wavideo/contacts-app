package com.example.nugo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.databinding.FragmentAddStickerDialogBinding
import com.example.nugo.databinding.FragmentContactDetailBinding


private const val ARG_PARAM1 = "param1"

class AddStickerDialogFragment : Fragment() {

    private var param1: String? = null

    private lateinit var adapter : AddStickerDialogAdapter
    private var _binding: FragmentAddStickerDialogBinding? = null
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
        _binding = FragmentAddStickerDialogBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AddStickerDialogAdapter(StickerManager.stickers) // adapter 초기화

        binding.recyclerviewAddsticker.adapter = adapter
        binding.recyclerviewAddsticker.layoutManager = LinearLayoutManager(this.requireContext())

        // 스티커 목록 내 스티커 선택 시 동작
        adapter.itemClick = object : AddStickerDialogAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {

//                Toast.makeText(this, "스티커가 추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }


//        binding.v .setOnClickListener{
//            Toast.makeText(this.requireContext(), "스티커가 추가되었습니다.", Toast.LENGTH_SHORT).show()
//        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            AddStickerDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}