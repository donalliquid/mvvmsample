package com.sgufakto.mvvmsample.data.repositories

import com.sgufakto.mvvmsample.data.network.MyApi
import com.sgufakto.mvvmsample.data.network.SafeApiRequest
import com.sgufakto.mvvmsample.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository: SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest {
            MyApi().userLogin(email, password)
        }
    }


}