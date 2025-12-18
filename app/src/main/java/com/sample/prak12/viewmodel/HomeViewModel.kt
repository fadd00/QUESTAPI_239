package com.sample.prak12.viewmodel

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
                StatusUiSiswa.Success(repositoryDataSiswa.getDataSiswa())
            }
            catch (e: IOException) {
                StatusUiSiswa.Error
            }
            catch (e: HttpException) {
                StatusUiSiswa.Error
            }
        }
    }
}