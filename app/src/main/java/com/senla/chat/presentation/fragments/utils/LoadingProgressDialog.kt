package com.senla.chat.presentation.fragments.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.navigation.NavController
import com.senla.chat.R
import com.senla.chat.service.CloseService

class LoadingProgressDialog(private val mActivity: Activity,private val navController: NavController) {
    private var isDialog: AlertDialog? = null

    fun startLoading() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom_progress, null)

        val builder = AlertDialog.Builder(mActivity)
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
    }

    fun isDismiss() {
        isDialog?.dismiss()
    }
}