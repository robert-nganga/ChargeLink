package com.nganga.robert.chargelink.models

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val imageUrl: String,
    val gender: String,
    val dob: String,
    val cars: List<Car>

){
    constructor():this(
        "",
        "",
        "",
        "",
        "",
        "",
        listOf(Car())
    )

    fun toMap() = hashMapOf(
        "name" to name,
        "email" to email,
        "phone" to phone,
        "imageUrl" to imageUrl,
        "gender" to gender,
        "dob" to dob,
        "cars" to cars
    )
}
