package co.nimblehq.compose.crypto.ui.components.linechart

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import me.bytebeats.views.charts.AxisLabelFormatter
import me.bytebeats.views.charts.toLegacyInt

private const val Y_OFFSET = 20f

@Suppress("MagicNumber")
data class CoinPriceLabelDrawer(
    val labelTextSize: TextUnit = 12.sp,
    val labelTextColorLowest: Color = Color.White,
    val labelTextColorHighest: Color = Color.Black,
    val axisLabelFormatter: AxisLabelFormatter = { value -> "$value" }
) : ILabelDrawer {
    private val mLabelTextArea: Float? = null

    private val mPaintLowest by lazy {
        Paint().apply {
            textAlign = Paint.Align.CENTER
            color = labelTextColorLowest.toLegacyInt()
        }
    }

    private val mPaintHighest by lazy {
        Paint().apply {
            textAlign = Paint.Align.CENTER
            color = labelTextColorHighest.toLegacyInt()
        }
    }

    override fun requiredAboveBarHeight(drawScope: DrawScope): Float =
        3F / 2F * labelTextHeight(drawScope)

    override fun requiredXAxisHeight(drawScope: DrawScope): Float = 0F

    override fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: Any?,
        pointLocation: Offset,
        xAxisArea: Rect,
        isHighestPrice: Boolean
    ) {
        val textPaint = if (isHighestPrice) {
            mPaintHighest
        } else {
            mPaintLowest
        }
        val labelValue = axisLabelFormatter(label)
        val bounds = android.graphics.Rect()
        textPaint.getTextBounds(labelValue, 0, labelValue.length, bounds)
        val xCenter = when {
            pointLocation.x <= 0f -> { // First point on the chart
                bounds.width().toFloat() / 2
            }
            pointLocation.x >= canvas.nativeCanvas.width -> { // Last point on the chart
                canvas.nativeCanvas.width - bounds.width().toFloat() / 2
            }
            else -> {
                pointLocation.x
            }
        }
        val yCenter = if (isHighestPrice) {
            pointLocation.y - labelTextHeight(drawScope) / 2
        } else {
            pointLocation.y + labelTextHeight(drawScope) / 2 + Y_OFFSET
        }
        canvas.nativeCanvas.drawText(labelValue, xCenter, yCenter, paint(drawScope, textPaint))
    }

    private fun labelTextHeight(drawScope: DrawScope): Float = with(drawScope) {
        mLabelTextArea ?: (1.5F * labelTextSize.toPx())
    }

    private fun paint(drawScope: DrawScope, textPaint: Paint): Paint =
        with(drawScope) {
            textPaint.apply {
                textSize = labelTextSize.toPx()
            }
        }
}
