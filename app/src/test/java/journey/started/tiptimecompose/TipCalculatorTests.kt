package journey.started.tiptimecompose

import org.junit.Assert.assertEquals
import org.junit.Test

class TipCalculatorTests {

    @Test
    fun calculate_20_percent_tip_no_roundup () {
        val list = listOf ("Excellent (20%)", "Good (18%)", "Okay (15%)")

        val cost = 10.00
        val option = list[0]
        val expectedTip = "£2.00"
        val actualTip = calculation(cost = cost, list = list, option = option, check = false)
        assertEquals(expectedTip, actualTip)
    }
}