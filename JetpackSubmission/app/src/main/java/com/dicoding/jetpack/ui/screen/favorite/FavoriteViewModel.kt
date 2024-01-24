package com.dicoding.jetpack.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpack.data.DaerahRepository
import com.dicoding.jetpack.model.Daerah
import com.dicoding.jetpack.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: DaerahRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Daerah>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Daerah>>>
        get() = _uiState

    fun getAddedSerialFavorite() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedSeriesFavorite().collect { listSerial ->
                _uiState.value = UiState.Success(listSerial)
            }
        }
    }

    fun updateSerialFavorite(serialId: Long) {
        viewModelScope.launch {
            repository.updateSeriesFavorite(serialId)
        }
    }

    init {
        viewModelScope.launch {
            repository.getAddedSeriesFavorite().collect { serialList ->
                _uiState.value = UiState.Success(serialList)
            }
        }
    }
}