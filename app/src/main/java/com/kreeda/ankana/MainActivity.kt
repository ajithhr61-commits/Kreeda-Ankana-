package com.kreeda.ankana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kreeda.ankana.navigation.KreedaNavHost
import com.kreeda.ankana.ui.theme.KreedaAnkanaTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity — Single activity that hosts the Compose navigation graph.
 * Uses edge-to-edge display and Hilt for dependency injection.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KreedaAnkanaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KreedaNavHost()
                }
            }
        }
    }
}
