package com.flipperdevices.faphub.installation.button.impl.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.flipperdevices.faphub.installation.button.api.FapButtonConfig
import com.flipperdevices.faphub.installation.button.api.FapButtonSize
import com.flipperdevices.faphub.installation.button.api.FapInstallationUIApi
import com.flipperdevices.faphub.installation.button.impl.composable.states.ComposableFapButton
import com.flipperdevices.faphub.installation.button.impl.viewmodel.FapStatusViewModel
import com.flipperdevices.faphub.installation.stateprovider.api.model.FapState
import org.koin.compose.koinInject

class FapInstallationUIApiImpl : FapInstallationUIApi {
    @Composable
    override fun ComposableButton(
        config: FapButtonConfig?,
        modifier: Modifier,
        fapButtonSize: FapButtonSize
    ) {
        val statusViewModel = koinInject<FapStatusViewModel>()
        val stateFlow = remember(config) {
            statusViewModel.getStateForApplicationId(config)
        }
        val state by stateFlow.collectAsState(FapState.NotInitialized)

        ComposableFapButton(
            modifier = modifier,
            localState = state,
            fapButtonSize = fapButtonSize,
            statusViewModel = statusViewModel,
            config = config
        )
    }
}
