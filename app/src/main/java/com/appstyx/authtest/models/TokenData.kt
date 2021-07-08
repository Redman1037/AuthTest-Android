package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

data class TokenData(
    @SerializedName("token")
    var token:String?=null,
    @SerializedName("refreshToken")
    var refreshToken:String?=null
)
