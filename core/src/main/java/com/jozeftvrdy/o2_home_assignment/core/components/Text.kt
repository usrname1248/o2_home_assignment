package com.jozeftvrdy.o2_home_assignment.core.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jozeftvrdy.o2_home_assignment.core.R

@Composable
fun AppBarTitle() {
    Text(
        text = stringResource(id = R.string.app_bar_title),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.onSurface,
        )
    )
}

@Composable
fun ScreenTitle(
    modifier : Modifier = Modifier,
    text: String,
    fontSize:  TextUnit = 24.sp,
    color: Color = MaterialTheme.colorScheme.primary,
    fontWeight: FontWeight = FontWeight.SemiBold,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun ScreenText(
    modifier : Modifier = Modifier,
    text: String,
    fontSize:  TextUnit = 16.sp,
    color: Color = MaterialTheme.colorScheme.secondary,
    fontWeight: FontWeight = FontWeight.Medium,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight
        )
    )
}

@Composable
fun ButtonText(
    text: String,
    fontSize:  TextUnit = 16.sp,
    enabled: Boolean = true,
    color: Color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary,
    fontWeight: FontWeight = if (enabled) FontWeight.SemiBold else  FontWeight.Thin,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        textAlign = textAlign,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight
        )
    )
}