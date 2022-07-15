package co.nimblehq.compose.crypto.ui.screens.to_be_removed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.nimblehq.compose.crypto.ui.theme.Dimension

@Composable
fun ContentCard(
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(
            topStart = Dimension.Dp24,
            topEnd = Dimension.Dp24,
            bottomStart = Dimension.Dp0,
            bottomEnd = Dimension.Dp0
        ),
        elevation = Dimension.Dp0,
        modifier = Modifier.fillMaxSize()
    ) {
        content.invoke()
    }
}
