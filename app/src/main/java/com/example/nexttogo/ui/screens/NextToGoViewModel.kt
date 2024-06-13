package com.example.nexttogo.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nexttogo.NextToGoApplication
import com.example.nexttogo.data.NextToGoRepository
import com.example.nexttogo.model.RaceSummary
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface NextToGoUiState {
    data class Success(val raceSummary: List<RaceSummary>) : NextToGoUiState
    object Error : NextToGoUiState
    object Loading : NextToGoUiState
}

class NextToGoViewModel(private val nextToGoRepository: NextToGoRepository) : ViewModel() {

    /** The mutable State that stores the status of the most recent request */
    var nextToGoUiState: NextToGoUiState by mutableStateOf(NextToGoUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getNextToGoRaces()
    }

    fun getNextToGoRaces() {
        viewModelScope.launch {
            try {

                val nextToGo = nextToGoRepository.getNextToGo()
                val races = nextToGo.data.raceSummaries.values.toList()

                nextToGoUiState = NextToGoUiState.Success(races)
            } catch (e: IOException) {
                nextToGoUiState = NextToGoUiState.Error
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NextToGoApplication)
                val nextToGoRepository = application.container.nextToGoRepository
                NextToGoViewModel(nextToGoRepository = nextToGoRepository)
            }
        }
    }
}