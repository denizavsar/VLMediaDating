package com.deniz.vlmediadating.utils

import android.os.SystemClock
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

fun View.fadeOut(withEndAction: () -> Unit) {
    if (alpha == 0f) return
    animate()
        .alpha(0f)
        .setDuration(Constants.ANIMATION_DURATION)
        .withEndAction(withEndAction)
        .start()
}

fun ViewGroup.performSwipeToLeft(target: View, distance: Float) {
    this.performSwipe(target, distanceX = -distance, distanceY = 0f)
}

fun ViewGroup.performSwipeToRight(target: View, distance: Float) {
    this.performSwipe(target, distanceX = +distance, distanceY = 0f)
}

fun ViewGroup.performSwipe(target: View, distanceX: Float, distanceY: Float) {
    val parentCoords = intArrayOf(0, 0)
    this.getLocationInWindow(parentCoords)

    val childCoords = intArrayOf(0, 0)
    target.getLocationInWindow(childCoords)

    val initGlobalX = childCoords[0].toFloat() + 1f
    val initGlobalY = childCoords[1].toFloat() + 1f

    val initLocalX = (childCoords[0] - parentCoords[0]).toFloat() + 1f
    val initLocalY = (childCoords[1] - parentCoords[1]).toFloat() + 1f

    val downTime = SystemClock.uptimeMillis()
    var eventTime = SystemClock.uptimeMillis()

    this.dispatchTouchEvent(
        MotionEvent.obtain(
            downTime,
            eventTime,
            MotionEvent.ACTION_DOWN,
            initGlobalX,
            initGlobalY,
            0
        ).apply {
            setLocation(initLocalX, initLocalY)
            source = InputDevice.SOURCE_TOUCHSCREEN
        }
    )

    val steps = 20
    var i = 0
    while (i in 0..steps) {
        val globalX = initGlobalX + i * distanceX / steps
        val globalY = initGlobalY + i * distanceY / steps
        val localX = initLocalX + i * distanceX / steps
        val localY = initLocalY + i * distanceY / steps
        if (globalX <= 10f || globalY <= 10f) {
            break
        }
        this.dispatchTouchEvent(
            MotionEvent.obtain(
                downTime,
                ++eventTime,
                MotionEvent.ACTION_MOVE,
                globalX,
                globalY,
                0
            ).apply {
                setLocation(localX, localY)
                source = InputDevice.SOURCE_TOUCHSCREEN
            }
        )
        i++
    }

    this.dispatchTouchEvent(
        MotionEvent.obtain(
            downTime,
            ++eventTime,
            MotionEvent.ACTION_UP,
            initGlobalX + i * distanceX,
            initGlobalY + i * distanceY,
            0
        ).apply {
            setLocation(initLocalX + i * distanceX, initLocalY + i * distanceY)
            source = InputDevice.SOURCE_TOUCHSCREEN
        }
    )
}