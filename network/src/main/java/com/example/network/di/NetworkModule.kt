package com.example.network.di

import android.content.Context
import com.example.network.CommonUtild
import com.example.network.auth.AuthInterceptor
import com.example.network.remote.ComroApi
import com.example.network.repository.ComroRepository
import com.example.network.repository.ComroRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val COMRO_BASE_URL = "https://headfieldstaging.com/comro-application/api/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideComroApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): ComroApi {
        return  retrofit.client(okHttpClient).build().create(ComroApi::class.java)
    }



    @Provides
    @Singleton
    fun provideComroRepository(api:ComroApi): ComroRepository {
        return ComroRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideAuthAuthenticator(@ApplicationContext context: Context): AuthInterceptor {
        return AuthInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60,java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(60,java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(60,java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(COMRO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())


}