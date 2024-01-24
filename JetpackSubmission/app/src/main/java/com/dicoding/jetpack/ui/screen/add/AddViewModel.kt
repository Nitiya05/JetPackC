package com.dicoding.jetpack.ui.screen.add

import androidx.lifecycle.ViewModel
import com.dicoding.jetpack.ui.components.DataDaerah

class AddViewModel : ViewModel() {
    val daftar = mutableListOf<DataDaerah>()

    private var judul: String = ""
    private var deskripsi: String = ""
    private var rate: String = ""
    private var imageUri: String? = null


    fun addData(judul: String, deskripsi: String, rate: String) {
        val filmBaru = DataDaerah()
        filmBaru.judul
        filmBaru.deskripsi
        filmBaru.rate

        daftar.add(filmBaru)
    }


    fun setImageUri(uri: String) {

        this.imageUri = uri
    }


    private fun clearData() {
        judul = ""
        deskripsi = ""
        rate = ""
        imageUri = null
    }
}
