package com.umc.android.packit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.umc.android.packit.databinding.OrderCancelDialogBinding

//주문 취소 다이얼로그
class OrderCancelDialog : DialogFragment() {

    private lateinit var binding:OrderCancelDialogBinding
    private lateinit var listener: MyDialogOKClickedListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding =OrderCancelDialogBinding.inflate(LayoutInflater.from(requireContext()))

        val dlg = Dialog(requireActivity())
        dlg.setContentView(binding.root)

        //주문 취소 확인 버튼 클릭 이벤트 처리 (주문 취소하시겠습니까? -확인)
        binding.okBtn.setOnClickListener {
            listener.onOKClicked("정상적으로 주문 취소되었습니다.")
            dlg.dismiss()
            //환불 처리 완료 - 주문내역 화면으로 이동
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val orderHistoryFragment = OrderHistoryFragment()
            transaction.replace(R.id.fl_basic, orderHistoryFragment)
            transaction.commit()
        }

        //주문 취소하지 않기로 선택함 이벤트 처리 (주문 취소하시겠습니까? -취소)
        binding.cancelBtn.setOnClickListener {
            dlg.dismiss()
        }

        return dlg
    }

    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object : MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }

    interface MyDialogOKClickedListener {
        fun onOKClicked(content: String)
    }
}
