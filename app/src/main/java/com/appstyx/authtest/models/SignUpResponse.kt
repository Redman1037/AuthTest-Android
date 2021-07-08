package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("data")
    var data:TokenData?=null
)
