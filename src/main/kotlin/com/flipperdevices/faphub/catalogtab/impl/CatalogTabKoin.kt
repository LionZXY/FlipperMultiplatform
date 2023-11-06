package com.flipperdevices.faphub.catalogtab.impl

import com.flipperdevices.faphub.catalogtab.api.CatalogTabApi
import com.flipperdevices.faphub.catalogtab.impl.api.CatalogTabApiImpl
import com.flipperdevices.faphub.catalogtab.impl.viewmodel.CategoriesViewModel
import com.flipperdevices.faphub.catalogtab.impl.viewmodel.FapsListViewModel
import org.koin.dsl.module

fun catalogTabKoin() = module {
    single<CatalogTabApi> { CatalogTabApiImpl(get()) }
    single { FapsListViewModel(get(), get(), get()) }
    single { CategoriesViewModel(get(), get()) }
}