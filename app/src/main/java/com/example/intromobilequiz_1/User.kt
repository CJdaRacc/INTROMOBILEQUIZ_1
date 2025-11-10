package com.example.intromobilequiz_1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geo(
    val lat: String = "",
    val lng: String = ""
) : Parcelable

@Parcelize
data class Address(
    val street: String = "",
    val suite: String = "",
    val city: String = "",
    val zipcode: String = "",
    val geo: Geo = Geo()
) : Parcelable

@Parcelize
data class Company(
    val name: String = "",
    val catchPhrase: String = "",
    val bs: String = ""
) : Parcelable

@Parcelize
data class User(
    val id: Int = 0,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val website: String = "",
    val address: Address = Address(),
    val company: Company = Company()
) : Parcelable