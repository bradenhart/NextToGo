package com.example.nexttogo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nexttogo.model.RacingCategory
import com.example.nexttogo.ui.screens.NextToGoViewModel

@Composable()
fun NextToGoFilter(filterState: Map<String,Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        FilterOption(RacingCategory.HORSE_RACING, filterState[RacingCategory.HORSE_RACING.id]!!)
        FilterOption(RacingCategory.GREYHOUND_RACING, filterState[RacingCategory.GREYHOUND_RACING.id]!!)
        FilterOption(RacingCategory.HARNESS_RACING, filterState[RacingCategory.HARNESS_RACING.id]!!)
    }
}

//@Preview
//@Composable
//fun PreviewNextToGoFilter() {
//    val racingCategories = listOf(RacingCategory.HORSE_RACING, RacingCategory.HARNESS_RACING)
//
//    NextToGoFilter(racingCategories = racingCategories)
//}

@Composable
fun FilterOption(racingCategory: RacingCategory, initState: Boolean = true) {
    val nextToGoViewModel: NextToGoViewModel = viewModel(factory = NextToGoViewModel.Factory)
    var checkedState by remember { mutableStateOf(initState) }

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = initState,
            onCheckedChange = {
                checkedState = it
                nextToGoViewModel.onFilterStateChanged(racingCategory = racingCategory, state = checkedState)
            }
        )
        Icon(
            painter = painterResource(id = racingCategory.res),
            contentDescription = racingCategory.desc,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun PreviewFilterOption() {
    FilterOption(RacingCategory.HARNESS_RACING)
}