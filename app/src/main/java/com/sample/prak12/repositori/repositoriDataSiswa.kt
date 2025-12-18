package com.sample.prak12.repositori

import com.sample.prak12.apiservice.ServiceApiSiswa
import com.sample.prak12.modeldata.DataSiswa

interface RepositoryDataSiswa {
    suspend fun getDataSiswa() : List<DataSiswa>
    suspend fun postDataSiswa(dataSiswa: DataSiswa) : retrofit2.Response<Void>
}

class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa() : List<DataSiswa> = serviceApiSiswa.getSiswa()
    override suspend fun postDataSiswa(dataSiswa: DataSiswa) : retrofit2.Response<Void> = serviceApiSiswa.postSiswa(dataSiswa)
}

/**
 * Repository yang menggunakan koneksi database langsung (tanpa PHP)
 */
class DatabaseRepositoryDataSiswa(
    private val dbHelper: DatabaseHelper
) : RepositoryDataSiswa {
    override suspend fun getDataSiswa(): List<DataSiswa> {
        return dbHelper.getAllSiswa()
    }

    override suspend fun postDataSiswa(dataSiswa: DataSiswa): retrofit2.Response<Void> {
        val success = dbHelper.insertSiswa(dataSiswa)
        // Create dummy Response for compatibility
        return if (success) {
            retrofit2.Response.success(null)
        } else {
            retrofit2.Response.error(500, okhttp3.ResponseBody.create(null, ""))
        }
    }
}

