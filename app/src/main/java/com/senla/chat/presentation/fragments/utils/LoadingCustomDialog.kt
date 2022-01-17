package com.senla.chat.presentation.fragments.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.widget.TextView
import androidx.navigation.NavController
import com.senla.chat.R
import com.senla.chat.service.CloseService

class LoadingCustomDialog(private val mActivity: Activity,private val navController: NavController) {
    private var isDialog: AlertDialog? = null


    fun startLoading() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)
        dialogView.setBackgroundResource(R.drawable.background_loading_custom_dialog)

        val builder = AlertDialog.Builder(mActivity, R.style.MyDialog)
        builder.setView(dialogView)
        builder.setCancelable(true)
        builder.setOnCancelListener {
            navController.navigate(R.id.termsFragment)
            Intent(mActivity, CloseService::class.java).also { intent ->
                mActivity.stopService(intent)
            }
        }
        isDialog = builder.create()
        isDialog?.show()

        val dialogButton1: TextView? = isDialog?.findViewById(R.id.tv_change_choice)
        dialogButton1?.setOnClickListener {
            navController.navigate(R.id.termsFragment)
            Intent(mActivity, CloseService::class.java).also { intent ->
                mActivity.stopService(intent)
            }
//            Toast.makeText(
//                mActivity.applicationContext,
//                "Нажал изменить критерии",
//                Toast.LENGTH_SHORT
//            ).show()
            isDialog?.dismiss()
        }

//        val dialogButton2: TextView? = isDialog?.findViewById(R.id.tv_repeat)
//        dialogButton2?.setOnClickListener {
//            Toast.makeText(mActivity.applicationContext, "Повтор", Toast.LENGTH_SHORT).show()
//            isDialog?.dismiss()
//        }
    }

    fun isDismiss() {
        isDialog?.dismiss()
    }
}