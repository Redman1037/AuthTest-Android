package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

data class GenderDataWrapper(
    @SerializedName("genders")
    var genders:List<Gender>
)
