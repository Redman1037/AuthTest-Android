package com.appstyx.authtest.di

import com.appstyx.authtest.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rest.RetrofitService
import rest.RetrofitUtils
import rest.restutils.GsonUtils
import rest.restutils.ToStringConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl()= BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(RetrofitUtils.BASE_URL)
            .client(RetrofitUtils.getInterceptorClient())

        retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())  ////for making string request https://stackoverflow.com/a/42489802/6478047
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
            .addConverterFactory(ToStringConverterFactory())


        val retrofit = retrofitBuilder.build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit):RetrofitService = retrofit.create(RetrofitService::class.java)


}