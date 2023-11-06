package androidx.lifecycle

import kotlinx.coroutines.GlobalScope

open class ViewModel {

}

val ViewModel.viewModelScope
    get() = GlobalScope