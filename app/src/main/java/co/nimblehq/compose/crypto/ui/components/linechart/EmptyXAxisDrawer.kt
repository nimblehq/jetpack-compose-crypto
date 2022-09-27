package co.nimblehq.compose.crypto.ui.components.linechart

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.bytebeats.views.charts.line.render.xaxis.IXAxisDrawer

class EmptyXAxisDrawer: IXAxisDrawer {

    override fun drawXAxisLabels(
        drawScope: DrawScope,
        canvas: Canvas,
        drawableArea: Rect,
        labels: List<*>
    ) {
        // Do nothing
    }

    override fun drawXAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect) {
        // Do nothing
    }

    override fun requireHeight(drawScope: DrawScope): Float {
        // Do nothing
        return 0f
    }
}
