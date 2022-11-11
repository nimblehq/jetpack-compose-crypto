package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun SeeAll(
    modifier: Modifier
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = Dp8),
            text = stringResource(id = R.string.home_see_all),
            style = AppTheme.styles.tiffanyBlueMedium14
        )
        Icon(
            modifier = Modifier
                .size(Dp14)
                .align(Alignment.CenterVertically),
            imageVector = Icons.Filled.ArrowForward,
            tint = AppTheme.colors.textSectionLink,
            contentDescription = null
        )
    }
}

@Composable
@Preview
fun SeeAllPreview() {
    ComposeTheme {
        SeeAll(
            modifier = Modifier
        )
    }
}
