package com.flipperdevices.faphub.catalogtab.impl.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flipperdevices.bridge.dao.api.FapHubHideItemApi
import com.flipperdevices.core.pager.loadingPagingDataFlow
import com.flipperdevices.faphub.dao.api.FapNetworkApi
import com.flipperdevices.faphub.dao.api.model.SortType
import com.flipperdevices.faphub.target.api.FlipperTargetProviderApi
import kotlinx.coroutines.flow.*

class FapsListViewModel(
    private val fapNetworkApi: FapNetworkApi,
    //private val fapManifestApi: FapManifestApi,
    fapHubHideItemApi: FapHubHideItemApi,
    targetProviderApi: FlipperTargetProviderApi,
) : ViewModel() {
    private val sortState = MutableStateFlow<SortType>(SortType.UPDATE_AT_DESC)

    fun getSortTypeFlow(): StateFlow<SortType> = sortState

    val faps = combine(
        sortState,
        targetProviderApi.getFlipperTarget(),
        fapHubHideItemApi.getHiddenItems()
    ) { sortType, target, hiddenItems ->
        if (target == null) {
            return@combine loadingPagingDataFlow()
        }
        return@combine Pager(
            PagingConfig(pageSize = FAPS_PAGE_SIZE)
        ) {
            FapsPagingSource(fapNetworkApi, sortType, target, hiddenItems)
        }.flow
    }.flatMapLatest { it }.cachedIn(viewModelScope)

    fun onSelectSortType(sortType: SortType) {
        sortState.update { sortType }
    }

    fun refreshManifest() {
        //fapManifestApi.invalidateAsync()
    }
}
