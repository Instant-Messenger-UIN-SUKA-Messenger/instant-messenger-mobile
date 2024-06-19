package com.tegas.instant_messenger_mobile.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tegas.instant_messenger_mobile.data.ChatRepository
import com.tegas.instant_messenger_mobile.data.Result
import com.tegas.instant_messenger_mobile.data.UserModel
import com.tegas.instant_messenger_mobile.data.retrofit.response.ChatsItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ChatRepository) : ViewModel() {

    private val _mainViewModel = MediatorLiveData<Result<List<ChatsItem>>>()
    val mainViewModel: LiveData<Result<List<ChatsItem>>> = _mainViewModel

    fun getChatList(nim: String) {
        val liveData = repository.getChatList(nim)
        _mainViewModel.addSource(liveData) { result ->
            _mainViewModel.value = result
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    class Factory(private val repository: ChatRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(repository) as T

    }
}