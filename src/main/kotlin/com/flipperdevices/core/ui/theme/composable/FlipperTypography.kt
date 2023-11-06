package com.flipperdevices.core.ui.theme.composable

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

@Stable
data class FlipperTypography(
    val titleB24: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val titleB22: TextStyle = TextStyle(
        fontSize = 22.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val titleB20: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val titleSB18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W600
    ),
    val titleSB16: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W600
    ),
    val titleEB20: TextStyle = TextStyle(
        fontSize = 20.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W800
    ),
    val titleEB18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W800
    ),
    val titleB18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val titleR18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W400
    ),
    val titleM18: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W500
    ),
    val subtitleB12: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val subtitleEB12: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W800
    ),
    val subtitleM12: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W500
    ),
    val subtitleR12: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W400
    ),
    val subtitleR10: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W400
    ),
    val subtitleB10: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val subtitleM10: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W500
    ),
    val bodySB14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W600
    ),
    val bodySSB14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val bodyR14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W400
    ),
    val bodyM14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W500
    ),
    val bodyR16: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W400
    ),
    val buttonB16: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val buttonM16: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W500
    ),
    val buttonB14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = robotoFamily,
        fontWeight = FontWeight.W700
    ),
    val updateButton40: TextStyle = TextStyle(
        fontSize = 40.sp,
        fontFamily = FontFamily(Font(resource = "font/flipper_bold.ttf")),
        fontWeight = FontWeight.W400
    ),
    val updateText40: TextStyle = TextStyle(
        fontSize = 40.sp,
        fontFamily = FontFamily(Font(resource = "font/flipper.ttf")),
        fontWeight = FontWeight.W400
    ),
    val flipperAction: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(resource = "font/flipper_action.ttf")),
        fontWeight = FontWeight.W500
    ),
    val monoSpaceM14: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily(Font(resource = "font/roboto_mono_medium.ttf")),
        fontWeight = FontWeight.W500
    ),
    val monoSpaceM10: TextStyle = TextStyle(
        fontSize = 10.sp,
        fontFamily = FontFamily(Font(resource = "font/roboto_mono_medium.ttf")),
        fontWeight = FontWeight.W500
    ),
    val monoSpaceM12: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resource = "font/roboto_mono_medium.ttf")),
        fontWeight = FontWeight.W500
    ),
    val monoSpaceM16: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(resource = "font/roboto_mono_medium.ttf")),
        fontWeight = FontWeight.W500
    ),
    val monoSpaceR12: TextStyle = TextStyle(
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(resource = "font/roboto_mono_medium.ttf")),
        fontWeight = FontWeight.W400
    ),
    val notificationB8: TextStyle = TextStyle(
        fontSize = 8.sp,
        fontFamily = FontFamily(Font(resource = "font/roboto_mono_medium.ttf")),
        fontWeight = FontWeight.W700
    ),
    val fapHubButtonText: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(resource = "font/flipper_action.ttf")),
        fontWeight = FontWeight.W500
    ),
    val fapHubButtonProgressText: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(resource = "font/flipper.ttf")),
        fontWeight = FontWeight.W500
    ),
    val infraredEditButton: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontFamily = FontFamily(Font(resource = "font/flipper.ttf")),
        fontWeight = FontWeight.W400
    ),
)

fun getTypography(): FlipperTypography {
    return FlipperTypography()
}

internal val robotoFamily = FontFamily(
    Font(resource = "font/roboto_regular.ttf", FontWeight.W400),
    Font(resource = "font/roboto_italic.ttf", FontWeight.W400, FontStyle.Italic),
    Font(resource = "font/roboto_black.ttf", FontWeight.W900),
    Font(resource = "font/roboto_black_italic.ttf", FontWeight.W900, FontStyle.Italic),
    Font(resource = "font/roboto_light.ttf", FontWeight.W300),
    Font(resource = "font/roboto_light_italic.ttf", FontWeight.W300, FontStyle.Italic),
    Font(resource = "font/roboto_medium.ttf", FontWeight.W500),
    Font(resource = "font/roboto_medium_italic.ttf", FontWeight.W500, FontStyle.Italic),
    Font(resource = "font/roboto_bold.ttf", FontWeight.W700),
    Font(resource = "font/roboto_bold_italic.ttf", FontWeight.W700, FontStyle.Italic),
    Font(resource = "font/roboto_thin.ttf", FontWeight.W100),
    Font(resource = "font/roboto_thin_italic.ttf", FontWeight.W100, FontStyle.Italic)
)
