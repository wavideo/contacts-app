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
//    private var _binding: FragmentContactListBinding? = null
//    private val binding get() = _binding!!
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

        val adapter = ContactListAdapter(ContactManager.Contacts) // dataList 생성


        binding.recycleListView.adapter = adapter
        binding.recycleListView.layoutManager = LinearLayoutManager(this.requireContext())

        adapter.itemClick = object : ContactListAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
//                Toast.makeText(this.requireContext(), "${view.tvName}이 클릭되었습니다.", Toast.LENGTH_SHORT)
//                    .show()
                val dataToSend = position
                val fragmentContactDetail = ContactDetailFragment.newInstance(dataToSend.toString())
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.topView, fragmentContactDetail)
                    .addToBackStack(null)
                    .commit()
            }
        }

//        val adapter = ContactListAdapter(dataList, // dataList 생성
//            itemClickListener = { item, position ->
//                Toast.makeText(this.requireContext(), "${item.tvName}이 클릭되었습니다.", Toast.LENGTH_SHORT).show()
//                val dataToSend = position
//                val fragmentContactDetail = ContactDetailFragment.newInstance(dataToSend.toString())
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .replace(R.id.listView, fragmentContactDetail)
//                    .addToBackStack(null)
//                    .commit()
//            })


    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

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

//        class ContactListFragment : AppCompatActivity() {
//
//            private lateinit var binding: FragmentContactListBinding
//
//
//            override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//
//                enableEdgeToEdge()
//
//                binding = FragmentContactListBinding.inflate(layoutInflater)
//
//                setContentView(binding.root)
//
//                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//                    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                    v.setPadding(
//                        systemBars.left,
//                        systemBars.top,
//                        systemBars.right,
//                        systemBars.bottom
//                    )
//                    insets
//                }
//                //데이터 원본 준비
//                val dataList = mutableListOf<ContactListItem>()
//                dataList.add(ContactListItem("김은택", "010-1111-1111", "aaa@naver.com"))
//
//                val adapter = ContactListAdapter(dataList, // dataList 생성
//                    itemClickListener = { item, position ->
//                        Toast.makeText(this, "${item.tvName} clicked", Toast.LENGTH_SHORT).show()
//                        var nameIntent = Intent(this@ContactListFragment, StickerListFragment::class.java)
//                        nameIntent.putExtra("name", item.tvName)
//                        startActivity(nameIntent)
//                    })
//                binding.recycleListView.adapter = adapter
//                binding.recycleListView.layoutManager = LinearLayoutManager(this@ContactListFragment)
//
//            }
//        }
    }
}