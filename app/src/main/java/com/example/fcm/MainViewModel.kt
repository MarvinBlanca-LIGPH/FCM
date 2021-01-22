package com.example.fcm

import androidx.lifecycle.*

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

        FirebaseSingleton.getToken()
    }

    fun subscribeKotlin() {
        if (!isSubKotlin) {
            FirebaseSingleton.subscribe("kotlin")
            _kotlinText.value = activity.resources.getString(R.string.unsubscribe_kotlin)
            isSubKotlin = true
        } else {
            FirebaseSingleton.unsubscribe("kotlin")
            _kotlinText.value = activity.resources.getString(R.string.topic_kotlin)
            isSubKotlin = false
        }
    }

    fun subscribeAndroid() {
        if (!isSubAndroid) {
            FirebaseSingleton.subscribe("android")
            _androidText.value = activity.resources.getString(R.string.unsubscribe_android)
            isSubAndroid = true
        } else {
            FirebaseSingleton.unsubscribe("android")
            _androidText.value = activity.resources.getString(R.string.topic_android)
            isSubAndroid = false
        }
    }
}