import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.flipperdevices.core.ui.theme.FlipperTheme
import com.flipperdevices.faphub.appcard.composable.AppCard
import com.flipperdevices.faphub.dao.network.koinDao
import com.flipperdevices.faphub.dao.network.ktorfit.koinKtorfit
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.batikSvgDecoder
import io.kamel.image.config.resourcesFetcher
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

@Composable
@Preview
fun App(kamelConfig: KamelConfig) {

    CompositionLocalProvider(LocalKamelConfig provides kamelConfig) {
        FlipperTheme {
            KoinApplication(application = {
                koinDao()
                koinKtorfit()
            }) {
                AppCard(null) { }
            }
        }
    }
}

fun main() {
    val desktopConfig = KamelConfig {
        takeFrom(KamelConfig.Default)
        // Available only on Desktop.
        resourcesFetcher()
        // Available only on Desktop.
        // An alternative svg decoder
        batikSvgDecoder()
    }
    application {
        Window(onCloseRequest = ::exitApplication) {
            App(desktopConfig)
        }
    }
}
