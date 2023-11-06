package com.flipperdevices.faphub.target

import com.flipperdevices.faphub.target.api.FlipperTargetProviderApi
import com.flipperdevices.faphub.target.model.FlipperTarget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FlipperTargetProviderApiNoop : FlipperTargetProviderApi {
    override fun getFlipperTarget(): StateFlow<FlipperTarget?> {
        return MutableStateFlow(FlipperTarget.NotConnected)
    }
}