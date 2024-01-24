package com.dicoding.jetpack.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpack.data.DaerahRepository
import com.dicoding.jetpack.model.Daerah
import com.dicoding.jetpack.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DaerahRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Daerah>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Daerah>>>
        get() = _uiState

    private var currentKeyword = ""

    fun getAllSeries(keyword: String = "") {
        viewModelScope.launch {
            repository.getAllSeries(keyword)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    fun searchDaerah(keyword: String) {
        currentKeyword = keyword
        getAllSeries(keyword)
    }
}