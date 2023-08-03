package co.nimblehq.compose.crypto.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun AppDialogPopUp(
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    title: String,
    message: String,
    actionText: String,
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface {
            Column(
                modifier = Modifier.width(DialogWidth)
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.h6,
                    color = AppTheme.colors.text,
                    modifier = Modifier.padding(top = Dp16, start = Dp16, end = Dp16)
                )
                Text(
                    text = message,
                    style = AppTheme.typography.body1,
                    color = AppTheme.colors.text,
                    modifier = Modifier.padding(Dp16)
                )
                TextButton(
                    onClick = onClick,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = Dp16, end = Dp8)
                ) {
                    Text(
                        text = actionText,
                        style = AppTheme.styles.semiBold16,
                        color = AppTheme.colors.dialogText,
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
                message = "No internet connection was found. Please check your internet connection and try again.",
                actionText = "OK",
                title = "Oops!"
            )
        }
    }
}
