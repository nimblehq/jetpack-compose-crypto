package co.nimblehq.compose.crypto.ui.components.linechart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import co.nimblehq.compose.crypto.ui.components.chartintervals.ChartIntervalsButtonGroup
import co.nimblehq.compose.crypto.ui.components.chartintervals.TimeIntervals
import me.bytebeats.views.charts.line.*
import me.bytebeats.views.charts.line.render.line.EmptyLineShader
import me.bytebeats.views.charts.line.render.line.ILineDrawer
import me.bytebeats.views.charts.line.render.line.ILineShader
import me.bytebeats.views.charts.line.render.line.SolidLineDrawer
import me.bytebeats.views.charts.line.render.point.FilledCircularPointDrawer
import me.bytebeats.views.charts.line.render.point.IPointDrawer
import me.bytebeats.views.charts.line.render.xaxis.IXAxisDrawer
import me.bytebeats.views.charts.line.render.xaxis.SimpleXAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.IYAxisDrawer
import me.bytebeats.views.charts.line.render.yaxis.SimpleYAxisDrawer
import me.bytebeats.views.charts.simpleChartAnimation

@Composable
fun CryptoLineChart(
    lineChartData: LineChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    pointDrawer: IPointDrawer = FilledCircularPointDrawer(),
    lineDrawer: ILineDrawer = SolidLineDrawer(),
    lineShader: ILineShader = EmptyLineShader,
    xAxisDrawer: IXAxisDrawer = SimpleXAxisDrawer(),
    yAxisDrawer: IYAxisDrawer = SimpleYAxisDrawer(),
    horizontalOffset: Float = 5F,
    onTimeIntervalChanged: (TimeIntervals) -> Unit
) {
    check(horizontalOffset in 0F..25F) {
        "Horizontal Offset is the percentage offset from side, and must be between 0 and 25, included."
    }
    val transitionAnimation = remember(lineChartData.points) { Animatable(initialValue = 0F) }

    LaunchedEffect(lineChartData.points) {
        transitionAnimation.snapTo(0F)
        transitionAnimation.animateTo(1F, animationSpec = animation)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = modifier.height(184.dp)) {
            drawIntoCanvas { canvas ->
                val yAxisDrawableArea = computeYAxisDrawableArea(
                    xAxisLabelSize = xAxisDrawer.requireHeight(this),
                    size = size
                )
                val xAxisDrawableArea = computeXAxisDrawableArea(
                    yAxisWidth = yAxisDrawableArea.width,
                    labelHeight = xAxisDrawer.requireHeight(this),
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
                lineChartData.points.forEachIndexed { index, point ->
                    withProgress(
                        index = index,
                        lineChartData = lineChartData,
                        transitionProgress = transitionAnimation.value
                    ) {
                        pointDrawer.drawPoint(
                            drawScope = this,
                            canvas = canvas,
                            center = computePointLocation(
                                drawableArea = chartDrawableArea,
                                lineChartData = lineChartData,
                                point = point,
                                index = index
                            )
                        )
                    }
                }
            }
        }

        // Chart intervals
        ChartIntervalsButtonGroup(
            modifier = Modifier
                .padding(top = 24.dp),
            onIntervalChanged = onTimeIntervalChanged
        )
    }
}