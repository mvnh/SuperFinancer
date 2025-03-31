@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_feed.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvnh.feature_feed.R
import com.mvnh.feature_feed.domain.model.Post
import com.mvnh.feature_feed.domain.state.FeedState
import com.mvnh.feature_feed.ui.components.PostCard
import com.mvnh.feature_feed.ui.viewmodel.FeedViewModel
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PostsScreen(
    onNavigateTo: OnNavigateTo,
    viewModel: FeedViewModel = koinViewModel()
) {
    val feed by viewModel.feed.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.feed),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onNavigateTo(NavigableRoute.Feed.CreatePost()) {} },
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
        Column(modifier = feedScreenModifier(innerPadding)) {
            FeedContent(
                state = feed,
                onNavigateTo = onNavigateTo,
                modifier = listModifier()
            )
        }
    }
}

@Composable
private fun FeedContent(
    state: FeedState,
    onNavigateTo: OnNavigateTo,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = state is FeedState.GetFeedSuccess,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.animateContentSize()
    ) {
        (state as? FeedState.GetFeedSuccess)?.let {
            FeedContainer(
                posts = it.posts,
                onNavigateTo = onNavigateTo
            )
        }
    }
}

@Composable
private fun FeedContainer(
    posts: List<Post>,
    onNavigateTo: OnNavigateTo
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        posts.forEach { post ->
            PostCard(
                post = post,
                navigateToArticle = {
                    val title = post.linkedArticleTitle
                    val url = post.linkedArticleUrl
                    if (title != null && url != null) {
                        onNavigateTo(NavigableRoute.Main.Article(title, url)) {}
                    }
                }
            )
        }
    }
}

@Composable
private fun feedScreenModifier(paddingValues: PaddingValues) = Modifier
    .fillMaxSize()
    .padding(paddingValues)
    .verticalScroll(rememberScrollState())

private fun listModifier() = Modifier
    .fillMaxWidth()
    .padding(8.dp)
