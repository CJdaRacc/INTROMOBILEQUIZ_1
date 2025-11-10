package com.example.intromobilequiz_1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val users: MutableList<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)

        fun bind(user: User) {
            tvName.text = user.name
            tvEmail.text = user.email
            tvPhone.text = user.phone

            itemView.setOnClickListener {
                // Optional: keep the toast
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.selected_user, user.name),
                    Toast.LENGTH_SHORT
                ).show()

                val ctx = itemView.context
                val intent = Intent(ctx, UserDetailActivity::class.java).apply {
                    putExtra("extra_user", user)
                }
                ctx.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun setItems(newItems: List<User>) {
        users.clear()
        users.addAll(newItems)
        notifyDataSetChanged()
    }
}