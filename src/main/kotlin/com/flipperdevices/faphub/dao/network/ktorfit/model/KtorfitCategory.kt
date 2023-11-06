package com.flipperdevices.faphub.dao.network.ktorfit.model

import com.flipperdevices.faphub.dao.api.model.FapCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.awt.Color

@Serializable
data class KtorfitCategory(
    @SerialName("_id") val id: String,
    @SerialName("priority") val priority: Int,
    @SerialName("name") val name: String,
    @SerialName("color") val color: String,
    @SerialName("icon_uri") val iconUrl: String,
    @SerialName("applications") val applicationsCount: Int
) {
    fun toFapCategory(): FapCategory {
        return FapCategory(
            id = id,
            name = name,
            picUrl = iconUrl,
            applicationCount = applicationsCount,
            color = runCatching { parseColor(color)?.rgb }.getOrNull()
        )
    }
}

fun parseColor(input: String): Color? {
    var color: Color? = null
    if (input.matches("^#([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6})$".toRegex())) {
        // We have a hex value with either 6 (rgb) or 8 (rgba) digits
        val r = input.substring(1, 3).toInt(16)
        val g = input.substring(3, 5).toInt(16)
        val b = input.substring(5, 7).toInt(16)
        color = if (input.length > 7) {
            val a = input.substring(7, 9).toInt(16)
            Color(r, g, b, a)
        } else {
            Color(r, g, b)
        }
    }
    return color
}
