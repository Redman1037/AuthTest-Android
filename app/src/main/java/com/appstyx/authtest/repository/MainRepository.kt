package com.appstyx.authtest.repository

import com.appstyx.authtest.models.GenderResponse
import com.appstyx.authtest.models.SignUpResponse
import com.appstyx.authtest.models.User
import com.appstyx.authtest.models.UserResponse
import rest.RetrofitService
import rest.apiRequest
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(val retrofitService: RetrofitService) {

    suspend fun getGender(): GenderResponse {
        return apiRequest { retrofitService.getGenders() }
    }

    suspend fun signUpUser(user: User): SignUpResponse {
        return apiRequest { retrofitService.signUpUser(user.email,user.firstName,user.lastName,user.gender) }
    }

    suspend fun getProfile(): UserResponse {
        return apiRequest { retrofitService.getProfile() }
    }


}