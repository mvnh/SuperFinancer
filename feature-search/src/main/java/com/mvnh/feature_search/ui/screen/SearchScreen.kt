@file:OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)

package com.mvnh.feature_search.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.mvnh.common.ui.components.ShimmerEffect
import com.mvnh.feature_search.domain.model.SearchArticle
import com.mvnh.feature_search.domain.state.SearchArticlesState
import com.mvnh.feature_search.domain.state.SearchTickersState
import com.mvnh.feature_search.ui.components.SearchArticleCard
import com.mvnh.feature_search.ui.components.SearchBar
import com.mvnh.feature_search.ui.components.SearchTickerCard
import com.mvnh.feature_search.ui.viewmodel.SearchViewModel
import com.mvnh.navigation.OnNavigateTo
import com.mvnh.navigation.PreviousScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SearchScreen(
    onNavigateTo: OnNavigateTo,
    viewModel: SearchViewModel = koinViewModel()
) {
    val windowInfo = LocalWindowInfo.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val searchQueryState = remember { MutableStateFlow("") }
    val searchTickers by viewModel.searchTickers.collectAsState()
    val searchArticles by viewModel.searchArticles.collectAsState()

    LaunchedEffect(searchQuery) {
        searchQueryState.value = searchQuery
    }

    LaunchedEffect(searchQueryState) {
        searchQueryState
            .debounce(1000)
            .collectLatest { query ->
                if (query.isNotBlank()) {
                    viewModel.performSearch(query)
                }
            }
    }

    LaunchedEffect(windowInfo) {
        snapshotFlow { windowInfo.isWindowFocused }.collect { isWindowFocused ->
            if (isWindowFocused) {
                focusRequester.requestFocus()
            }
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(bottom = 16.dp)
            ) {
                TopAppBar(
                    title = {
                        SearchBar(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            onSearchCancel = {
                                searchQuery = ""
                                focusManager.clearFocus()
                                onNavigateTo(PreviousScreen) {}
                            },
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .fillMaxWidth()
                                .height(54.dp)
                                .padding(end = 16.dp)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            SearchTickersContent(searchTickers)
            SearchArticlesContent(searchArticles)
        }
    }
}

@Composable
private fun SearchTickersContent(state: SearchTickersState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = state is SearchTickersState.Loading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize(),
            content = {
                ShimmerEffect(
                    modifier = searchTickerShimmerItemModifier()
                )
            }
        )
        AnimatedVisibility(
            visible = state is SearchTickersState.SearchTickersSuccess,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize()
        ) {
            (state as? SearchTickersState.SearchTickersSuccess)?.let {
                SearchTickerCard(
                    searchTickers = it.tickers,
                    modifier = searchTickerItemModifier()
                )
            }
        }
    }
}

@Composable
private fun SearchArticlesContent(state: SearchArticlesState) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = state is SearchArticlesState.Loading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize(),
            content = { SearchArticlesShimmerList() }
        )
        AnimatedVisibility(
            visible = state is SearchArticlesState.SearchArticlesSuccess,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.animateContentSize()
        ) {
            (state as? SearchArticlesState.SearchArticlesSuccess)?.let {
                SearchArticlesList(it.articles)
            }
        }
    }
}

@Composable
private fun SearchArticlesShimmerList() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(top = 8.dp)
            .height(900.dp)
    ) {
        repeat(5) {
            ShimmerEffect(
                modifier = searchArticleShimmerItemModifier()
            )
        }
    }
}

@Composable
private fun SearchArticlesList(searchArticles: List<SearchArticle>) {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        searchArticles.forEach { searchArticle ->
            SearchArticleCard(
                searchArticle = searchArticle,
                modifier = searchArticleItemModifier()
            )
        }
    }
}

private fun searchTickerItemModifier() = Modifier
    .padding(8.dp)
    .fillMaxWidth()
    .wrapContentHeight()
    .clip(RoundedCornerShape(16.dp))

private fun searchTickerShimmerItemModifier() = Modifier
    .padding(8.dp)
    .fillMaxWidth()
    .height(150.dp)
    .clip(RoundedCornerShape(16.dp))

private fun searchArticleShimmerItemModifier() = Modifier
    .padding(horizontal = 8.dp)
    .fillMaxWidth()
    .height(300.dp)
    .clip(RoundedCornerShape(16.dp))

private fun searchArticleItemModifier() = Modifier
    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    .fillMaxWidth()
    .wrapContentHeight()
    .clip(RoundedCornerShape(16.dp))
