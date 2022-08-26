package co.nimblehq.compose.crypto.ui.screens.information

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme
import co.nimblehq.compose.crypto.ui.theme.Style
import co.nimblehq.compose.crypto.ui.theme.Style.textColor

@Suppress("FunctionNaming", "LongMethod")
@Composable
fun CoinInfoAppbar(
    modifier: Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = if (MaterialTheme.colors.isLight) {
                    painterResource(id = R.drawable.ic_back_dark)
                } else {
                    painterResource(id = R.drawable.ic_back_light)
                },
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
                painter = if (MaterialTheme.colors.isLight) {
                    painterResource(id = R.drawable.ic_heart_dark)
                } else {
                    painterResource(id = R.drawable.ic_heart_light)
                },
                contentDescription = null
            )
        }
    }
}

@Suppress("FunctionNaming")
@Preview
@Composable
fun CoinInfoAppbarPreview() {
    ComposeTheme {
        Surface {
            CoinInfoAppbar(modifier = Modifier)
        }
    }
}

@Suppress("FunctionNaming")
@Preview
@Composable
fun CoinInfoAppbarPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface {
            CoinInfoAppbar(modifier = Modifier)
        }
    }
}
