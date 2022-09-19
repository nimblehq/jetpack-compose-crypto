package co.nimblehq.compose.crypto.ui.components.linechart

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

class EmptyYAxisDrawer: me.bytebeats.views.charts.line.render.yaxis.IYAxisDrawer {

    override fun drawAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        minValue: Float,
        maxValue: Float
    ) {
        // Do nothing
    }

    override fun drawAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect) {
        // Do nothing
    }

}