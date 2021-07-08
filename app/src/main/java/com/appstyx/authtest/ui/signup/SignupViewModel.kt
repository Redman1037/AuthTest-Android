package com.appstyx.authtest.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appstyx.authtest.common.Constants
import com.appstyx.authtest.common.Event
import com.appstyx.authtest.common.isNotNullOrEmpty
import com.appstyx.authtest.models.Gender
import com.appstyx.authtest.models.GenderResponse
import com.appstyx.authtest.models.SignUpResponse
import com.appstyx.authtest.models.User
import com.appstyx.authtest.repository.MainRepository
import com.appstyx.authtest.ui.main.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rest.restmodels.CommonResponse
import rest.restutils.ApiException
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {

    private var _genderResponse = Event<GenderResponse>()
    val genderResponse: LiveData<GenderResponse>
        get() = _genderResponse

    private var _signUpResponse = Event<SignUpResponse>()
    val signUpResponse: LiveData<SignUpResponse>
        get() = _signUpResponse


    private var _genderApiException = Event<CommonResponse>()
    val genderApiException: LiveData<CommonResponse>
        get() = _genderApiException

    private var _signUpApiException = Event<CommonResponse>()
    val signUpApiException: LiveData<CommonResponse>
        get() = _signUpApiException


    private var _errorEvent = Event<Int>()
    val errorEvent: LiveData<Int>
        get() = _errorEvent

    val commonEvent = Event<Int>()

    var user = User()
    private var genderList: List<Gender>? = null


    fun onSignupClick() {
        validateUser(user)
    }

    private fun validateUser(user: User){

        if (user.email.isNullOrBlank()){
            _errorEvent.value=Constants.ERROR_EVENT_EMPTY_EMAIL
        }else if(!isValidEmail(user.email!!)){
            _errorEvent.value=Constants.ERROR_EVENT_INVALID_EMAIL
        }else if (user.firstName.isNullOrBlank()){
            _errorEvent.value=Constants.ERROR_EVENT_EMPTY_NAME
        }else if (user.firstName!!.length<2){
            _errorEvent.value=Constants.ERROR_EVENT_INVALID_NAME
        }else if (user.lastName.isNullOrBlank()){
            _errorEvent.value=Constants.ERROR_EVENT_EMPTY_LAST_NAME
        }else if (user.lastName!!.length<2){
            _errorEvent.value=Constants.ERROR_EVENT_INVALID_LAST_NAME
        }else if (user.gender.isNullOrBlank()){
            _errorEvent.value=Constants.ERROR_EVENT_EMPTY_GENDER
        }else{
            commonEvent.value=Constants.EVENT_SHOW_PROGRESS
            signUpUser(user)
        }

    }


    fun getGenders() = viewModelScope.launch {
        try {
            val genderResponse = mainRepository.getGender()
            _genderResponse.value = genderResponse
        } catch (e: ApiException) {
            e.printStackTrace()
            _genderApiException.value = e.commonResponse
        }
    }

    fun signUpUser(user: User)=viewModelScope.launch {
        try {
            val signUpResponse=mainRepository.signUpUser(user)
            _signUpResponse.value=signUpResponse
            commonEvent.value=Constants.EVENT_DISMISS_PROGRESS
        }catch (e:ApiException){
            e.printStackTrace()
            _signUpApiException.value=e.commonResponse
            commonEvent.value=Constants.EVENT_DISMISS_PROGRESS
        }
    }

    fun getGendersList(): List<Gender>? {

        if (genderList.isNotNullOrEmpty()) {
            return genderList
        }

        if (genderResponse.value == null) {
            return null
        }

        val genderList = genderResponse.value?.data?.genders?.toMutableList() ?: mutableListOf()

        val emptyHintGender = Gender().apply {
            name = "Select Gender"
        }
        genderList.add(0, emptyHintGender)

        this.genderList = genderList
        return genderList
    }

    /**
     * @param emailId Value to validate
     * @return true if it a matches with valid email pattern
     */
    fun isValidEmail(emailId: CharSequence): Boolean {
        return if (emailId.isNotBlank()) {
            android.util.Patterns.EMAIL_ADDRESS.matcher(emailId)
                .matches()
        } else false
    }

}