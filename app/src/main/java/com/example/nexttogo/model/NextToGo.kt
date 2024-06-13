package com.example.nexttogo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NextToGo(
    val status: Int,
    val data: Data,
    val message: String

)

@Serializable
data class Data(
    @SerialName(value = "next_to_go_ids")
    val nextToGoIds: List<String>,

    @SerialName(value = "race_summaries")
    val raceSummaries: Map<String, RaceSummary>
)
