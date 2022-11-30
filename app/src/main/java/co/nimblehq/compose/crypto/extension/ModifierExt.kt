package co.nimblehq.compose.crypto.extension

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import co.nimblehq.compose.crypto.ui.theme.Dp0

/**
 * Original solution: https://github.com/wasim15185/jetpack-compose-crypto/blob/develop/app/src/main/java/co/nimblehq/compose/crypto/extension/BoxShadow.kt
 * Draw a shadow affect under component
 *
 * borderRadius: Border radius of shadow
 * blurRadius: Blur the shadow
 * offsetY: Position of shadow according to Y axis
 * offsetX: Position of shadow according to X axis
 * spread: Squeeze or release the shadow of CardView
 */
fun Modifier.boxShadow(
    color: Color = Color.Black,
    borderRadius: Dp = Dp0,
    blurRadius: Dp = Dp0,
    offsetY: Dp = Dp0,
    offsetX: Dp = Dp0,
    spread: Dp = Dp0,
    modifier: Modifier = Modifier,
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != Dp0) {
                /* The feature maskFilter used below to apply the blur effect only works
                    with hardware acceleration disabled.
                 */
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)
