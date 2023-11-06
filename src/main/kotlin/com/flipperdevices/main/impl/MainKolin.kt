package com.flipperdevices.main.impl

import com.flipperdevices.main.impl.viewmodel.MainViewModel
import org.koin.dsl.module

fun mainScreenKoin() = module {
    single<MainViewModel> { MainViewModel() }
}