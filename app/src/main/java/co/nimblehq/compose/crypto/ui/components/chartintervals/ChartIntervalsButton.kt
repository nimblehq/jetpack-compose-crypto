package co.nimblehq.compose.crypto.ui.components.chartintervals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import co.nimblehq.compose.crypto.ui.theme.Color
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp12
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp14
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp4
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp45
import co.nimblehq.compose.crypto.ui.theme.Dimension.Dp8
import co.nimblehq.compose.crypto.ui.theme.Style

@Composable
fun ChartIntervalsButtonGroup(
    modifier: Modifier,
    onIntervalChanged: (TimeIntervals) -> Unit
) {

    val selectedColor = remember { mutableStateOf(0) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dp14)
    ) {
        val listOfInterval = listOf(
            TimeIntervals.ONE_DAY,
            TimeIntervals.ONE_WEEK,
            TimeIntervals.ONE_MONTH,
            TimeIntervals.ONE_YEAR,
            TimeIntervals.FIVE_YEAR
        )

        listOfInterval.forEachIndexed { index, interval ->
            val backgroundColor = if (selectedColor.value == index) {
                Color.CaribbeanGreen
            } else {
                Color.Transparent
            }

            ChartIntervalsButton(
                modifier = Modifier
                    .requiredWidth(Dp45)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(Dp12)
                    ),
                interval = interval,
                onClick = {
                    if (selectedColor.value != index) {
                        selectedColor.value = index
                        onIntervalChanged.invoke(interval)
                    }
                }
            )
        }
    }
}

@Composable
fun ChartIntervalsButton(
    modifier: Modifier,
    interval: TimeIntervals,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = Dp4, horizontal = Dp8),
        textAlign = TextAlign.Center,
        text = interval.text,
        color = Color.White,
        style = Style.medium14()
    )
}

@Preview
@Composable
fun ChartIntervalsButtonGroupPreview() {
    ChartIntervalsButtonGroup(
        modifier = Modifier.fillMaxWidth(),
        onIntervalChanged = {}
    )
}
