//package com.example.nugo
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.recyclerview.widget.RecyclerView
//
//class GridItemAdapter : RecyclerView.Adapter<GridItemAdapter.GridItemViewHolder>() {
//    // griditem 객체 저장 리스트
//    private val listData: ArrayList<GridItem> = ArrayList()
//
//    val icons = intArrayOf(
//        R.drawable.ic_add_circle,
//        R.drawable.imoge01_thumbsup,
//        R.drawable.imoge02_slightlysmilingface,
//        R.drawable.imoge03_smilingfacewithhearts,
//        R.drawable.imoge04_smilingfacewithsunglasses,
//        R.drawable.imoge05_partyingface,
//        R.drawable.imoge06_thumbsdown,
//        R.drawable.imoge07_confusedface,
//        R.drawable.imoge08_grinningfacewithsweat,
//        R.drawable.imoge09_loudlycryingface,
//        R.drawable.imoge10_poutingface,
//        R.drawable.imoge11_telephonereceiver,
//        R.drawable.imoge12_speechballoon,
//        R.drawable.imoge13_alarmclock,
//        R.drawable.imoge14_moneywithwings,
//        R.drawable.imoge15_handshake,
//        R.drawable.imoge16_cookedrice,
//        R.drawable.imoge17_hotbeverage,
//        R.drawable.imoge18_shoppingbags,
//        R.drawable.imoge19_partypopper,
//        R.drawable.imoge20_foldedhands,
//        R.drawable.imoge21_house,
//        R.drawable.imoge22_automobile,
//        R.drawable.imoge23_babylightskintone,
//        R.drawable.imoge24_dogface,
//        R.drawable.imoge25_catface,
//        R.drawable.imoge26_hollowredcircle,
//        R.drawable.imoge27_star,
//        R.drawable.imoge28_redheart,
//        R.drawable.imoge29_lightbulb,
//        R.drawable.imoge30_fire,
//        R.drawable.imoge31_crossmark,
//        R.drawable.imoge32_warning,
//        R.drawable.imoge33_policecarlight,
//        R.drawable.imoge34_pileofpoo,
//        R.drawable.imoge35_bomb
//    )
//
//    class GridItem(var img: Int)
//
//    // 뷰의 데이터 설정
//    class GridItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val gridItem: ImageView = view.findViewById(R.id.iv_sticker_griditem)
//
//        fun onBind(item: GridItem) {
//            gridItem.setImageResource(item.img)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemViewHolder {
//        val view: View =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
//        return GridItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: GridItemViewHolder, position: Int) {
//        holder.onBind(listData[position])
//    }
//
//}
//
//
