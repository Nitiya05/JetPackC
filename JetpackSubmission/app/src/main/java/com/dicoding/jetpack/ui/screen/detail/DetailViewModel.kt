package com.dicoding.jetpack.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpack.data.DaerahRepository
import com.dicoding.jetpack.model.Daerah
import com.dicoding.jetpack.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DaerahRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Daerah>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Daerah>>
        get() = _uiState

    fun getSerialById(serialId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getSerialById(serialId))
        }
    }

    fun addToFavorite(serial: Daerah) {
        repository.addToFavorite(serial)
    }
}