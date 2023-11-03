package com.example.amphibians.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibianApplication
import com.example.amphibians.data.AmphibianInfoRepository
import com.example.amphibians.model.Amphibian
import kotlinx.coroutines.launch

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>) : AmphibiansUiState

    data object Error : AmphibiansUiState
    data object Loading : AmphibiansUiState

}

class AmphibiansViewModel(private val amphibianInfoRepository: AmphibianInfoRepository) :
    ViewModel() {
    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set


    init {
        getAmphibiansInfo()
    }


    fun getAmphibiansInfo() {
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try {
                val result = amphibianInfoRepository.getAmphibians()
                AmphibiansUiState.Success(result)
            } catch (e: Exception) {
                Log.d("AmphibiansViewModel", e.toString())
                AmphibiansUiState.Error
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibianApplication)
                val amphibianInfoRepository = application.container.amphibianInfoRepository
                AmphibiansViewModel(amphibianInfoRepository = amphibianInfoRepository)
            }
        }
    }
}