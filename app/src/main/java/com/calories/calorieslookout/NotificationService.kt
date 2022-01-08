package com.calories.calorieslookout

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.calories.calorieslookout.LoginActivity
import com.calories.calorieslookout.MainActivity
import com.calories.calorieslookout.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "notification_name"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class NotificationService: FirebaseMessagingService() {

//    val intent = Intent(this, LoginActivity::class.java).apply {
//        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
//
//    val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
//
//    var builder = NotificationCompat.Builder(this, channelId)
//        .setSmallIcon(R.drawable.calories_logo)
//        .setContentTitle("MyCalories")
//        .setContentText("test")
//        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//        .setContentIntent(pendingIntent)
//        .setAutoCancel(true)
//
//
//    private fun createNotificationChannel() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = getString(R.string.channel_name)
//            val descriptionText = getString(R.string.channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(0, builder.build())
//        }
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.getNotification() != null){
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }

    fun getRemoteView(title: String, message: String): RemoteViews{
        val remoteView = RemoteViews("com.calories.calorieslookout", R.layout.notification)

        remoteView.setTextViewText(R.id.notificationtitle, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.logo, R.drawable.calories_logo)

        return remoteView
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification(title: String, message: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // FLAG_ONE_SHOT means we will use pendingIntent only once
        // opening content of the notification using pendingIntent
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.calories_logo)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)


        builder = builder.setContent(getRemoteView(title, message))

        // Enter the channel with the system
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0,builder.build())

    }

}















