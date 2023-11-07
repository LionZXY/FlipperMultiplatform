package com.flipperdevices.faphub.catalogtab.impl.api

import androidx.compose.runtime.Composable
import com.flipperdevices.faphub.catalogtab.api.CatalogTabApi
import com.flipperdevices.faphub.catalogtab.impl.composable.ComposableCatalogTabScreen
import com.flipperdevices.faphub.dao.api.model.FapCategory
import com.flipperdevices.faphub.dao.api.model.FapItemShort
import com.flipperdevices.faphub.errors.api.FapHubComposableErrorsRenderer
import com.flipperdevices.faphub.installation.button.api.FapButtonSize
import com.flipperdevices.faphub.installation.button.api.FapInstallationUIApi
import com.flipperdevices.faphub.installation.button.api.toFapButtonConfig
import com.flipperdevices.faphub.installation.button.impl.api.FapInstallationUIApiImpl

class CatalogTabApiImpl(
    //private val fapInstallationUIApi: FapInstallationUIApi,
    private val errorsRenderer: FapHubComposableErrorsRenderer,
    private val buttonInstallationUIApiImpl: FapInstallationUIApi,
) : CatalogTabApi {
    @Composable
    override fun ComposableCatalogTab(
        onOpenFapItem: (FapItemShort) -> Unit,
        onCategoryClick: (FapCategory) -> Unit
    ) {
        ComposableCatalogTabScreen(
            onOpenFapItem = onOpenFapItem,
            onCategoryClick = onCategoryClick,
            errorsRenderer = errorsRenderer,
            installationButton = { fapItem, modifier ->
                buttonInstallationUIApiImpl.ComposableButton(
                    config = fapItem?.toFapButtonConfig(),
                    modifier = modifier,
                    fapButtonSize = FapButtonSize.COMPACTED
                )
            }
        )
    }
}
