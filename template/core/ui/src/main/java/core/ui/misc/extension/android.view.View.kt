package core.ui.misc.extension

import android.graphics.*
import android.view.HapticFeedbackConstants
import android.view.View

fun View.hapticTap() {
    context.vibrateHaptic()
}

fun View.hapticKeyRelease() {
//    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
//        performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
//    } else {
//        hapticKey()
//    }
    context.vibrateHaptic()
}

fun View.asBitmap(): Bitmap? {
    if (width <= 0 || height <= 0) return null
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

fun Bitmap.cropped(): Bitmap {
    var minX: Int = width
    var minY: Int = height
    var maxX = -1
    var maxY = -1
    for (y in 0 until height) {
        for (x in 0 until width) {
            val alpha: Int = getPixel(x, y) shr 24 and 255
            if (alpha > 0) {
                if (x < minX) minX = x
                if (x > maxX) maxX = x
                if (y < minY) minY = y
                if (y > maxY) maxY = y
            }
        }
    }
    val cropped =
        if (maxX < minX || maxY < minY) {
            this
        } else {
            try {
                Bitmap.createBitmap(
                    this,
                    minX,
                    minY,
                    maxX - minX + 1,
                    maxY - minY + 1
                )
            } finally {
                recycle()
            }
        }
    return cropped
}

fun Bitmap.rounded(radius: Float): Bitmap? {
    val bitmap = this
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint().apply {
        isAntiAlias = true
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }
    val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    val path = Path().apply {
        addRoundRect(rectF, radius, radius, Path.Direction.CW)
    }
    canvas.drawPath(path, paint)
    recycle()
    return output
}