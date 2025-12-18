package com.sample.prak12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.prak12.modeldata.DataSiswa
import com.sample.prak12.repositori.RepositoryDataSiswa
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<DataSiswa> = listOf()) : StatusUiSiswa
    object Error: StatusUiSiswa
    object Loading: StatusUiSiswa
}
class HomeViewModel (private val repositoryDataSiswa: RepositoryDataSiswa):
    ViewModel() {
    var listSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
        private set

    init {
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            listSiswa = StatusUiSiswa.Loading
            listSiswa = try {
                println("Memulai request getData...")
                val data = repositoryDataSiswa.getDataSiswa()
                println("Berhasil mendapatkan data: ${data.size} siswa")
                StatusUiSiswa.Success(data)
            }
            catch (e: IOException) {
                println("IOException saat load siswa: ${e.message}")
                e.printStackTrace()
                StatusUiSiswa.Error
            }
            catch (e: Exception) {
                println("Exception saat load siswa: ${e.message}")
                e.printStackTrace()
                StatusUiSiswa.Error
            }
        }
    }
}