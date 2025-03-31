@file:OptIn(ExperimentalMaterial3Api::class)

package com.mvnh.feature_home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvnh.feature_home.R

@Composable
internal fun HomeScreenTopBar(
    onSearchBarClicked: () -> Unit,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(colors.containerColor)
            .padding(bottom = 16.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.main),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            },
            colors = colors
        )
        FakeSearchBar { onSearchBarClicked() }
    }
}
