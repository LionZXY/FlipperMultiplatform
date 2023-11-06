package com.flipperdevices.bridge.dao.impl

import com.flipperdevices.bridge.dao.api.FapHubHideItemApi
import com.flipperdevices.faphub.dao.api.model.FapHubHiddenItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FapHubHideItemApiImpl : FapHubHideItemApi {
    private val hiddenItems = MutableStateFlow(emptySet<FapHubHiddenItem>())

    override fun getHiddenItems(): Flow<Set<FapHubHiddenItem>> {
        return hiddenItems
    }

    override suspend fun isHidden(applicationUid: String): Boolean {
        return hiddenItems.value.contains(applicationUid)
    }

    override suspend fun hideItem(applicationUid: String) {
        hiddenItems.update { it.plus(applicationUid) }
    }

    override suspend fun unHideItem(applicationUid: String) {
        hiddenItems.update { it.minus(applicationUid) }
    }
}