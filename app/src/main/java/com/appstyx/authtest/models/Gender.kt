package com.appstyx.authtest.models

import com.google.gson.annotations.SerializedName

 class Gender{
    @SerializedName("id")
    var id:String?=null
    @SerializedName("name")
    var name:String?=null

    //this will show only name in spinnerAdapter
    override fun toString(): String {
        return name?:""
    }
 }
