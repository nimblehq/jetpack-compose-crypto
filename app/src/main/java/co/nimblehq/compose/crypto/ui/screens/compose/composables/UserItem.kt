package co.nimblehq.compose.crypto.ui.screens.compose.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import co.nimblehq.compose.crypto.R
import co.nimblehq.compose.crypto.model.UserUiModel
import co.nimblehq.compose.crypto.ui.screens.compose.theme.Dimension

@Suppress("LongMethod")
@Composable
fun UserItem(
    userUiModel: UserUiModel,
    onClick: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(userUiModel.toString()) })
    ) {
        Row(modifier = Modifier.padding(Dimension.Dp16)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .width(Dimension.Dp52)
                    .height(Dimension.Dp52),
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = Dimension.Dp16)
            ) {
                with(userUiModel) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = phone,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(top = Dimension.Dp4)
                    )
                }
            }
        }
    }
}
