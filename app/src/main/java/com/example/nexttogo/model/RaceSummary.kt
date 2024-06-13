package com.example.nexttogo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceSummary(
    @SerialName(value = "race_id")
    val raceId: String,
    @SerialName(value = "race_name")
    val raceName: String,
    @SerialName(value = "race_number")
    val raceNumber: Int,
    @SerialName(value = "meeting_id")
    val meetingId: String,
    @SerialName(value = "meeting_name")
    val meetingName: String,
    @SerialName(value = "category_id")
    val categoryId: String,
    @SerialName(value = "advertised_start")
    val advertisedStart: AdvertisedStart,
    @SerialName(value = "race_form")
    val raceForm: RaceForm? = null,
    @SerialName(value = "venue_id")
    val venueId: String? = null,
    @SerialName(value = "venue_name")
    val venueName: String? = null,
    @SerialName(value = "venue_state")
    val venueState: String? = null,
    @SerialName(value = "venue_country")
    val venueCountry: String? = null
)

@Serializable
data class AdvertisedStart(
    val seconds: Long
)

@Serializable
data class DistanceType(
    val id: String? = null,
    val name: String? = null,
    @SerialName(value = "short_name")
    val shortName: String? = null
)

@Serializable
data class TrackCondition(
    val id: String? = null,
    val name: String? = null,
    @SerialName(value = "short_name")
    val shortName: String? = null
)

@Serializable
data class Weather(
    val id: String? = null,
    val name: String? = null,
    @SerialName(value = "short_name")
    val shortName: String? = null,
    @SerialName(value = "icon_uri")
    val iconUri: String? = null
)

@Serializable
data class RaceForm(
    val distance: Int? = null,
    @SerialName(value = "distance_type")
    val distanceType: DistanceType? = null,
    @SerialName(value = "distance_type_id")
    val distanceTypeId: String? = null,
    @SerialName(value = "track_condition")
    val trackCondition: TrackCondition? = null,
    @SerialName(value = "track_condition_id")
    val trackConditionId: String? = null,
    @SerialName(value = "weather")
    val weather: Weather? = null,
    @SerialName(value = "weather_id")
    val weatherId: String? = null,
    @SerialName(value = "race_comment")
    val raceComment: String? = null,
    @SerialName(value = "additional_data")
    val additionalData: String? = null,
    val generated: Int? = null,
    @SerialName(value = "silk_base_url")
    val silkBaseUrl: String? = null,
    @SerialName(value = "race_comment_alternative")
    val raceCommentAlternative: String? = null,
)
