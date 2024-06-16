package com.example.nexttogo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nexttogo.R
import com.example.nexttogo.model.Race
import com.example.nexttogo.model.RacingCategory
import com.example.nexttogo.ui.NextToGoFilter
import kotlinx.coroutines.delay
import java.time.Instant
import kotlin.math.abs

@Composable
fun HomeScreen(
    nextToGoUiState: NextToGoUiState,
    modifier: Modifier = Modifier
) {
    when (nextToGoUiState) {
        is NextToGoUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is NextToGoUiState.Success -> RacesListScreen(
            races = nextToGoUiState.races,
            filterState = nextToGoUiState.filterState
        )

        is NextToGoUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun RaceCard(race: Race) {
    var iconRes: Int = (RacingCategory from race.categoryId)!!.res

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 20.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = iconRes),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.CenterVertically),

                text = "${race.meetingName} R${race.raceNumber}"
            )
            BasicCountdownTimer(
                raceId = race.raceId,
                time = race.advertisedStart,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun PreviewRaceCard() {
    val race = Race(
        raceId = "1",
        categoryId = RacingCategory.GREYHOUND_RACING.id,
        raceCategory = RacingCategory.GREYHOUND_RACING,
        meetingName = "Prairie Meadows",
        raceNumber = 1,
        advertisedStart = 120L
    )

    RaceCard(race = race)
}

@Composable
fun BasicCountdownTimer(raceId: String, time: Long, modifier: Modifier = Modifier) {
    val nextToGoViewModel: NextToGoViewModel = viewModel(factory = NextToGoViewModel.Factory)
    val epochInstant = Instant.now().epochSecond
    val timeUntil =  time - epochInstant

    var timeLeft by remember { mutableStateOf(timeUntil) }

    LaunchedEffect(key1 = timeLeft) {
        while (true) {
            delay(1000L)
            timeLeft--

//            if (timeLeft <= -60) {
////                nextToGoViewModel.onRaceExpired(raceId)
////                nextToGoViewModel.getNextToGoRaces()
//                break
//            }
        }
    }

    CountdownText(
        modifier = modifier,
        seconds = timeLeft
    )
}

@Composable
fun CountdownText(seconds: Long, modifier: Modifier = Modifier) {
    val formattedTime = getCountdownString(seconds.toInt())

    Text(
        modifier = modifier,
        text = formattedTime
    )
}

@Preview
@Composable
fun PreviewCountdownText() {
    val fakeTime = Instant.now().plusSeconds(60)

    CountdownText(seconds = fakeTime.epochSecond)
}

fun getCountdownString(seconds: Int): String {
    val isNegative = seconds < 0
    var totalHours: Int
    var totalMinutes: Int
    var totalSeconds: Int = abs(seconds)
    var countdownString = ""

    totalHours = totalSeconds / 3600

    if (totalHours > 0) {
        totalSeconds -= (totalHours * 3600)
        countdownString += "${totalHours}h "
    }

    totalMinutes = totalSeconds / 60

    if (totalMinutes > 0) {
        totalSeconds -= (totalMinutes * 60)
        countdownString += "${totalMinutes}m "
    }

    countdownString += "${totalSeconds}s"

    return if (isNegative) "-$countdownString" else countdownString
}

@Preview
@Composable
fun PreviewBasicCountdownTimer() {
    BasicCountdownTimer(raceId = "1", time = 1L)
}

@Composable
fun RacesListScreen(
    races: List<Race>,
    filterState: Map<String,Boolean>
) {
    val maxSize = if (races.size < 5) races.size else 5

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        races.subList(0, maxSize).forEach {
            race -> if (!race.raceStarted || race.categoryDisplayed) RaceCard(race = race) else null
        }
        NextToGoFilter(filterState = filterState)
    }
}

