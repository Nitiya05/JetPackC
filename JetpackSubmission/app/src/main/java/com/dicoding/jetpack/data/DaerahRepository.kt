package com.dicoding.jetpack.data

import androidx.compose.runtime.mutableStateListOf
import com.dicoding.jetpack.model.FakeDataSource
import com.dicoding.jetpack.model.Daerah
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DaerahRepository {

    private val daerah = mutableListOf<Daerah>()
    private val daerahFav = mutableStateListOf<Daerah>()

    init {
        if (daerah.isEmpty()) {
            FakeDataSource.dummySeries.forEach {
                daerah.add(it)
            }
        }
    }

    fun getAllSeries(query: String): Flow<List<Daerah>> {
        return flow {
            val filteredSeries = daerah.filter { it.title.contains(query, ignoreCase = true) }
            emit(filteredSeries)
        }
    }

    fun getSerialById(seriesId: Long): Daerah {
        return daerah.first {
            it.id == seriesId
        }
    }

    fun getAddedSeriesFavorite(): Flow<List<Daerah>> {
        return flow {
            emit(daerah.filter { it.isFav })
        }
    }

    fun addToFavorite(series: Daerah) {
        series.isFav = true
    }

    fun updateSeriesFavorite(seriesId: Long) {
        val animeToUpdate = daerah.find { it.id == seriesId }
        animeToUpdate?.let {
            it.isFav = !it.isFav
            if (it.isFav) {
                daerahFav.add(it)
            } else {
                daerahFav.remove(it)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: DaerahRepository? = null

        fun getInstance(): DaerahRepository =
            instance ?: synchronized(this) {
                DaerahRepository().apply {
                    instance = this
                }
            }
    }
}