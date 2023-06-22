package com.narvatov.datingapp.ui.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.halfapum.general.coroutines.Dispatcher
import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.preference.NotificationPreferencesDataStore
import com.narvatov.datingapp.utils.inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Random

class MessagingService : FirebaseMessagingService() {

    private val dispatcher: Dispatcher by inject()

    private val job = SupervisorJob()
    private val scope = CoroutineScope(dispatcher.IO + job)

    private val notificationPreferencesDataStore: NotificationPreferencesDataStore by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val context = this

        scope.launch {
            val notificationPreference = notificationPreferencesDataStore.get()

            if (notificationPreference.isNotAllowed()) return@launch

            val userName = remoteMessage.data["name"]
            val message = remoteMessage.data["message"]

            val notificationId = Random().nextInt()
            val channelId = "chat_message"

            val builder = NotificationCompat.Builder(context, channelId).apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentTitle(userName)
                setContentText(message)
                setStyle(NotificationCompat.BigTextStyle().bigText(message))
                priority = NotificationCompat.PRIORITY_DEFAULT
                setAutoCancel(true)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "Chat Message"
                val channelDescription =
                    "This notification channel is used for message notifications"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, channelName, importance)
                channel.description = channelDescription
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }

            val notificationManagerCompat = NotificationManagerCompat.from(context)
            notificationManagerCompat.notify(notificationId, builder.build())
        }
    }

}