package com.example.nexttogo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nexttogo.R
import com.example.nexttogo.model.RaceSummary
import com.example.nexttogo.ui.theme.NextToGoTheme

@Composable
fun HomeScreen(
    nextToGoUiState: NextToGoUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (nextToGoUiState) {
        is NextToGoUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is NextToGoUiState.Success -> RacesListScreen(
            races = nextToGoUiState.raceSummary,
            modifier = modifier
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    top = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                ),
            contentPadding = contentPadding
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

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(races: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = races)
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    NextToGoTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}

@Composable
fun RaceCard(race: RaceSummary, modifier: Modifier = Modifier) {
    Text(text = race.raceName)
}

@Composable
fun RacesListScreen(
    races: List<RaceSummary>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = races,
            key = { race ->
                race.raceName
            }
        ) { race ->
            RaceCard(race = race, modifier = Modifier.fillMaxSize())
        }
    }
}