package co.nimblehq.compose.crypto.ui.screens.detail

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.coinInfoAppBarIconColor
import co.nimblehq.compose.crypto.ui.theme.Style.textColor

@Composable
fun Appbar(
    modifier: Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                tint = MaterialTheme.colors.coinInfoAppBarIconColor,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            // TODO: Remove dummy value when work on Integrate.
            text = "Ethereum",
            color = MaterialTheme.colors.textColor,
            style = Style.medium16()
        )

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_heart),
                tint = MaterialTheme.colors.coinInfoAppBarIconColor,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun AppbarPreview() {
    ComposeTheme {
        Surface {
            Appbar(modifier = Modifier)
        }
    }
}

@Preview
@Composable
fun AppbarPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface {
            Appbar(modifier = Modifier)
        }
    }
}
