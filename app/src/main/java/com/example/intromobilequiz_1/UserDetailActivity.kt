package com.example.intromobilequiz_1

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity

class UserDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val user: User? = intent.getParcelableExtra("extra_user")

        val tvHeader: TextView = findViewById(R.id.tvHeader)
        val tvName: TextView = findViewById(R.id.tvName)
        val tvUsername: TextView = findViewById(R.id.tvUsername)
        val tvEmail: TextView = findViewById(R.id.tvEmail)
        val tvPhone: TextView = findViewById(R.id.tvPhone)
        val tvWebsite: TextView = findViewById(R.id.tvWebsite)
        val tvAddress: TextView = findViewById(R.id.tvAddress)
        val tvGeo: TextView = findViewById(R.id.tvGeo)
        val tvCompany: TextView = findViewById(R.id.tvCompany)

        user?.let {
            tvHeader.text = getString(R.string.user_header, it.id)
            tvName.text = it.name
            tvUsername.text = it.username
            tvEmail.text = it.email
            tvPhone.text = it.phone
            tvWebsite.text = it.website
            val address = it.address
            tvAddress.text = listOf(address.street, address.suite, address.city, address.zipcode)
                .filter { part -> part.isNotBlank() }
                .joinToString(", ")
            tvGeo.text = getString(R.string.geo_format, address.geo.lat, address.geo.lng)
            val company = it.company
            tvCompany.text = listOf(
                company.name,
                company.catchPhrase,
                company.bs
            ).filter { part -> part.isNotBlank() }.joinToString("\n")
        }
    }
}
