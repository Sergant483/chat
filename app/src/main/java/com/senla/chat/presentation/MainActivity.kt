package com.senla.chat.presentation

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.senla.chat.R
import com.senla.chat.presentation.fragments.utils.LoadingCustomDialog
import com.senla.chat.presentation.fragments.utils.LoadingProgressDialog

class MainActivity : AppCompatActivity() {

    private lateinit var loadingCustomDialog: LoadingCustomDialog
    private lateinit var loadingProgressDialog: LoadingProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadingProgressDialog()
        loadingCustomDialog()



    }


    private fun loadingCustomDialog(){
        loadingCustomDialog = LoadingCustomDialog(this)
        loadingCustomDialog.startLoading()
    }

    private fun loadingProgressDialog(){
        loadingProgressDialog = LoadingProgressDialog(this)
        loadingProgressDialog.startLoading()
    }

}