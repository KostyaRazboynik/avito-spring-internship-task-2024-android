package com.kostyarazboynik.kinopoisksearch.ui.dagger

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.kostyarazboynik.kinopoisksearch.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent
import kotlinx.coroutines.CoroutineScope

@UIScope
@Subcomponent(
    modules = [
    ],
)
interface UIComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun appCompatActivity(activity: AppCompatActivity): Builder

        @BindsInstance
        fun mainActivity(mainActivity: MainActivity): Builder

        @BindsInstance
        fun componentScope(
            @UICoroutineScope
            componentScope: CoroutineScope
        ): Builder

        fun build(): UIComponent
    }
}
