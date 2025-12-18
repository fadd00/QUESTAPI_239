package com.sample.prak12.repositori

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sample.prak12.apiservice.ServiceApiSiswa
import com.sample.prak12.database.DatabaseHelper
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface ContainerApp {
    val repositoryDataSiswa: RepositoryDataSiswa
}

class DefaultContainerApp : ContainerApp {
    // ========================================
    // PILIH MODE KONEKSI:
    // ========================================
    // true  = Gunakan koneksi DATABASE LANGSUNG (tanpa PHP) ✅ RECOMMENDED
    // false = Gunakan API PHP (dengan Retrofit)
    private val USE_DIRECT_DATABASE = true

    // ========================================
    // KONFIGURASI API (jika USE_DIRECT_DATABASE = false)
    // ========================================
    private val baseurl = "http://192.168.1.30:8080/"

    val logging = HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
    }

    val klien = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .client(klien)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())
        ).build()

    private val retrofitService: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    // ========================================
    // REPOSITORY - Otomatis pilih berdasarkan USE_DIRECT_DATABASE
    // ========================================
    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        if (USE_DIRECT_DATABASE) {
            // Gunakan koneksi database langsung
            DatabaseRepositoryDataSiswa(DatabaseHelper())
        } else {
            // Gunakan API PHP
            JaringanRepositoryDataSiswa(retrofitService)
        }
    }
}

class AplikasiDataSiswa : Application() {
    lateinit var container : ContainerApp
    override fun onCreate() {
        super.onCreate()
        this.container = DefaultContainerApp()
    }
}

