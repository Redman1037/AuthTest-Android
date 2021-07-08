package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    var data:UserDataWrapper?=null
) {
}