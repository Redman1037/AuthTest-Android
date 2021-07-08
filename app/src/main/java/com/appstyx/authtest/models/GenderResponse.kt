package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

data class GenderResponse(
    @SerializedName("data")
    var data:GenderDataWrapper?=null
)
