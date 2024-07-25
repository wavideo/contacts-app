package com.example.nugo
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.nugo.databinding.ItemGridBinding
//
//class GridItemAdapter : RecyclerView.Adapter<GridItemAdapter.Holder>() {
//    class Holder(val binding = ItemGridBinding) : RecyclerView.ViewHolder(binding.root) {
//        val gridItem = binding.ivBind
//
//        fun onBind(item: GridItem) {
//            gridItem.setImageResource(item.img)
//        }
//    }
//
//    // griditem 객체 저장 리스트
//    private val listData: ArrayList<GridItem> = ArrayList()
//    val icons = StickerManager.icons
//    class GridItem(var img: Int)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
//        return GridItemViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return .size
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//        holder.onBind(listData[position])
//    }
//
//}


