package rest.restmodels

import com.google.gson.annotations.SerializedName

class CommonResponse {
    @SerializedName("code")
    var code: Int = 0
    @SerializedName("message")
    var message: String? = null
    @SerializedName("data")
    var data:MutableMap<String,Any>?=null

}
