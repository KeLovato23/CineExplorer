package com.idigitalstudios.cineexplorerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import com.idigitalstudios.cineexplorerapp.presentation.navigation.CineExplorerNavGraph
import com.idigitalstudios.cineexplorerapp.ui.theme.CineExplorerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = isSystemInDarkTheme()

            SideEffect {
                enableEdgeToEdge(
                    statusBarStyle = if (darkTheme)
                        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                    else
                        SystemBarStyle.light(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT
                        ),
                    navigationBarStyle = if (darkTheme)
                        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                    else
                        SystemBarStyle.light(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT
                        )
                )
            }

            CineExplorerAppTheme(darkTheme = darkTheme) {
                CineExplorerNavGraph()
            }
        }
    }
}
