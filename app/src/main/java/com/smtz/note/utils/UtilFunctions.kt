package com.smtz.note.utils

import android.animation.ValueAnimator
import android.view.View

fun animateSearchCardViewWidth(searchCardView: View, targetWidth: Int) {
    val animator = ValueAnimator.ofInt(searchCardView.width, targetWidth)
    animator.addUpdateListener { animation ->
        val params = searchCardView.layoutParams
        params.width = animation.animatedValue as Int
        searchCardView.layoutParams = params
    }
    animator.duration = 300 // Set the duration of the animation
    animator.start()
}