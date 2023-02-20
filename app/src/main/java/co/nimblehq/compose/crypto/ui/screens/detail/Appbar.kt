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
import co.nimblehq.compose.crypto.ui.theme.AppTheme
import co.nimblehq.compose.crypto.ui.theme.ComposeTheme

@Composable
fun Appbar(
    modifier: Modifier,
    title: String?,
    onBackIconClick: () -> Unit,
    onRightActionClick: (() -> Unit)? = null
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { onBackIconClick.invoke() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                tint = AppTheme.colors.coinInfoAppBarIconTint,
                contentDescription = null
            )
        }

        title?.let {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                color = AppTheme.colors.text,
                style = AppTheme.styles.medium16
            )
        }

        onRightActionClick?.let {
            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = onRightActionClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_heart),
                    tint = AppTheme.colors.coinInfoAppBarIconTint,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
fun AppbarPreview() {
    ComposeTheme {
        Surface {
            Appbar(
                modifier = Modifier,
                title = "Ethereum",
                onBackIconClick = {}
            )
        }
    }
}

@Preview
@Composable
fun AppbarPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface {
            Appbar(
                modifier = Modifier,
                title = "Ethereum",
                onBackIconClick = {}
            )
        }
    }
}
