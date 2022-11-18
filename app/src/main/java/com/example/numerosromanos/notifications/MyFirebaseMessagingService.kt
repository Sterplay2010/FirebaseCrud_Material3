package com.example.numerosromanos.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.numerosromanos.MainActivity
import com.example.numerosromanos.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService:FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.isNotEmpty()){
            println("Log Notification: ${message.data.get("title")}")
            createNotification(message.data.get("title").toString())
        }
        message.notification?.let {
            println("Log Notification: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    fun createNotification(message:String){
        var intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pending = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        var canal = getString(R.string.app_name)
        var sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notification = NotificationCompat.Builder(this,canal)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("App Romanos")
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(sound)
            .setContentIntent(pending)
        var notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var channelTwo = NotificationChannel(canal,getString(R.string.app_name),NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channelTwo)
        }
        notificationManager.notify(0,notification.build())
    }

}