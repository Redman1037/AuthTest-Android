package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

data class UserDataWrapper(
    @SerializedName("user")
    var user: User?=null
)
