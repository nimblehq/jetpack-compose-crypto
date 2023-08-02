package co.nimblehq.compose.crypto.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun AppDialogPopUp(
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes actionText: Int,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .width(DialogWidth)
        ) {
            Text(
                text = stringResource(id = title),
                style = AppTheme.typography.h6,
                color = Color.Black,
                modifier = Modifier.padding(top = Dp16, start = Dp16, end = Dp16)
            )
            Text(
                text = stringResource(id = message),
                style = AppTheme.typography.body1,
                color = Color.Black,
                modifier = Modifier.padding(Dp16)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dp16, end = Dp8)
            ) {
                TextButton(
                    onClick = onClick
                ) {
                    Text(
                        text = stringResource(id = actionText),
                        style = AppTheme.styles.semiBold16,
                        color = Color.Blue,
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AppDialogPopUpPreview() {
    ComposeTheme {
        Box {
            AppDialogPopUp(
                onDismiss = { /*TODO*/ },
                onClick = { /*TODO*/ },
                message = R.string.no_internet_message,
                actionText = android.R.string.ok,
                title = R.string.no_internet_title
            )
        }
    }
}
