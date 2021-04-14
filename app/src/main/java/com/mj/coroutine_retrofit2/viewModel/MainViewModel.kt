package com.mj.coroutine_retrofit2.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mj.coroutine_retrofit2.api.RetrofitObject
import com.mj.coroutine_retrofit2.response.UserResponse
import kotlinx.coroutines.*

class MainViewModel : ViewModel() {

    var userData: MutableLiveData<UserResponse> = MutableLiveData()

    private val coroutineExceptionHanlder =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            throwable.printStackTrace()
        }

    private val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder
    private val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder


    init {
        getUser()
    }


    private fun getUser() {

        var tmpData: UserResponse? = null

        viewModelScope.launch(ioDispatchers) {

            try {
                RetrofitObject.getApiService().getUserData().apply {

                    if (this.age.isNotEmpty() && this.name.isNotEmpty()) {
                        tmpData = this
                    }

                    withContext(uiDispatchers) {
                        userData.value = tmpData
                    }
                }
            } catch (e: Exception) {

            }
        }
    }
}