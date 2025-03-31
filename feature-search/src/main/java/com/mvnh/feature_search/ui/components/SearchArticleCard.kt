package com.mvnh.feature_search.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mvnh.feature_search.domain.model.SearchArticle
import java.time.format.DateTimeFormatter

private const val NYT_STATIC_URL = "https://static01.nyt.com/" // TODO: Move to a common place

@Composable
internal fun SearchArticleCard(
    searchArticle: SearchArticle,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (searchArticle.multimedia.isNotBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("${NYT_STATIC_URL}${searchArticle.multimedia}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Text(
                text = searchArticle.snippet,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = searchArticle.leadParagraph,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                val formattedDate =
                    searchArticle.publishedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

                Text(
                    text = searchArticle.source,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = formattedDate,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
