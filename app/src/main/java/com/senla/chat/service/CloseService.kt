package com.senla.chat.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.senla.chat.App
import com.senla.chat.R
import com.senla.chat.presentation.MainActivity
import com.senla.chat.presentation.fragments.chat.ChatFragment
import com.senla.chat.presentation.fragments.chat.ChatViewModel
import com.senla.chat.presentation.fragments.utils.PreferenceManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class CloseService : Service() {
    @Inject
    lateinit var database: FirebaseFirestore
    private var preferenceManager: PreferenceManager? = null
    private var id: String? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        (applicationContext as App).appComponent.inject(this)
        preferenceManager = PreferenceManager(applicationContext)
        id = preferenceManager?.getString(ChatViewModel.KEY_USER_ID_IN_DB)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        deleteUser(id!!)
        super.onDestroy()
    }

    private fun deleteUser(idUserInDb: String) {
        database.collection(ChatViewModel.KEY_COLLECTION_USERS)
            .document(idUserInDb)
            .delete()
            .addOnSuccessListener { Log.d("TAGG", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.d("TAGG", "Error deleting document", e) }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_LOW
                )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
            createNotification()
        } else {
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this)
                .setContentTitle(resources.getString(R.string.app_name))
                .setSmallIcon(R.mipmap.ic_app)
            val notification = builder.build()
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notification)
        }
    }

    private fun createNotification() {
       val notificationIntent = Intent(this, ChatFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(resources.getString(R.string.app_name))
            .setSmallIcon(R.mipmap.ic_app)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
    }

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}