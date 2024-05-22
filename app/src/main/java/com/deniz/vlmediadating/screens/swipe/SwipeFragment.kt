package com.deniz.vlmediadating.screens.swipe

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.deniz.vlmediadating.R
import com.deniz.vlmediadating.adapters.UserCardAdapter
import com.deniz.vlmediadating.databinding.FragmentSwipeBinding
import com.deniz.vlmediadating.swipable.StackedItemAnimator
import com.deniz.vlmediadating.swipable.StackedLayoutManager
import com.deniz.vlmediadating.utils.Constants
import com.deniz.vlmediadating.utils.performSwipeToLeft
import com.deniz.vlmediadating.utils.performSwipeToRight
import kotlin.math.abs
import kotlin.math.min

class SwipeFragment : Fragment(R.layout.fragment_swipe) {

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SwipeViewModel
    private lateinit var cardAdapter: UserCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SwipeViewModel::class.java]

        cardAdapter = UserCardAdapter()

        with(binding) {
            cardRecyclerView.adapter = cardAdapter
            cardRecyclerView.layoutManager = StackedLayoutManager()
            cardRecyclerView.itemAnimator = StackedItemAnimator()
            ItemTouchHelper(SimpleItemTouchCallback(cardAdapter)).apply {
                attachToRecyclerView(cardRecyclerView)
            }

            likeButton.setOnClickListener {
                cardRecyclerView.performSwipeToRight(cardRecyclerView.getChildAt(0), 50f)
            }

            dislikeButton.setOnClickListener {
                cardRecyclerView.performSwipeToLeft(cardRecyclerView.getChildAt(0), 50f)
            }
        }

        viewModel.users.observe(viewLifecycleOwner) { newUsers ->
            cardAdapter.addUsersToList(newUsers)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class SimpleItemTouchCallback(private val adapter: UserCardAdapter) :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.removeTopUser()
            if (adapter.itemCount < Constants.PREFETCH_ITEM_COUNT) {
                viewModel.fetchCharacters()
            }
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
        ) {
            val itemView = viewHolder.itemView
            val alpha = 1.0f - abs(dX) / recyclerView.width
            itemView.alpha = alpha
            itemView.translationX = dX
            val rotationDegrees =
                (-Constants.MAX_CARD_ROTATION_DEGREE).coerceAtLeast(
                    Constants.MAX_CARD_ROTATION_DEGREE.coerceAtMost(
                        dX * Constants.CARD_ROTATION_FACTOR
                    )
                ) // Limit rotation
            itemView.rotation = rotationDegrees

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

        override fun getSwipeDirs(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            // Only allow swiping for the top card view
            return if (viewHolder.adapterPosition == 0) {
                super.getSwipeDirs(recyclerView, viewHolder)
            } else {
                0 // Disable swiping for other items
            }
        }
    }
}