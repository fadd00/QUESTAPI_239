package com.sample.prak12.modeldata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSiswa(
    val id: String? = null,
    val nama: String,
    val alamat: String,
    val telpon: String,
    @SerialName("created_at")
    val createdAt: String? = null
)
data class UIStateSiswa(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false,
)
data class DetailSiswa(
    val id: String? = null,
    val nama: String = "",
    val alamat: String = "",
    val telpon: String = "",
)
fun DetailSiswa.toDataSiswa(): DataSiswa = DataSiswa(
    id = id,
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