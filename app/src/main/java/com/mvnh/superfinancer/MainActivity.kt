package com.mvnh.superfinancer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mvnh.superfinancer.di.SuperFinancer
import com.mvnh.superfinancer.ui.theme.SuperFinancerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperFinancerTheme {
                SuperFinancer()
            }
        }
    }
}
