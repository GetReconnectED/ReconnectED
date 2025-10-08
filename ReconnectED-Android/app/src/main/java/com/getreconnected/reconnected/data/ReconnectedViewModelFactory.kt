package com.getreconnected.reconnected.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.getreconnected.reconnected.ui.navigation.ReconnectedViewModel

class ReconnectedViewModelFactory(private val repository: ScreenTimeRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReconnectedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ReconnectedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
