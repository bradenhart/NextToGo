package com.example.nexttogo.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nexttogo.NextToGoApplication
import com.example.nexttogo.data.NextToGoRepository
import com.example.nexttogo.model.Race
import com.example.nexttogo.model.RacingCategory
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.Instant

sealed interface NextToGoUiState {
    data class Success(val races: List<Race>, val filterState: Map<String,Boolean>) : NextToGoUiState
    object Error : NextToGoUiState
    object Loading : NextToGoUiState
}

class NextToGoViewModel(private val nextToGoRepository: NextToGoRepository) : ViewModel() {

    val MAX_DISPLAY_SIZE = 5

    /** The mutable State that stores the status of the most recent request */
    var nextToGoUiState: NextToGoUiState by mutableStateOf(NextToGoUiState.Loading)
        private set

    private val _filterState : MutableLiveData<Map<String,Boolean>> by lazy {
        MutableLiveData<Map<String,Boolean>>()
    }
    private val _races : MutableLiveData<List<Race>> by lazy {
        MutableLiveData<List<Race>>()
    }

    fun onFilterStateChanged(racingCategory: RacingCategory, state: Boolean) {
        // Update the filter state
        val currFilterState = _filterState.value
        var newFilterState = if (currFilterState != null) currFilterState.toMutableMap() else mutableMapOf()
        var allChecked = false

        newFilterState[racingCategory.id] = state

        // Check if any filters are checked. If they're all unchecked, revert them all to checked to enable all filters
        if (newFilterState.filterValues { it == true }.isEmpty()) {
            newFilterState = _getDefaultFilterState()
            allChecked = true
        }

        _filterState.value = newFilterState

        // Update races to display
        val newRaces = _races.value!!.toMutableList().map {

            val categoryDisplayed: Boolean = if (allChecked) {
                true
            } else {
                if (it.raceCategory == racingCategory) state else it.categoryDisplayed
            }

            Race(
                it.raceId,
                it.categoryId,
                it.raceCategory,
                it.meetingName,
                it.raceNumber,
                it.advertisedStart,
                categoryDisplayed =  categoryDisplayed,
                raceStarted = !_isTimeExpired(it.advertisedStart)
            )
        }

        _races.value = newRaces

        _launch()
    }

    fun onRaceExpired() {
        // Update races to display

        val newRaces = _races.value!!.toMutableList().map {
            val isRaceStarted = _isTimeExpired(it.advertisedStart)

            Race(
                it.raceId,
                it.categoryId,
                it.raceCategory,
                it.meetingName,
                it.raceNumber,
                it.advertisedStart,
                it.categoryDisplayed,
                raceStarted = isRaceStarted
            )
        }.filter { !it.raceStarted }

        _races.value = newRaces

        _launch()
    }

    private fun _launch() {
        viewModelScope.launch {
            try {
                var filteredRaces = _races.value!!.filter { it.categoryDisplayed }
                // On success, update the ui state to refresh the UI
                nextToGoUiState = NextToGoUiState.Success(filteredRaces, _filterState.value!!)
            } catch (e: IOException) {
                nextToGoUiState = NextToGoUiState.Error
            }
        }
    }

    init {
        _initFilterState()
        getNextToGoRaces()
    }

    private fun _getDefaultFilterState() : MutableMap<String,Boolean> {
        return mutableMapOf(
            RacingCategory.HORSE_RACING.id to true,
            RacingCategory.GREYHOUND_RACING.id to true,
            RacingCategory.HARNESS_RACING.id to true
        )
    }

    private fun _isTimeExpired(seconds: Long): Boolean {
        return seconds - Instant.now().epochSecond <= -60
    }

    private fun _initFilterState() {
        _filterState.value = _getDefaultFilterState()
    }

    private fun _getMaxRange(listSize: Int): Int {
        return if (listSize < MAX_DISPLAY_SIZE) listSize else MAX_DISPLAY_SIZE
    }

    fun getNextToGoRaces() {
        val filterState = _filterState.value!!

        viewModelScope.launch {
            try {
                // Get the next to go races from the repository
                val nextToGo = nextToGoRepository.getNextToGo()

                // Get a list of race summaries ordered by ascending advertised start time
                val races = nextToGo.data.raceSummaries.values
                    .toList()
                    .map {
                        Race(
                            raceId = it.raceId,
                            categoryId = it.categoryId,
                            raceCategory = (RacingCategory from it.categoryId)!!,
                            meetingName = it.meetingName,
                            raceNumber = it.raceNumber,
                            advertisedStart = it.advertisedStart.seconds
                        )
                    }
                    .sortedBy { race -> race.advertisedStart }
                    .filter {
                        !_isTimeExpired(it.advertisedStart)
                    }

                // Add all unfiltered races to state
                _races.value = races

                // On success, update the ui state to refresh the UI
                nextToGoUiState = NextToGoUiState.Success(_races.value!!, _filterState.value!!)
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