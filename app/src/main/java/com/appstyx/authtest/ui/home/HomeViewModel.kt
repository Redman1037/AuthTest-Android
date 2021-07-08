package com.appstyx.authtest.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appstyx.authtest.common.Constants
import com.appstyx.authtest.common.Event
import com.appstyx.authtest.models.UserResponse
import com.appstyx.authtest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rest.restmodels.CommonResponse
import rest.restutils.ApiException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    val commonEvent = Event<Int>()

    private var _userResponse = Event<UserResponse?>()
    val userResponse: LiveData<UserResponse?>
        get() = _userResponse


    private var _userProfileApiException = Event<CommonResponse>()
    val userProfileApiException: LiveData<CommonResponse>
        get() = _userProfileApiException


    fun onLogoutClick() {
        _userResponse.value=null
    }


    fun getMyProfile()=viewModelScope.launch {
        try {
            commonEvent.value=Constants.EVENT_SHOW_PROGRESS
            val userResponse=mainRepository.getProfile()
            _userResponse.value=userResponse
            commonEvent.value= Constants.EVENT_DISMISS_PROGRESS
        }catch (e: ApiException){
            e.printStackTrace()
            _userProfileApiException.value=e.commonResponse
            commonEvent.value= Constants.EVENT_DISMISS_PROGRESS
        }
    }

}