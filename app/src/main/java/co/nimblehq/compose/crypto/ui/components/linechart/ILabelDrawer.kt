package co.nimblehq.compose.crypto.ui.components.linechart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

@Suppress("LongParameterList")
interface ILabelDrawer {
    fun requiredXAxisHeight(drawScope: DrawScope): Float = 0F
    fun requiredAboveBarHeight(drawScope: DrawScope): Float = 0F
    fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: Any?,
        pointLocation: Offset,
        xAxisArea: Rect,
        isHighestPrice: Boolean
    )
}
