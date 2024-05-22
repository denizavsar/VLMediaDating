package com.deniz.vlmediadating.swipable

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class StackedItemAnimator : DefaultItemAnimator() {

    override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            holder.itemView.alpha = 1 - value
            holder.itemView.translationX = holder.itemView.width * value
        }
        animator.duration = removeDuration
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchRemoveFinished(holder)
                holder.itemView.alpha = 1f
                holder.itemView.translationX = 0f
                super.onAnimationEnd(animation)
            }
        })
        animator.start()
        return true
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        holder.itemView.alpha = 0f
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener { animation ->
            holder.itemView.alpha = animation.animatedValue as Float
        }
        animator.duration = addDuration
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
                holder.itemView.alpha = 1f
                super.onAnimationEnd(animation)
            }
        })
        animator.start()
        return true
    }
}