import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.flipperdevices.bridge.dao.hideKoin
import com.flipperdevices.core.ui.errors.impl.fapHubKoin
import com.flipperdevices.core.ui.theme.FlipperTheme
import com.flipperdevices.core.ui.theme.LocalPallet
import com.flipperdevices.faphub.appcard.composable.AppCard
import com.flipperdevices.faphub.catalogtab.impl.catalogTabKoin
import com.flipperdevices.faphub.dao.network.koinDao
import com.flipperdevices.faphub.dao.network.ktorfit.koinKtorfit
import com.flipperdevices.faphub.target.flipperTargetKoin
import com.flipperdevices.main.impl.mainScreenKoin
import com.lionzxy.flipperapp.navigation.DefaultRootComponent
import com.lionzxy.flipperapp.navigation.RootContent
import io.kamel.core.config.DefaultHttpCacheSize
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.batikSvgDecoder
import io.kamel.image.config.resourcesFetcher
import io.ktor.client.plugins.logging.*
import org.koin.compose.KoinApplication
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin

val lifecycle = LifecycleRegistry()

@Composable
@Preview
fun App(koin: Koin, kamelConfig: KamelConfig) {

    CompositionLocalProvider(LocalKamelConfig provides kamelConfig) {
        FlipperTheme {
            KoinContext(context = koin) {
                RootContent(
                    DefaultRootComponent(
                        DefaultComponentContext(lifecycle = lifecycle),
                        koin
                    ),
                    modifier = Modifier.fillMaxSize()
                        .background(LocalPallet.current.background)
                )
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
        //batikSvgDecoder()
        httpFetcher {
            httpCache(DefaultHttpCacheSize)
            Logging {
                level = LogLevel.INFO
                logger = Logger.SIMPLE
            }
        }

    }
    val koin = startKoin {
        modules(
            koinDao(),
            koinKtorfit(),
            mainScreenKoin(),
            fapHubKoin(),
            catalogTabKoin(),
            hideKoin(),
            flipperTargetKoin()
        )
    }
    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Flipper Desktop App"
        ) {
            App(koin.koin, desktopConfig)
        }
    }
}
