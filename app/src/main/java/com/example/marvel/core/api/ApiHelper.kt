package com.example.marvel.core.api

import com.example.marvel.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiHelper {

    /**
     *  Creates a static singleton synchronized Retrofit object
     */
    companion object {
        @Volatile
        private var INSTANCE: Retrofit? = null

        fun getApiHelperInstance(): Retrofit {
            return INSTANCE ?: synchronized(this) {
                val instance = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}