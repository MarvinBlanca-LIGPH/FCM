package com.example.fcm

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

object FirebaseSingleton {
    private val firebaseTopic = Firebase.messaging
    private val firebaseMessaging = FirebaseMessaging.getInstance()

    fun getToken() {
        firebaseMessaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            val token = task.result.toString()
            println("Token: $token")
        })
    }

    fun subscribe(topic: String) {
        firebaseTopic.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = R.string.msg_subscribed.toString()

                if (!task.isSuccessful) {
                    msg = R.string.msg_subscribe_failed.toString()
                }
                println(msg)
            }
    }

    fun unsubscribe(topic: String) {
        firebaseTopic.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                var msg = R.string.msg_subscribed.toString()

                if (!task.isSuccessful) {
                    msg = R.string.msg_subscribe_failed.toString()
                }
                println(msg)
            }
    }
}