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
import androidx.compose.ui.unit.dp
import co.nimblehq.compose.crypto.ui.theme.Color

@Composable
fun ChartIntervalsButtonGroup(
    modifier: Modifier
) {

    val selectedColor = remember { mutableStateOf(0) }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
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
                androidx.compose.ui.graphics.Color.Transparent
            }

            ChartIntervalsButton(
                modifier = Modifier
                    .requiredWidth(45.dp)
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp)
                    ),
                interval = interval,
                onClick = { selectedColor.value = index }
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
            .padding(vertical = 4.dp, horizontal = 8.dp),
        textAlign = TextAlign.Center,
        text = interval.text,
    )
}

@Preview
@Composable
fun ChartIntervalsButtonGroupPreview() {
    ChartIntervalsButtonGroup(
        modifier = Modifier.fillMaxWidth()
    )
}
