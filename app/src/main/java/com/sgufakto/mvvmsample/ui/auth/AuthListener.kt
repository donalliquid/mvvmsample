package com.sgufakto.mvvmsample.ui.auth

import androidx.lifecycle.LiveData
import com.sgufakto.mvvmsample.data.db.entities.User

interface AuthListener {
    fun OnStarted()
    fun OnSuccess(user: User)
    fun OnError(msg: String)
}