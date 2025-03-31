@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_feed.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mvnh.feature_feed.R
import com.mvnh.feature_feed.ui.viewmodel.FeedViewModel
import com.mvnh.navigation.NavigableRoute
import com.mvnh.navigation.OnNavigateTo
import com.mvnh.navigation.PreviousScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CreatePostScreen(
    linkedArticleTitle: String?,
    linkedArticleUrl: String?,
//    onDoneClick: (
//        author: String,
//        content: String,
//        articleTitle: String?,
//        articleUrl: String?
//    ) -> Unit,
    onNavigateTo: OnNavigateTo,
    viewModel: FeedViewModel = koinViewModel()
) {
    var author by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var tags by rememberSaveable { mutableStateOf("") }

    fun validateInputs(author: String, content: String): Boolean {
        return author.isNotBlank() && content.isNotBlank()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.create_post)
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
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (validateInputs(author, content)) {
                                viewModel.createPost(
                                    author = author,
                                    content = content,
                                    tags = listOf(), // TODO: should be implemented
                                    linkedArticleTitle = linkedArticleTitle,
                                    linkedArticleUrl = linkedArticleUrl
                                )
                                onNavigateTo(NavigableRoute.Feed.Posts) {}
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = createPostScreenModifier(innerPadding)) {
            AuthorInput(
                author = author,
                onAuthorChange = { author = it }
            )
            ContentInput(
                content = content,
                onContentChange = { content = it }
            )
        }
    }
}

@Composable
fun AuthorInput(
    author: String,
    onAuthorChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = author,
        onValueChange = onAuthorChange,
        label = {
            Text(
                text = stringResource(id = R.string.author)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ContentInput(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = content,
        onValueChange = onContentChange,
        label = {
            Text(
                text = stringResource(id = R.string.content)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
        ),
        modifier = modifier.fillMaxWidth()
    )
}

private fun createPostScreenModifier(paddingValues: PaddingValues) = Modifier
    .fillMaxSize()
    .padding(paddingValues)
    .padding(16.dp)
