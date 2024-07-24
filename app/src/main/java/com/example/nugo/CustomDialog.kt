package com.example.nugo

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView



// A안(DialogFragment 사용하기, 황성희튜터님이 dialog보다는 dialgfragment가 좋을것같다고 추천하심.)
// androidx.fragment.app.DialogFragment 이거랑 DialogFragment 둘중에 뭐를 사용 해야하는지 모르겠음..
class CustomDialog : DialogFragment(){
    // Dialog의 host에게 Event를 전달하기 위한 리스너
    private  lateinit var listener: StopDialogListener

    //interface를 구현, 각 메서드는 Dialog를 파라미터로 전달

    interface StopDialogListener{
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // 스티커 수정완료 버튼
    private  lateinit var  positiveButton: Button
    // 스티커 나가기 버튼(오른쪽 상단위에 X모양 button일거라고 생각하고 button 넣음)
    private lateinit var  negativeButton: Button


    // Click Event 전달받기 위해서 필요 위에 inerface부터 tectview, button 까지 연관된 것
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            // 리스너 인스턴스화하여 host에게 event를 보냄
            listener = context as StopDialogListener
        } catch (e: ClassCastException){
            // interface가 구현되지 않았으면 하는 excetion thorw
            throw ClassCastException(
                (context.toString())
            )

        }
    }

    //Custom Dialog 실행 코드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정하면 화면 밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss되지 않음.
        isCancelable = true
    }

//    private lateinit var binding: CustomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = CustomDialogBinding.inflate(inflater,container,false)

//        val view = inflater.inflate(R.layout.dialog_sticker_detail)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        positiveButton.setOnClickListener{
            listener.onDialogPositiveClick(this)
        }
        negativeButton.setOnClickListener {
            listener.onDialogNegativeClick(this)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}


// B안(Dialog, 아래 참고용 자료)
//class CustomDialog(context: Context): Dialog(context) {
//    private lateinit var itemClickListener: ItemClickListener
//    private lateinit var binding: DialogCustomBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DialogCustomBinding.inflate(LayoutInflater.from(context))
//        setContentView(binding.root)
//
//        // 사이즈를 조절하고 싶을 때 사용 (use it when you want to resize dialog)
//        // resize(this, 0.8f, 0.4f)
//
//        // 배경을 투명하게 (Make the background transparent)
//        // 다이얼로그를 둥글게 표현하기 위해 필요 (Required to round corner)
//        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//
//        // 다이얼로그 바깥쪽 클릭시 종료되도록 함 (Cancel the dialog when you touch outside)
//        setCanceledOnTouchOutside(true)
//
//        // 취소 가능 유무
//        setCancelable(true)
//
//        binding.tvCancel.setOnClickListener {
//            dismiss() // 다이얼로그 닫기 (Close the dialog)
//        }
//
//        binding.tvCall.setOnClickListener {
//            // interface를 이용해 다이얼로그를 호출한 곳에 값을 전달함
//            // Use interface to pass the value to the activty or fragment
//            itemClickListener.onClick("031-467-0000")
//            dismiss()
//        }
//    }
//
//    // 사이즈를 조절하고 싶을 때 사용 (use it when you want to resize dialog)
//    private fun resize(dialog: Dialog, width: Float, height: Float){
//        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//
//        if (Build.VERSION.SDK_INT < 30) {
//            val size = Point()
//            windowManager.defaultDisplay.getSize(size)
//
//            val x = (size.x * width).toInt()
//            val y = (size.y * height).toInt()
//            dialog.window?.setLayout(x, y)
//        } else {
//            val rect = windowManager.currentWindowMetrics.bounds
//
//            val x = (rect.width() * width).toInt()
//            val y = (rect.height() * height).toInt()
//            dialog.window?.setLayout(x, y)
//        }
//    }
//
//    interface ItemClickListener {
//        fun onClick(message: String)
//    }
//
//    fun setItemClickListener(itemClickListener: ItemClickListener) {
//        this.itemClickListener = itemClickListener
//    }
//}