package core.ui.misc

import android.content.res.Resources
import android.util.DisplayMetrics

enum class DisplayUnit {

    PX {
        override fun convert(sourceCount: Float, sourceUnit: DisplayUnit): Float {
            return sourceUnit.toPx(sourceCount)
        }

        override fun toPx(count: Float): Float {
            return count
        }

        override fun toDp(count: Int): Float {
            return count / displayMetrics.density
        }

        override fun toSp(count: Float): Float {
            return count / displayMetrics.scaledDensity
        }
    },

    DP {
        override fun convert(sourceCount: Float, sourceUnit: DisplayUnit): Float {
            return sourceUnit.toDp(sourceCount.toInt())
        }

        override fun toPx(count: Float): Float {
            return count * displayMetrics.density
        }

        override fun toDp(count: Int): Float {
            return count.toFloat()
        }

        override fun toSp(count: Float): Float {
            return count * (displayMetrics.scaledDensity / displayMetrics.density)
        }
    },

    SP {
        override fun convert(sourceCount: Float, sourceUnit: DisplayUnit): Float {
            return sourceUnit.toSp(sourceCount)
        }

        override fun toPx(count: Float): Float {
            return count * displayMetrics.scaledDensity
        }

        override fun toDp(count: Int): Float {
            return count * (displayMetrics.density / displayMetrics.scaledDensity)
        }

        override fun toSp(count: Float): Float {
            return count
        }
    }

    ;

    abstract fun convert(sourceCount: Float, sourceUnit: DisplayUnit): Float
    abstract fun toPx(count: Float): Float
    abstract fun toDp(count: Int): Float
    abstract fun toSp(count: Float): Float

    companion object {
        val displayMetrics: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }
    }
}