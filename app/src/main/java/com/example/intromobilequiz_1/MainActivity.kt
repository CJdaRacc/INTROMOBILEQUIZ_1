package com.example.intromobilequiz_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnFetch: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        btnFetch = findViewById(R.id.btnFetch)
        progressBar = findViewById(R.id.progressBar)

        adapter = UserAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnFetch.setOnClickListener {
            fetchUsers()
        }
    }

    private fun fetchUsers() {
        val url = ApiConfig.USERS_URL
        showLoading(true)
        val requestQueue = Volley.newRequestQueue(this)

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val users = parseUsers(response)
                    adapter.setItems(users)
                } catch (e: JSONException) {
                    Toast.makeText(this, getString(R.string.failed_parse_data), Toast.LENGTH_SHORT).show()
                } finally {
                    showLoading(false)
                }
            },
            { error ->
                showLoading(false)
                Toast.makeText(this, getString(R.string.failed_fetch_data), Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }

    private fun parseUsers(jsonArray: JSONArray): List<User> {
        val list = mutableListOf<User>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)

            val id = obj.optInt("id")
            val name = obj.optString("name")
            val username = obj.optString("username")
            val email = obj.optString("email")
            val phone = obj.optString("phone")
            val website = obj.optString("website")

            val addressObj = obj.optJSONObject("address") ?: JSONObject()
            val street = addressObj.optString("street")
            val suite = addressObj.optString("suite")
            val city = addressObj.optString("city")
            val zipcode = addressObj.optString("zipcode")
            val geoObj = addressObj.optJSONObject("geo") ?: JSONObject()
            val lat = geoObj.optString("lat")
            val lng = geoObj.optString("lng")
            val geo = Geo(lat = lat, lng = lng)
            val address = Address(
                street = street,
                suite = suite,
                city = city,
                zipcode = zipcode,
                geo = geo
            )

            val companyObj = obj.optJSONObject("company") ?: JSONObject()
            val company = Company(
                name = companyObj.optString("name"),
                catchPhrase = companyObj.optString("catchPhrase"),
                bs = companyObj.optString("bs")
            )

            val user = User(
                id = id,
                name = name,
                username = username,
                email = email,
                phone = phone,
                website = website,
                address = address,
                company = company
            )
            list.add(user)
        }
        return list
    }

    private fun showLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        btnFetch.isEnabled = !loading
    }
}