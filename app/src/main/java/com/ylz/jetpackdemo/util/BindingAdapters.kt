package com.ylz.jetpackdemo.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ylz.jetpackdemo.R
import com.ylz.jetpackdemo.data.Popular

/**
 * Author: ChenGuiPing
 * Date: 2021/9/30
 * Description:
 */
object BindingAdapters {

    @BindingAdapter("popularityIcon")
    @JvmStatic
    fun popularityIcon(view: ImageView, popular: Popular) {
        val color = getAssociatedColor(popular, view.context)
        ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(color))
        view.setImageDrawable(getDrawablePopularity(popular, view.context))
    }

    @BindingAdapter("progressTint")
    @JvmStatic
    fun tintPopularity(view: ProgressBar, popular: Popular) {
        val color = getAssociatedColor(popular, view.context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = ColorStateList.valueOf(color)
        }
    }

    @BindingAdapter("progressScaled")
    @JvmStatic
    fun setProgress(progressBar: ProgressBar, likes: Int) {
        progressBar.progress = (likes * 100 / 5).coerceAtMost(100)
    }

    @BindingAdapter("hideIfZero")
    @JvmStatic
    fun hideIfZero(view: View, number: Int) {
        view.visibility = if (number == 0) View.GONE else View.VISIBLE
    }

    private fun getAssociatedColor(popular: Popular, context: Context): Int {
        return when (popular) {
            Popular.NORMAL ->
                context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorForeground))
                    .getColor(0, 0x000000)
            Popular.POPULAR ->
                ContextCompat.getColor(context, R.color.popular)
            Popular.STAR -> ContextCompat.getColor(context, R.color.star)
        }
    }

    private fun getDrawablePopularity(popular: Popular, context: Context): Drawable? {
        return when (popular) {
            Popular.NORMAL -> ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp)
            Popular.POPULAR -> ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
            Popular.STAR -> ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp)
        }
    }



}