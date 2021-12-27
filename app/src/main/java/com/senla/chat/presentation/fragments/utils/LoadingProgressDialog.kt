package com.senla.chat.presentation.fragments.utils

import android.app.Activity
import android.app.AlertDialog
import com.senla.chat.R

class LoadingProgressDialog(private val mActivity: Activity) {
    private lateinit var isDialog: AlertDialog

    fun startLoading() {
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom_progress, null)

        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun isDismiss() {
        isDialog.dismiss()
    }
}