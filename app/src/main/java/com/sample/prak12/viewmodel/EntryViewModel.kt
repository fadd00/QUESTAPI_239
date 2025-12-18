package com.sample.prak12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sample.prak12.modeldata.DetailSiswa
import com.sample.prak12.modeldata.UIStateSiswa
import com.sample.prak12.modeldata.toDataSiswa
import com.sample.prak12.repositori.RepositoryDataSiswa

class EntryViewModel (private val repositoryDataSiswa: RepositoryDataSiswa):
    ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa):
            Boolean {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun  updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }
    suspend fun addSiswa() {
        if (validasiInput()) {
            val sip = repositoryDataSiswa.postDataSiswa(uiStateSiswa.detailSiswa.toDataSiswa())
            if (sip.isSuccessful) {
                println("Sukses Tambah Data: ${sip.message()}")
            }else{
                println("Gagal Tambah Data: ${sip.errorBody()}")
            }
        }
    }
}