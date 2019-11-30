package com.sgufakto.mvvmsample.data.network.responses

import com.sgufakto.mvvmsample.data.db.entities.User

data class AuthResponse (
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)