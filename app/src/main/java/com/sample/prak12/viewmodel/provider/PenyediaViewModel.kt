package com.sample.prak12.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sample.prak12.repositori.AplikasiDataSiswa
import com.sample.prak12.viewmodel.HomeViewModel
import com.sample.prak12.viewmodel.EntryViewModel

fun CreationExtras.aplikasiDataSiswa() : AplikasiDataSiswa = (
        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as
                AplikasiDataSiswa)
object PenyediaViewModel {
    val Factory = viewModelFactory  {
        initializer {
            HomeViewModel(aplikasiDataSiswa().container.repositoryDataSiswa)
        }
        initializer {
            EntryViewModel(aplikasiDataSiswa().container.repositoryDataSiswa)
        }
    }
}