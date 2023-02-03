package co.nimblehq.compose.crypto.ui.components.linechart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import me.bytebeats.views.charts.line.*
import me.bytebeats.views.charts.line.render.line.EmptyLineShader
import me.bytebeats.views.charts.line.render.line.ILineDrawer
import me.bytebeats.views.charts.line.render.line.ILineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.point.IPointDrawer
import me.bytebeats.views.charts.simpleChartAnimation

private const val DEFAULT_AXIS_SIZE = 0f

@Suppress("MagicNumber", "LongMethod")
@Composable
fun CoinPriceChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = FilledCircularPointDrawer(),
    lineDrawer: ILineDrawer = SolidLineDrawer(),
    lineShader: ILineShader = EmptyLineShader,
    labelDrawer: ILabelDrawer = CoinPriceLabelDrawer(),
    horizontalOffset: Float = 5F,
) {
    check(horizontalOffset in 0F..25F) {
        "Horizontal Offset is the percentage offset from side, and must be between 0 and 25, included."
    }
    val transitionAnimation = remember(lineChartData.points) { Animatable(initialValue = 0F) }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(184.dp)
    ) {
        drawIntoCanvas { canvas ->
            val yAxisDrawableArea = computeYAxisDrawableArea(
                xAxisLabelSize = DEFAULT_AXIS_SIZE,
                size = size
            )
            val xAxisDrawableArea = computeXAxisDrawableArea(
                yAxisWidth = yAxisDrawableArea.width,
                labelHeight = DEFAULT_AXIS_SIZE,
                size = size
            )

            val chartDrawableArea = computeDrawableArea(
                xAxisDrawableArea = xAxisDrawableArea,
                yAxisDrawableArea = yAxisDrawableArea,
                size = size,
                offset = horizontalOffset
            ).copy(left = 0F) // Chart should fill the screen width

            lineDrawer.drawLine(
                drawScope = this,
                canvas = canvas,
                linePath = computeLinePath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )
            lineShader.fillLine(
                drawScope = this,
                canvas = canvas,
                fillPath = computeFillPath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                    transitionProgress = transitionAnimation.value
                )
            )

            if (lineChartData.points.isNotEmpty()) {
                val maxPrice = lineChartData.points.maxOf { it.value }
                val minPrice = lineChartData.points.minOf { it.value }
                val maxPriceIndex = lineChartData.points.indexOfFirst { it.value == maxPrice }
                val minPriceIndex = lineChartData.points.indexOfFirst { it.value == minPrice }

                lineChartData.points.forEachIndexed { index, point ->
                    withProgress(
                        index = index,
                        lineChartData = lineChartData,
                        transitionProgress = transitionAnimation.value
                    ) {
                        val pointLocation = computePointLocation(
                            drawableArea = chartDrawableArea,
                            lineChartData = lineChartData,
                            point = point,
                            index = index
                        )
                        pointDrawer.drawPoint(
                            drawScope = this,
                            canvas = canvas,
                            center = pointLocation
                        )
                        if (index in listOf(minPriceIndex, maxPriceIndex)) {
                            labelDrawer.drawLabel(
                                drawScope = this,
                                canvas = canvas,
                                label = point.label,
                                pointLocation = pointLocation,
                                xAxisArea = xAxisDrawableArea,
                                isHighestPrice = index == maxPriceIndex
                            )
                        }
                    }
                }
            }
        }
    }
}
