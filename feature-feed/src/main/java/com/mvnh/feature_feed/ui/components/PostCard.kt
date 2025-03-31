package com.mvnh.feature_feed.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mvnh.feature_feed.R
import com.mvnh.feature_feed.domain.model.Post

@Composable
internal fun PostCard(
    post: Post,
    navigateToArticle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var contentMaxLines by rememberSaveable { mutableIntStateOf(3) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = post.author,
                fontWeight = FontWeight.Bold
            )
            Column {
                Text(
                    text = post.content,
                    maxLines = contentMaxLines,
                    modifier = Modifier
                        .clickable {
                            contentMaxLines = if (contentMaxLines == 3) Int.MAX_VALUE else 3
                        }
                )
                if (contentMaxLines == 3) {
                    Text(
                        text = stringResource(id = R.string.more),
                        color = Color.Gray
                    )
                }
            }
            post.linkedArticleTitle?.let { articleTitle ->
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { navigateToArticle() },
                    colors = CardDefaults.cardColors().copy(
                        containerColor = Color.Gray
                    )
                ) {
                    Text(
                        text = articleTitle,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
