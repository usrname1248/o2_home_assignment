package com.jozeftvrdy.o2_home_assignment.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jozeftvrdy.o2_home_assignment.core.R
import com.jozeftvrdy.o2_home_assignment.core.extension.nonRippleClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTitleContent(
    title: String,
    text: String,
    onBackClicked: (() -> Unit)? = null,
    additionalContent: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppBarTitle()
                },
                navigationIcon = {
                    if (onBackClicked != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .widthIn(min = 44.dp)
                                .nonRippleClick(
                                    onClick = onBackClicked,
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = R.drawable.ic_arrow_back_black_24),
                                contentDescription = stringResource(id = R.string.app_bar_back_description),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.Center,
        ) {
            ScreenTitleColumnContent(
                title = title,
                text = text,
                additionalContent = additionalContent
            )
        }
    }
}

@Composable
private fun ColumnScope.ScreenTitleColumnContent(
    title: String,
    text: String,
    additionalContent: @Composable ColumnScope.() -> Unit
) {
    Spacer(rawSize = 36)
    ScreenTitle(
        modifier = Modifier
            .fillMaxWidth(),
        text = title,
    )
    Spacer(rawSize = 24)
    ScreenText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        text = text
    )
    Spacer(rawSize = 16)
    additionalContent()
}