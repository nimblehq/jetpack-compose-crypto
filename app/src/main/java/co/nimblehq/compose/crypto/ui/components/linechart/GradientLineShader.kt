package co.nimblehq.compose.crypto.ui.components.linechart

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import me.bytebeats.views.charts.line.render.line.ILineShader

data class GradientLineShader(
    val colors: List<Color> = listOf(Color.Cyan, Color.Transparent)
) : ILineShader {
    override fun fillLine(drawScope: DrawScope, canvas: Canvas, fillPath: Path) {
        val gradient = Brush.linearGradient(
            colors = colors,
        )
        drawScope.drawPath(path = fillPath, brush = gradient)
    }
}