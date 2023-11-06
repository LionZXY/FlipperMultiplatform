package com.flipperdevices.faphub.installation.button.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.info
import com.flipperdevices.faphub.installation.button.api.FapButtonConfig
import com.flipperdevices.faphub.installation.button.impl.helper.OpenFapHelper
import com.flipperdevices.faphub.installation.button.impl.model.OpenFapResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenFapViewModel(
    private val openFapHelper: OpenFapHelper,
) : ViewModel(), LogTagProvider {
    override val TAG: String = "OpenFapViewModel"

    private val busyDialogState = MutableStateFlow(false)
    fun getDialogState() = busyDialogState.asStateFlow()
    fun getOpenFapState(fapButtonConfig: FapButtonConfig?) =
        openFapHelper.getOpenFapState(fapButtonConfig)

    fun closeDialog() {
        viewModelScope.launch {
            busyDialogState.emit(false)
        }
    }
}
