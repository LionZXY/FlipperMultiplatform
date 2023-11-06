package com.flipperdevices.faphub.dao.network.api

import com.flipperdevices.faphub.dao.api.FapReportApi
import com.flipperdevices.faphub.dao.network.ktorfit.api.KtorfitApplicationApi
import com.flipperdevices.faphub.dao.network.ktorfit.model.KtorfitReport

private const val DEFAULT_TYPE = "default_android"

class FapReportApiImpl(
    val ktorfitApplicationApi: KtorfitApplicationApi
) : FapReportApi {
    override suspend fun report(applicationUid: String, description: String) {
        ktorfitApplicationApi.report(
            applicationUid,
            KtorfitReport(
                description = description,
                descriptionType = DEFAULT_TYPE
            )
        )
    }
}
