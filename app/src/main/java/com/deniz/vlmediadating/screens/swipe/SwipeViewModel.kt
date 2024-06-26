package com.deniz.vlmediadating.screens.swipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deniz.vlmediadating.api.API
import com.deniz.vlmediadating.api.data.User
import com.deniz.vlmediadating.utils.Constants
import com.deniz.vlmediadating.utils.VLDatingLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SwipeViewModel : ViewModel() {

    private var currentPage: Int? = Constants.API_START_PAGE

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            try {
                val response = API.getUsers(currentPage ?: Constants.API_START_PAGE)
                val nextKey = response.info.next?.substringAfter("page=")?.toInt()
                currentPage = nextKey
                _users.value = response.results
            } catch (e: Exception) {
                VLDatingLogger.logError(e.message ?: "Exception - fetchCharacters")
                delay(2500)
                fetchCharacters()
            }
        }
    }
}