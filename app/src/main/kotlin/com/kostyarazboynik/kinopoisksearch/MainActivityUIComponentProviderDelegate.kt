package com.kostyarazboynik.kinopoisksearch


import com.kostyarazboynik.kinopoisksearch.dagger.ScopeComponentProvider
import com.kostyarazboynik.kinopoisksearch.ui.dagger.UIComponent
import kotlinx.coroutines.CoroutineScope


class MainActivityUIComponentProviderDelegate : ScopeComponentProvider<UIComponent>() {

    fun onActivityCreate(
        createComponent: (CoroutineScope) -> UIComponent
    ) = store(
        coroutineScopeName = "MainActivity.UIComponent",
        createComponent = createComponent
    )

    fun onActivityDestroy() = clear()
}
