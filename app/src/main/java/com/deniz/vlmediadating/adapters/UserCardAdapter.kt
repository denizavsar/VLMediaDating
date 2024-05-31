package com.deniz.vlmediadating.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deniz.vlmediadating.R
import com.deniz.vlmediadating.api.data.User
import com.deniz.vlmediadating.custom.StatusView
import com.deniz.vlmediadating.databinding.CardLayoutBinding
import com.squareup.picasso.Picasso

class UserCardAdapter : RecyclerView.Adapter<UserCardAdapter.UserCardViewHolder>() {

    private var users: MutableList<User> = mutableListOf()
    private var swipedUsers: MutableList<User> = mutableListOf()

    fun removeTopUser() {
        if (users.isEmpty()) return

        val user = users.removeAt(0)
        notifyItemRemoved(0)
        swipedUsers.add(0, user)
    }

    fun addUsersToList(newUsers: List<User>) {
        val currentPosition = users.size
        users.addAll(newUsers)
        notifyItemRangeInserted(currentPosition, newUsers.size)
    }

    fun revert() {
        if (swipedUsers.isEmpty()) return

        val user = swipedUsers.removeAt(0)
        users.add(0, user)
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserCardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserCardViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class UserCardViewHolder(private val binding: CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                nameTextView.text = user.name
                locationTextView.text = user.location.name

                StatusView.Status.convertToStatus(user.status).let { status ->
                    statusView.setStatus(status)
                }

                Picasso
                    .get()
                    .load(user.image)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(userImageView)
            }
        }
    }
}