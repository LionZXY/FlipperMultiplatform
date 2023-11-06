package com.lionzxy.flipperapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import com.flipperdevices.main.impl.api.FapHubMainScreenComponent
import org.koin.core.Koin
import org.koin.core.context.KoinContext

interface RootComponent {

    val stack: Value<ChildStack<*, DecomposeComponent>>
    fun onBackClicked(toIndex: Int)
}

interface DecomposeComponent {
    @Composable
    fun Render()
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val koin: Koin
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<DecomposeComponentsData>()

    override val stack: Value<ChildStack<*, DecomposeComponent>> =
        childStack(
            source = navigation,
            serializer = DecomposeComponentsData.serializer(),
            initialConfiguration = DecomposeComponentsData.MainScreen, // The initial child component is List
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )

    private fun child(config: DecomposeComponentsData, componentContext: ComponentContext): DecomposeComponent =
        when (config) {
            is DecomposeComponentsData.MainScreen -> FapHubMainScreenComponent(componentContext, koin)
        }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }
}

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        it.instance.Render()
    }
}