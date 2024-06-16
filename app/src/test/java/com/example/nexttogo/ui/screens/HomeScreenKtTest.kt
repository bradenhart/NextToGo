package com.example.nexttogo.ui.screens

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class HomeScreenKtTest {

    @Test
    fun getCountdownString_success_withHoursMinutesSeconds() {
        assertEquals(getCountdownString(3661), "1h 1m 1s")
    }

    @Test
    fun getCountdownString_success_withMinutesSeconds() {
        assertEquals(getCountdownString(67), "1m 7s")
    }

    @Test
    fun getCountdownString_success_withSeconds() {
        assertEquals(getCountdownString(1), "1s")
    }

    @Test
    fun getCountdownString_success_NegativeHoursMinutesSeconds() {
        assertEquals(getCountdownString(-3661), "-1h 1m 1s")
    }

}

