package rest.restutils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {
    private var gson: Gson? = null

    fun toJson(`object`: Any?): String {

        return getGson().toJson(`object`)
    }

    fun fromJson(json: String?, class1: Class<*>?): Any {

        return getGson().fromJson(json, class1)
    }


    fun fromJson(json: Reader?, class1: Class<*>?): Any {

        return getGson().fromJson(json, class1)
    }

    //Getting gson everyTime will be expensive so saving it in variable
    fun getGson(): Gson {
        if (gson == null) {
            val gsonBuilder = GsonBuilder()
            ////This will serialize fields even if it is null
            gsonBuilder.serializeNulls()
            gson = gsonBuilder.create()
        }
        return gson!!
    }


    inline fun <reified T> cloneObject(any: T): T {
        val json = toJson(any)
        val obj = fromJson(json, T::class.java) as T
        return obj
    }

}