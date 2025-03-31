@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_home.ui.screen

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mvnh.feature_home.R
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo
import com.mvnh.navigation.PreviousScreen

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun ArticleScreen(
    title: String,
    url: String,
//    onCreatePostClick: (articleTitle: String, articleUrl: String) -> Unit,
//    onBackClick: () -> Unit
    onNavigateTo: OnNavigateTo
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateTo(PreviousScreen) {}
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    onNavigateTo(
                        NavigableRoute.Feed.CreatePost(
                            linkedArticleTitle = title,
                            linkedArticleUrl = url
                        )
                    ) {}
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.create_post)
                    )
                }
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { innerPadding ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            },
            modifier = articleScreenModifier(innerPadding)
        )
    }
}

private fun articleScreenModifier(paddingValues: PaddingValues) = Modifier
    .fillMaxSize()
    .padding(paddingValues)
