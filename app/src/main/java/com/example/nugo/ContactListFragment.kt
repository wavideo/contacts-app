package com.example.nugo

/* [ 허민 파트 ]
연락처 리스트에 대한 Fragment 입니다

<다른 파일과 연계되는 부분>
1. 데이터 클래스 ContactData를 통해 연락처 정보를 읽고 씁니다
2. 연락처 상세정보 (ContactDetailFragment)로 선택한 연락처를 넘겨줍니다*/

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Contacts
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nugo.databinding.FragmentContactListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val binding by lazy { FragmentContactListBinding.inflate(layoutInflater) }
    private lateinit var adapter : ContactListAdapter
    //    private var _binding: FragmentContactListBinding? = null
//    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 1
    private var param1: String? = null
    private var param2: String? = null

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
//        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        adapter = ContactListAdapter(ContactManager.Contacts) // adapter 초기화
        binding.recycleListView.adapter = adapter
        binding.recycleListView.layoutManager = LinearLayoutManager(this.requireContext())

        adapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val dataToSend = position
                val fragmentContactDetail = ContactDetailFragment.newInstance(dataToSend.toString())
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragmentContactDetail)
                    .addToBackStack(null)
                    .commit()
            }
        }

        // 연락처 추가 intent
        binding.addFriend.setOnClickListener {
            val intent_addFriend = Intent(requireContext(), AddFriendActivity::class.java)
            startActivity(intent_addFriend)
//            adapter.notifyDataSetChanged() // 전부 다 수정(대규모 데이터 처리 비추)
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.updateData(ContactManager.Contacts)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactListFragment().apply {
                var arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}