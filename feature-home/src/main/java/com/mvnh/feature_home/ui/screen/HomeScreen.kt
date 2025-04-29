@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_home.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mvnh.common.ui.components.ContentSection
import com.mvnh.common.ui.components.ShimmerEffect
import com.mvnh.feature_home.R
import com.mvnh.feature_home.domain.model.Article
import com.mvnh.feature_home.domain.model.Ticker
import com.mvnh.feature_home.domain.state.ArticlesState
import com.mvnh.feature_home.domain.state.TickersState
import com.mvnh.feature_home.ui.components.ArticleCard
import com.mvnh.feature_home.ui.components.HomeScreenTopBar
import com.mvnh.feature_home.ui.components.TickerCard
import com.mvnh.feature_home.ui.viewmodel.HomeViewModel
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(
    onNavigateTo: OnNavigateTo,
    viewModel: HomeViewModel = koinViewModel()
) {
    val tickers by viewModel.tickers.collectAsState()
    val articles by viewModel.articles.collectAsState()

    Scaffold(
        topBar = {
            HomeScreenTopBar(
                onSearchBarClicked = {
                    onNavigateTo(NavigableRoute.Search) {}
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Column(modifier = mainScreenModifier(innerPadding)) {
            ContentSection(
                title = stringResource(id = R.string.tickers),
                isLoading = tickers is TickersState.Loading,
                trailingIcon = { RefreshIcon() },
                onTrailingIconClick = viewModel::loadTickers,
                content = { TickersContent(tickers) }
            )
            ContentSection(
                title = stringResource(id = R.string.articles),
                isLoading = articles is ArticlesState.Loading,
                trailingIcon = { RefreshIcon() },
                onTrailingIconClick = viewModel::loadArticles
            ) {
                ArticlesContent(
                    state = articles,
                    onArticleClick = {
                        onNavigateTo(
                            NavigableRoute.Main.Article(
                                title = it.title,
                                url = it.url
                            )
                        ) {}
                    }
                )
            }
        }
    }
}

@Composable
private fun TickersContent(state: TickersState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = state is TickersState.Loading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize(),
            content = { TickersShimmerList() }
        )
        AnimatedVisibility(
            visible = state is TickersState.GetTickersSuccess,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize()
        ) {
            (state as? TickersState.GetTickersSuccess)?.let {
                TickersList(it.tickers)
            }
        }
    }
}

@Composable
private fun TickersShimmerList() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = listModifier()
    ) {
        items(5) {
            ShimmerEffect(
                modifier = tickerShimmerItemModifier()
            )
        }
    }
}

@Composable
private fun TickersList(tickers: List<Ticker>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = listModifier()
    ) {
        items(
            items = tickers,
            key = { it.ticker }
        ) { ticker ->
            TickerCard(ticker = ticker)
        }
    }
}

@Composable
private fun ArticlesContent(
    state: ArticlesState,
    onArticleClick: (Article) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = state is ArticlesState.Loading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize(),
            content = { ArticlesShimmerList() }
        )
        AnimatedVisibility(
            visible = state is ArticlesState.GetArticlesSuccess,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize()
        ) {
            (state as? ArticlesState.GetArticlesSuccess)?.let {
                ArticlesList(
                    articles = it.articles,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

@Composable
private fun ArticlesShimmerList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = listModifier()
    ) {
        repeat(5) {
            ShimmerEffect(
                modifier = articleShimmerItemModifier()
            )
        }
    }
}

@Composable
private fun ArticlesList(
    articles: List<Article>,
    onArticleClick: (Article) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = listModifier()
    ) {
        articles.forEach { article ->
            ArticleCard(
                article = article,
                onClick = { onArticleClick(article) }
            )
        }
    }
}

@Composable
private fun RefreshIcon() {
    Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = null
    )
}

@Composable
private fun mainScreenModifier(innerPadding: PaddingValues) = Modifier
    .fillMaxSize()
    .padding(innerPadding)
    .verticalScroll(rememberScrollState())

private fun listModifier() = Modifier
    .padding(
        start = 8.dp,
        end = 8.dp,
        bottom = 8.dp
    )
    .clip(RoundedCornerShape(16.dp))

private fun articleShimmerItemModifier() = Modifier
    .fillMaxWidth()
    .height(300.dp)
    .clip(RoundedCornerShape(16.dp))

private fun tickerShimmerItemModifier() = Modifier
    .width(300.dp)
    .height(100.dp)
    .clip(RoundedCornerShape(16.dp))
