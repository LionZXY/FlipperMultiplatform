package com.flipperdevices.faphub.catalogtab.impl.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.flipperdevices.faphub.appcard.composable.paging.ComposableFapsList
import com.flipperdevices.faphub.appcard.composable.paging.ComposableSortChoice
import com.flipperdevices.faphub.catalogtab.impl.composable.categories.ComposableCategories
import com.flipperdevices.faphub.catalogtab.impl.viewmodel.CategoriesViewModel
import com.flipperdevices.faphub.catalogtab.impl.viewmodel.FapsListViewModel
import com.flipperdevices.faphub.dao.api.model.FapCategory
import com.flipperdevices.faphub.dao.api.model.FapItemShort
import com.flipperdevices.faphub.errors.api.FapErrorSize
import com.flipperdevices.faphub.errors.api.FapHubComposableErrorsRenderer
import org.koin.compose.koinInject

@Composable
fun ComposableCatalogTabScreen(
    onOpenFapItem: (FapItemShort) -> Unit,
    onCategoryClick: (FapCategory) -> Unit,
    errorsRenderer: FapHubComposableErrorsRenderer,
    modifier: Modifier = Modifier,
    installationButton: @Composable (FapItemShort?, Modifier) -> Unit
) {

    val fapsListViewModel = koinInject<FapsListViewModel>()
    val fapsList = fapsListViewModel.faps.collectAsLazyPagingItems()
    val sortType by fapsListViewModel.getSortTypeFlow().collectAsState()

    val categoriesViewModel = koinInject<CategoriesViewModel>()
    val categoriesLoadState by categoriesViewModel.getCategoriesLoadState().collectAsState()
    LazyColumn(modifier) {
        ComposableCategories(
            loadState = categoriesLoadState,
            onCategoryClick = onCategoryClick,
            onRetry = categoriesViewModel::onRefresh,
            errorsRenderer = errorsRenderer
        )
        if (fapsList.loadState.refresh !is LoadState.Error) {
            item {
                ComposableSortChoice(
                    title = "All Apps",
                    sortType = sortType,
                    onSelectSortType = fapsListViewModel::onSelectSortType
                )
            }
        }
        ComposableFapsList(
            faps = fapsList,
            onOpenFapItem = onOpenFapItem,
            errorsRenderer = errorsRenderer,
            installationButton = installationButton,
            defaultFapErrorSize = FapErrorSize.IN_LIST
        )
    }
}
