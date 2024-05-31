package com.deniz.vlmediadating.swipable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.deniz.vlmediadating.utils.Constants

class StackedItemAnimator : DefaultItemAnimator() {

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            holder.itemView.alpha = 1 - value
        }
        animator.duration = Constants.ANIMATION_DURATION
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchRemoveFinished(holder)
                holder.itemView.alpha = 1f
                super.onAnimationEnd(animation)
            }
        })
        animator.start()
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.scaleX = 0f
        holder.itemView.scaleY = 0f
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            holder.itemView.scaleX = animation.animatedValue as Float
            holder.itemView.scaleY = animation.animatedValue as Float
        }
        animator.duration = Constants.ANIMATION_DURATION
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
                holder.itemView.scaleX = 1f
                holder.itemView.scaleY = 1f
                super.onAnimationEnd(animation)
            }
        })
        animator.start()
        return true
    }
}