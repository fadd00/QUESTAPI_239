package com.sample.prak12.repositori

interface ContainerApi{
    val repositoriDataSiswa : repositoriDataSiswa
}
class DefaultAppContainer : ContainerApi{
    private val baseUrl =