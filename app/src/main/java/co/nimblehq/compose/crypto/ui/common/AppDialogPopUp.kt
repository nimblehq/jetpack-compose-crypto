package co.nimblehq.compose.crypto.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import co.nimblehq.compose.crypto.ui.theme.*

@Composable
fun AppDialogPopUp(
    onDismiss: () -> Unit,
    title: String,
    message: String,
    dialogActions: List<DialogActionModel>,
    onClickAction: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
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
                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = Dp16, end = Dp8)
                ) {
                    for(action in dialogActions) {
                        TextButton(
                            onClick = {
                                action.onClickAction()
                                onClickAction()
                            },
                        ) {
                            Text(
                                text = action.actionText,
                                style = AppTheme.styles.semiBold16,
                                color = AppTheme.colors.dialogText,
                            )
                        }
                    }
                }
            }
        }
    }
}

data class DialogActionModel(
    val actionText: String,
    val onClickAction: () -> Unit
)

@Composable
@Preview(showSystemUi = true)
fun AppDialogPopUpPreview() {
    ComposeTheme {
        Box {
            AppDialogPopUp(
                onDismiss = {},
                message = "No internet connection was found. Please check your internet connection and try again.",
                title = "Oops!",
                dialogActions = listOf(DialogActionModel(
                    actionText = "OK",
                    onClickAction = {}
                )),
                onClickAction = {}
            )
        }
    }
}
