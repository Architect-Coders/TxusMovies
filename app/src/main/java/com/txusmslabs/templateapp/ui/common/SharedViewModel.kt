package com.txusmslabs.templateapp.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>> get() = _navigateToLogin

    fun logout() {
        _navigateToLogin.postValue(Event(true))
    }
}