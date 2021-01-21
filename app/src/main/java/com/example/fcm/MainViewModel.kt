package com.example.fcm

import android.widget.Toast
import androidx.lifecycle.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

class MainViewModel(
    private val activity: MainActivity
) : ViewModel() {
    private val _kotlinText = MutableLiveData<String>()
    val kotlinText
        get() = _kotlinText
    private val _androidText = MutableLiveData<String>()
    val androidText
        get() = _androidText
    private var isSubKotlin = false
    private var isSubAndroid = false

    init {
        _kotlinText.value = activity.resources.getString(R.string.topic_kotlin)
        _androidText.value = activity.resources.getString(R.string.topic_android)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            val token = task.result
            println("Token: $token")
        })
    }

    fun subscribeKotlin() {
        if (!isSubKotlin) {
            subscribeTo("kotlin")
            _kotlinText.value = activity.resources.getString(R.string.unsubscribe_kotlin)
            isSubKotlin = true
        } else {
            unsubscribeTo("kotlin")
            _kotlinText.value = activity.resources.getString(R.string.topic_kotlin)
            isSubKotlin = false
        }
    }

    fun subscribeAndroid() {
        if (!isSubAndroid) {
            subscribeTo("android")
            _androidText.value = activity.resources.getString(R.string.unsubscribe_android)
            isSubAndroid = true
        } else {
            unsubscribeTo("android")
            _androidText.value = activity.resources.getString(R.string.topic_android)
            isSubAndroid = false
        }
    }

    private fun subscribeTo(topic: String) {
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = activity.resources.getString(R.string.msg_subscribed)

                if (!task.isSuccessful) {
                    msg = activity.resources.getString(R.string.msg_subscribe_failed)
                }
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            }
    }

    private fun unsubscribeTo(topic: String) {
        Firebase.messaging.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                var msg = activity.resources.getString(R.string.msg_unsubscribed)

                if (!task.isSuccessful) {
                    msg = activity.resources.getString(R.string.msg_unsubscribe_failed)
                }
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            }
    }
}