package com.senla.chat.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.senla.chat.R
import com.senla.chat.service.CloseService


class MainActivity : AppCompatActivity() {
    private val navController
        get() =
            (supportFragmentManager.findFragmentById(findViewById<View>(R.id.nav_host_fragment).id) as NavHostFragment).navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        stopService()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.chatFragment) {
           stopService()
        }
        super.onBackPressed()
    }

    private fun stopService(){
        Intent(this, CloseService::class.java).also { intent ->
            this.stopService(intent)
        }
    }

}