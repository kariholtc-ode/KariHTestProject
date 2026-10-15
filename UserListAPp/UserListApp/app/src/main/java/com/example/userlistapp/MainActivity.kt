package com.example.userlistapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var fetchButton: Button
    private lateinit var progressBar: ProgressBar
    private val users = mutableListOf<User>()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usersRecyclerView = findViewById(R.id.usersRecyclerView)
        fetchButton = findViewById(R.id.fetchButton)
        progressBar = findViewById(R.id.progressBar)

        userAdapter = UserAdapter(users)
        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = userAdapter

        fetchButton.setOnClickListener { fetchUsers() }
    }

    private fun fetchUsers() {
        progressBar.visibility = View.VISIBLE
        fetchButton.isEnabled = false

        val url = "https://jsonplaceholder.typicode.com/users"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                users.clear()
                for (index in 0 until response.length()) {
                    val item = response.optJSONObject(index) ?: continue
                    users.add(
                        User(
                            name = item.optString("name", "Name unavailable"),
                            email = item.optString("email", "Email unavailable"),
                            phone = item.optString("phone", "Phone unavailable")
                        )
                    )
                }
                userAdapter.notifyDataSetChanged()
                if (users.isEmpty()) {
                    Toast.makeText(this, "No users were returned.", Toast.LENGTH_LONG).show()
                }
                progressBar.visibility = View.GONE
                fetchButton.isEnabled = true
            },
            {
                progressBar.visibility = View.GONE
                fetchButton.isEnabled = true
                Toast.makeText(this, "Failed to fetch data.", Toast.LENGTH_LONG).show()
            }
        )
        Volley.newRequestQueue(this).add(request)
    }
}
