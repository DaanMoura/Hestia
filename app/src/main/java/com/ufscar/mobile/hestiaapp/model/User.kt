package com.ufscar.mobile.hestiaapp.model

data class User(val nome: String,
                val email: String,
                val bio: String,
                val picturePath: String?,
                val dono: Boolean) {
    constructor(): this("","", "", null, true)
}