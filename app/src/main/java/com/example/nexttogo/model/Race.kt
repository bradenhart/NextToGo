package com.example.nexttogo.model

data class Race(
    val raceId: String,
    val categoryId: String,
    val raceCategory: RacingCategory,
    val meetingName: String,
    val raceNumber: Int,
    val advertisedStart: Long,
    val categoryDisplayed: Boolean = true,
    val raceStarted: Boolean = false
)
