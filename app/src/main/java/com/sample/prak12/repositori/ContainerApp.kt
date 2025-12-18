package com.sample.prak12.repositori

interface ContainerApi{
    val repositoriDataSiswa : repositoriDataSiswa
}
class DefaultAppContainer : ContainerApi {
    private val baseUrl = ""

    val logging = HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
}
    val klien = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
