package com.appstyx.authtest.ui

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AuthTestApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        application=this
    }

    companion object{

        var application:Application?=null

        fun getContext():Context{
            return application!!
        }

    }
}