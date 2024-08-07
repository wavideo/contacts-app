package com.example.nugo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import com.example.nugo.contact.ContactData
import com.example.nugo.contact.ContactListFragment
import com.example.nugo.contact.ContactManager
import com.example.nugo.databinding.FragmentListAddFirstStickerBinding
import com.example.nugo.sticker.NewStickerDialogueFragment
import com.example.nugo.sticker.StickerManager

private const val ARG_CONTACT_DATA = "CONTACT_DATA"

class ListAddFirstStickerFragment : Fragment() {
    private lateinit var contact: ContactData

    private val binding by lazy { FragmentListAddFirstStickerBinding.inflate(layoutInflater) }
    private val viewModel by activityViewModels<SharedViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact = it.getParcelable(ARG_CONTACT_DATA) ?: run {
                Log.e(TAG, "contact is null")
                parentFragmentManager.popBackStack()
                return
            }
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



        // 닫기 버튼
        binding.ivCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        fun update() {
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->

            binding.ivListAddFirstSticker1.setImageResource(StickerManager.icons[stickers[0].icon])
            binding.ivListAddFirstSticker2.setImageResource(StickerManager.icons[stickers[1].icon])
            binding.ivListAddFirstSticker3.setImageResource(StickerManager.icons[stickers[2].icon])
            binding.ivListAddFirstSticker4.setImageResource(StickerManager.icons[stickers[3].icon])
            binding.ivListAddFirstSticker5.setImageResource(StickerManager.icons[stickers[4].icon])

            binding.tvListAddFirstSticker1Name.text = stickers[0].name
            binding.tvListAddFirstSticker2Name.text = stickers[1].name
            binding.tvListAddFirstSticker3Name.text = stickers[2].name
            binding.tvListAddFirstSticker4Name.text = stickers[3].name
            binding.tvListAddFirstSticker5Name.text = stickers[4].name
            })
        }
        update()

        binding.ivListAddFirstSticker1.setOnClickListener {
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->

                if (stickers[0].isDelete) {
                    val fragment = NewStickerDialogueFragment.newInstance(0)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.cv_popup_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    setFragmentResultListener("dataSend") { key, bundle ->
                        update()
                    }
                } else {
                    contact.recentSticker = 0
                    contact.sticker0 += 1
                    viewModel.editContactData(contact)
                    Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            })

        }

        binding.ivListAddFirstSticker2.setOnClickListener {
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->
                if (stickers[1].isDelete) {
                    val fragment = NewStickerDialogueFragment.newInstance(1)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.cv_popup_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    setFragmentResultListener("dataSend") { key, bundle ->
                        update()
                    }
                } else {
                    contact.recentSticker = 1
                    contact.sticker1 += 1
                    viewModel.editContactData(contact)
                    Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()

                }
            })
        }

        binding.ivListAddFirstSticker3.setOnClickListener {
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->
                if (stickers[2].isDelete) {
                    val fragment = NewStickerDialogueFragment.newInstance(2)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.cv_popup_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    setFragmentResultListener("dataSend") { key, bundle ->
                        update()
                    }
                } else {
                    contact.recentSticker = 2
                    contact.sticker2 += 1
                    viewModel.editContactData(contact)
                    Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()

                }
            })
        }

        binding.ivListAddFirstSticker4.setOnClickListener {
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->
                if (stickers[3].isDelete) {
                    val fragment = NewStickerDialogueFragment.newInstance(3)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.cv_popup_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    setFragmentResultListener("dataSend") { key, bundle ->
                        update()
                    }
                } else {
                    contact.recentSticker = 3
                    contact.sticker3 += 1
                    viewModel.editContactData(contact)
                    Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()

                }
            })
        }

        binding.ivListAddFirstSticker5.setOnClickListener {
            viewModel.stickers.observe(viewLifecycleOwner, Observer { stickers ->
                if (stickers[4].isDelete) {
                    val fragment = NewStickerDialogueFragment.newInstance(4)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.cv_popup_container, fragment)
                        .addToBackStack(null)
                        .commit()
                    setFragmentResultListener("dataSend") { key, bundle ->
                        update()
                    }
                } else {
                    contact.recentSticker = 4
                    contact.sticker4 += 1
                    viewModel.editContactData(contact)
                    Toast.makeText(binding.root.context, "스티커가 추가되었습니다", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }
            })
        }

    }


    companion object {
        private const val TAG = "ListAddFirstStickerFragment"
        @JvmStatic
        fun newInstance(data: ContactData) =
            ListAddFirstStickerFragment().apply {
                arguments = bundleOf(ARG_CONTACT_DATA to data)
            }
    }


}