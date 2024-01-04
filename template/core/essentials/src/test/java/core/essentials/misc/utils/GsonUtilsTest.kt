package core.essentials.misc.utils

import com.google.gson.annotations.SerializedName
import core.essentials.AbstractTest
import org.junit.jupiter.api.Assertions
import java.util.Date
import kotlin.test.Test

class GsonUtilsTest : AbstractTest() {

    @Test
    fun `parse object with date`() {
        val dateString = "2023-04-12T16:54:26.131332+00:00"
        val text = "{ date: '$dateString' }"
        val dateHolder = GsonUtils.toObject(text, DateHolder::class.java)!!
        Assertions.assertNotNull(dateHolder.date)
    }

    data class DateHolder(
        @SerializedName("date")
        val date: Date?
    )

}