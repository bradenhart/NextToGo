package com.example.nexttogo.model

import com.example.nexttogo.R

enum class RacingCategory(val id: String, val res: Int, val desc: String) {

    HORSE_RACING(
        id = "4a2788f8-e825-4d36-9894-efd4baf1cfae",
        res = R.drawable.horse_racing,
        desc = R.string.horse_racing_category_desc.toString()),
    GREYHOUND_RACING(
        id = "9daef0d7-bf3c-4f50-921d-8e818c60fe61",
        res = R.drawable.greyhound_racing,
        desc = R.string.greyhound_racing_category_desc.toString()
    ),
    HARNESS_RACING(
        id = "161d9be2-e909-4326-8c2c-35ed71fb460b",
        res = R.drawable.harness_racing,
        desc = R.string.harness_racing_category_desc.toString()
    );

    companion object {
        infix fun from(id: String): RacingCategory? = entries.firstOrNull { it.id == id }
    }

}