package com.sample.prak12.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class DataSiswa(
    val id: Int? = null,
    val nama: String,
    val alamat: String,
    val telpon: String,
)
data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false,
)
data class DetailSiswa(
    val id: Int? = null,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = "",
)
fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = if (id == 0) null else id,
    nama = nama,
    alamat = alamat,
    telpon = telpon,
)
fun DataSiswa.toUiStateSiswa(isEntryValid: Boolean = false): UIStateSiswa = UIStateSiswa(
    detailSiswa = this.toDetailSiswa(),
    isEntryValid = isEntryValid
)
fun DataSiswa.toDetailSiswa(): DetailSiswa = DetailSiswa(
    id = id,
    nama = nama,
    alamat = alamat,
    telpon = telpon,
)