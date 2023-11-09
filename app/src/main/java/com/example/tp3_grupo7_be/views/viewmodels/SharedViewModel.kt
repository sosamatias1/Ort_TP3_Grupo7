package com.example.tp3_grupo7_be.views.viewmodels

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _isDarkMode = MutableLiveData<Boolean>()
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    fun setUsername(username: String) {
        _username.value = username

    }



}