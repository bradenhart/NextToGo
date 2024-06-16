package com.example.nexttogo.ui.screens

import androidx.test.core.app.ApplicationProvider
import com.example.nexttogo.model.RacingCategory
import org.junit.Test

class NextToGoViewModelTest {

    @Test
    fun onFilterStateChanged_uncheckedCategory() {

        val viewModel = NextToGoViewModel(ApplicationProvider.getApplicationContext())
        viewModel.onFilterStateChanged(RacingCategory.HORSE_RACING, false)

        // TODO: confirm that map contains "RacingCategory.HORSE_RACING.id": false key-value pair.

    }

}