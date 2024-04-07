package com.kostyarazboynik.kinopoisksearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.kostyarazboynik.kinopoisksearch.dagger.AppComponent
import com.kostyarazboynik.kinopoisksearch.dagger.ComponentProvider
import com.kostyarazboynik.kinopoisksearch.databinding.ActivityMainBinding
import com.kostyarazboynik.kinopoisksearch.ui.dagger.UIComponent
import com.kostyarazboynik.movielist.ui.MoviesListFragment

class MainActivity private constructor(
    private val mainActivityUIComponentProviderDelegate: MainActivityUIComponentProviderDelegate,
) : AppCompatActivity(),
    ComponentProvider<UIComponent> by mainActivityUIComponentProviderDelegate {
    constructor() : this(
        mainActivityUIComponentProviderDelegate = MainActivityUIComponentProviderDelegate()
    )

    init {
        AppComponent.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityUIComponentProviderDelegate
            .onActivityCreate { uiScopeCoroutineScope ->
                AppComponent
                    .component
                    .createUIComponent()
                    .activity(this)
                    .appCompatActivity(this)
                    .mainActivity(this)
                    .componentScope(uiScopeCoroutineScope)
                    .build()
            }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val fragment = MoviesListFragment.newInstance()

        createFragment(binding, fragment)
    }

    private fun createFragment(binding: ActivityMainBinding, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(binding.frameContent.id, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        mainActivityUIComponentProviderDelegate.onActivityDestroy()
        super.onDestroy()
    }
}