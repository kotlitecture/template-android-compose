package core.ui.misc

import android.content.res.Resources
import android.util.DisplayMetrics

enum class DisplayUnit {

    PX {
        override fun convert(from: Float, unit: DisplayUnit): Float = unit.toPx(from)
        override fun toSp(count: Float): Float = count / dm.scaledDensity
        override fun toDp(count: Int): Float = count / dm.density
        override fun toPx(count: Float): Float = count
    },

    DP {
        override fun convert(from: Float, unit: DisplayUnit): Float = unit.toDp(from.toInt())
        override fun toSp(count: Float): Float = count * (dm.scaledDensity / dm.density)
        override fun toPx(count: Float): Float = count * dm.density
        override fun toDp(count: Int): Float = count.toFloat()
    },

    SP {
        override fun convert(from: Float, unit: DisplayUnit): Float = unit.toSp(from)
        override fun toDp(count: Int): Float = count * (dm.density / dm.scaledDensity)
        override fun toPx(count: Float): Float = count * dm.scaledDensity
        override fun toSp(count: Float): Float = count
    }

    ;

    abstract fun convert(from: Float, unit: DisplayUnit): Float
    abstract fun toPx(count: Float): Float
    abstract fun toDp(count: Int): Float
    abstract fun toSp(count: Float): Float

    companion object {
        private val dm: DisplayMetrics by lazy { Resources.getSystem().displayMetrics }
    }
}