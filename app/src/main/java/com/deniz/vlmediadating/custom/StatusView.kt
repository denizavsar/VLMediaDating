package com.deniz.vlmediadating.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.deniz.vlmediadating.R
import com.deniz.vlmediadating.databinding.StatusViewBinding

class StatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    enum class Status {
        DEAD,
        ALIVE,
        UNKNOWN
    }

    private var _binding: StatusViewBinding? = null
    private val binding get() = _binding!!

    private var status: Status = Status.UNKNOWN

    init {
        _binding = StatusViewBinding.inflate(LayoutInflater.from(context), this, true)
        render()
    }

    fun setStatus(status: Status) {
        this.status = status
        render()
    }

    private fun render() {
        val backgroundDrawable: Drawable
        val iconDrawable: Drawable
        val currentStatus: String

        when (status) {
            Status.DEAD -> {
                backgroundDrawable =
                    ContextCompat.getDrawable(context, R.drawable.status_view_dead_background)!!
                iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_dead)!!
                currentStatus = context.getString(R.string.status_dead)
            }

            Status.ALIVE -> {
                backgroundDrawable =
                    ContextCompat.getDrawable(context, R.drawable.status_view_alive_background)!!
                iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_alive)!!
                currentStatus = context.getString(R.string.status_alive)
            }

            Status.UNKNOWN -> {
                backgroundDrawable =
                    ContextCompat.getDrawable(context, R.drawable.status_view_unknown_background)!!
                iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_status_unknown)!!
                currentStatus = context.getString(R.string.status_unknown)
            }
        }

        with(binding) {
            icon.setImageDrawable(iconDrawable)
            statusText.text = currentStatus
            background = backgroundDrawable
        }
    }
}
