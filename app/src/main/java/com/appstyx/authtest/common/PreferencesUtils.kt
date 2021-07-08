package com.appstyx.authtest.common

import android.content.Context
import com.pddstudio.preferences.encrypted.EncryptedPreferences
import rest.RetrofitUtils

object PreferencesUtils {

    const val ACCESS_TOKEN = "ACCESS_TOKEN"


    /**
     * Save object as String to Shared Preference
     *
     * @param context
     * @param key     The name of the preference.
     * @param value   The value for the preference.
     */
    fun saveToSharedPreference(
        context: Context, key: String,
        value: String?
    ) {

        val preferences =
            EncryptedPreferences.Builder(context).withEncryptionPassword(PASSWORD)
                .build()

        preferences.edit()
            .putString(key, value).apply()

    }


    /**
     * Get String value from Shared Preference
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return Value for the given preference if exist else null.
     */
    fun getFromSharedPreference(context: Context, key: String): String? {

        val preferences =
            EncryptedPreferences.Builder(context).withEncryptionPassword(PASSWORD)
                .build()

        return preferences.getString(key, null)
    }


    /**
     * Clear every thing from Shared Preference
     * @param context
     */
    fun clearSharedPreferences(context: Context) {
        val preferences =
            EncryptedPreferences.Builder(context).withEncryptionPassword(PASSWORD)
                .build()
        preferences.forceDeleteExistingPreferences()
    }


    fun isUserLoggedIn(context: Context): Boolean {
        return !getAccessToken(context).isNullOrBlank()
    }

    //todo async
    fun saveAccessToken(context: Context, accessToken: String) {
        saveToSharedPreference(context, ACCESS_TOKEN, accessToken)
    }

    //todo async
    fun getAccessToken(context: Context): String? {
        return getFromSharedPreference(context, ACCESS_TOKEN)
    }


}