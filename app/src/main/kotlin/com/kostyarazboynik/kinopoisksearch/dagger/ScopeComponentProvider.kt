package com.kostyarazboynik.kinopoisksearch.dagger

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel


abstract class ScopeComponentProvider<C : Any> : ComponentProvider<C> {

    @PublishedApi
    internal data class ComponentWithScope<C>(
        val component: C,
        val componentScope: CoroutineScope
    )

    @PublishedApi
    internal var componentWithScopeOrNull: ComponentWithScope<C>? = null
        set(value) {
            field?.componentScope?.cancel()
            field = value
        }

    override val component: C
        get() = componentWithScopeOrNull
            ?.component
            ?: error("Component instance was not stored")

    protected inline fun store(
        coroutineScopeName: String,
        createComponent: (CoroutineScope) -> C
    ) {
        val scope = CoroutineScope(
           SupervisorJob() +
                CoroutineName(coroutineScopeName)
        )
        val component = createComponent(scope)
        componentWithScopeOrNull = ComponentWithScope(
            component = component,
            componentScope = scope
        )
    }

    protected open fun clear() {
        componentWithScopeOrNull = null
    }
}
