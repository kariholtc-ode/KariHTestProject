package com.example.userlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText: TextView = itemView.findViewById(R.id.nameText)
        val emailText: TextView = itemView.findViewById(R.id.emailText)
        val phoneText: TextView = itemView.findViewById(R.id.phoneText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.nameText.text = user.name
        holder.emailText.text = user.email
        holder.phoneText.text = user.phone
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "You selected.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = users.size
}
