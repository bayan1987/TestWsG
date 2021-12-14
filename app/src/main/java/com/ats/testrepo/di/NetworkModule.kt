package com.ats.webservice.di

import android.content.Context
import android.content.Intent
import android.util.Log
import com.ats.webservice.data.api.APIEndpoints
import com.ats.webservice.data.repoImpl.RepoImpl
import com.ats.webservice.domain.repo.WsRepo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.grey.sar.data.local.PreferenceHelper
import com.grey.sar.data.local.PreferencesKeys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    fun provideApiClient(retrofit: Retrofit): APIEndpoints {
        return retrofit.create(APIEndpoints::class.java)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .addInterceptor {
                val request = it.request().newBuilder()
                request.addHeader("Accept-language", "en")
                request.addHeader("content-type", "application/json")
                request.addHeader("Authorization", PreferenceHelper().getString(PreferencesKeys.KEY_JWT_TOKEN,"")?:"")
                it.proceed(request.build())
            }
            .addInterceptor(Interceptor { chain ->
                val request: Request = chain.request()
                val response = chain.proceed(request)
                if (response.code == 401) {// unAuthorized
                }
                response
            })
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun provideSocketTimeOutInterceptor(){

    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(@ApplicationContext context: Context): HttpLoggingInterceptor {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideRepository(retrofitService: APIEndpoints): WsRepo {
        return RepoImpl(retrofitService)
    }

}