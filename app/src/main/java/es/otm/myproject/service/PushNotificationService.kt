package es.otm.myproject.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import es.otm.myproject.MainActivity
import es.otm.myproject.R
import es.otm.myproject.SettingsActivity
import es.otm.myproject.fragments.AudioFragment

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class PushNotificationService(private val context: Context) : FirebaseMessagingService() {

    private var pref : SharedPreferences = context.getSharedPreferences("es.otm.myproject_preferences", Context.MODE_PRIVATE)

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.notification != null){
            val title = message.notification!!.title
            val body = message.notification!!.body

            Log.d(
                "TAG",
                "Notification with title $title and body $body"
            )
            mostrarNotificacion(title, body)
        }
    }

    fun mostrarNotificacion(title: String?, content: String?){
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        var notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.login)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        manager.notify(1, notificationBuilder.build())

        val vibrar = pref.getBoolean(SettingsActivity.VIBRAR, false)

        if (vibrar){
            vibrateDevice()
        }
    }

    fun vibrateDevice(){
        var vibrator: Vibrator
        if (Build.VERSION.SDK_INT >= 31){
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrator = vibratorManager.defaultVibrator
        }
        else{
            @Suppress("DEPRECATION")
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        val mVibratePattern = longArrayOf(0, 400, 200, 400)
        if (Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createWaveform(mVibratePattern, -1))
        }
        else{
            @Suppress("DEPRECATION")
            vibrator.vibrate(mVibratePattern, -1)
        }
    }


    companion object{
        const val CHANNEL_ID = "FCM CID"
        const val CHANNEL_NAME = "FCM notification channel"
    }
}