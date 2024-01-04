package core.ui.misc.extension

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect


/**
 * Draws text centered horizontally and below the point [cx] and [cy].
 * Note, that [paint] text alignment must NOT be set [Paint.Align.CENTER]
 */
fun Canvas.drawTextBelow(text: String, cx: Float, cy: Float, boundsHolder: Rect, paint: Paint) {
    paint.getTextBounds(text, 0, text.length, boundsHolder)
    drawText(text, cx - boundsHolder.exactCenterX(), cy - boundsHolder.top, paint)
}

fun Canvas.drawTextCentered(text: String, cx: Float, cy: Float, boundsHolder: Rect, paint: Paint) {
    paint.getTextBounds(text, 0, text.length, boundsHolder)
    drawText(text, cx - boundsHolder.exactCenterX(), cy - boundsHolder.exactCenterY(), paint);
}
