package com.binanceclient.inf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InfViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is information Fragment"
    }
    val text: LiveData<String> = _text
}