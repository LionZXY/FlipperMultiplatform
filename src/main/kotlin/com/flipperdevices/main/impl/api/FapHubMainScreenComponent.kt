package com.flipperdevices.main.impl.api

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.flipperdevices.main.impl.composable.ComposableFapHubMainScreen
import com.lionzxy.flipperapp.navigation.DecomposeComponent
import org.koin.core.Koin
import org.koin.core.context.KoinContext


/*
private val catalogTabApi: CatalogTabApi,
private val searchEntryApi: FapHubSearchEntryApi,
private val categoryEntryApi: FapHubCategoryApi,
private val fapScreenApi: FapScreenApi,
private val installedApi: FapInstalledApi,
private val metricApi: MetricApi*/

class FapHubMainScreenComponent(
    componentContext: ComponentContext,
    private val koin: Koin
) : DecomposeComponent, ComponentContext by componentContext {

    @Composable
    override fun Render() {
        val readyToUpdateCount = 0
        ComposableFapHubMainScreen(
            onBack = {
                //navController.popBackStack()
            },
            catalogTabComposable = {
                /*
                catalogTabApi.ComposableCatalogTab(
                    onOpenFapItem = {
                        metricApi.reportSimpleEvent(SimpleEvent.OPEN_FAPHUB_APP, it.applicationAlias)
                        navController.navigate(fapScreenApi.getFapScreen(it.id))
                    },
                    onCategoryClick = {
                        metricApi.reportSimpleEvent(SimpleEvent.OPEN_FAPHUB_CATEGORY, it.name)
                        navController.navigate(categoryEntryApi.open(it))
                    }
                )*/
            },
            installedTabComposable = {
                /*
                installedApi.ComposableInstalledTab(onOpenFapItem = {
                    metricApi.reportSimpleEvent(SimpleEvent.OPEN_FAPHUB_APP, it)
                    navController.navigate(fapScreenApi.getFapScreen(it))
                })*/
            },
            onOpenSearch = {
                /*
                metricApi.reportSimpleEvent(SimpleEvent.OPEN_FAPHUB_SEARCH)
                navController.navigate(searchEntryApi.start())
                */
            },
            installedNotificationCount = readyToUpdateCount
        )
    }
}
