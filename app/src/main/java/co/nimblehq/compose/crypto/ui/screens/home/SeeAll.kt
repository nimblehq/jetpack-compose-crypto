package co.nimblehq.compose.crypto.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp14
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style

@Suppress("FunctionNaming")
@Composable
fun SeeAll(
    modifier: Modifier
) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.padding(end = Dp8),
            text = stringResource(id = R.string.home_see_all),
            style = Style.tiffanyBlueMedium14()
        )
        Icon(
            modifier = Modifier
                .size(Dp14)
                .align(Alignment.CenterVertically),
            imageVector = Icons.Filled.ArrowForward,
            tint = Color.TiffanyBlue,
            contentDescription = null
        )
    }
}

@Suppress("FunctionNaming")
@Composable
@Preview
fun SeeAllPreview() {
    ComposeTheme {
        SeeAll(
            modifier = Modifier
        )
    }
}
