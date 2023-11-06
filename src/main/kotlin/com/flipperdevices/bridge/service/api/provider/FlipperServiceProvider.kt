package com.flipperdevices.bridge.service.api.provider

import com.flipperdevices.bridge.service.api.FlipperServiceApi

interface FlipperServiceProvider {
    fun getServiceApi(): FlipperServiceApi
}

class FlipperServiceProviderImpl(private val flipperServiceApi: FlipperServiceApi) : FlipperServiceProvider {
    override fun getServiceApi(): FlipperServiceApi {
        return flipperServiceApi
    }

}
