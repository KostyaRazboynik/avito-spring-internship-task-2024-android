package com.kostyarazboynik.kinopoisksearch.dagger

import android.app.Application
import android.content.Context
import com.kostyarazboynik.kinopoisksearch.dagger.module.BuildTypeModule
import com.kostyarazboynik.kinopoisksearch.MainActivity
import com.kostyarazboynik.kinopoisksearch.MoviesApp
import com.kostyarazboynik.kinopoisksearch.dagger.module.DatabaseModule
import com.kostyarazboynik.kinopoisksearch.dagger.module.MoviesApiModule
import com.kostyarazboynik.kinopoisksearch.dagger.module.RepositoryModule
import com.kostyarazboynik.kinopoisksearch.dagger.module.UseCasesModule
import com.kostyarazboynik.kinopoisksearch.ui.dagger.UIComponent
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiComponent
import com.kostyarazboynik.movielist.dagger.FeatureMovieListUiModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        BuildTypeModule::class,
        DatabaseModule::class,
        MoviesApiModule::class,
        RepositoryModule::class,
        UseCasesModule::class,
        FeatureMovieListUiModule::class,
    ]
)
interface AppComponent {

    fun inject(app: MoviesApp)

    fun inject(activity: MainActivity)

    fun createUIComponent(): UIComponent.Builder

    fun featureMovieListUiComponent(): FeatureMovieListUiComponent.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun componentScope(componentScope: CoroutineScope): Builder

        fun build(): AppComponent
    }

    companion object : ScopeComponentProvider<AppComponent>() {

        fun init(
            application: Application,
        ) = store(
            coroutineScopeName = "AppComponent"
        ) { componentScope ->
            DaggerAppComponent
                .builder()
                .componentScope(componentScope)
                .context(application)
                .application(application)
                .build()
        }
    }
}
