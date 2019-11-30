package com.sgufakto.mvvmsample.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.sgufakto.mvvmsample.data.repositories.UserRepository
import com.sgufakto.mvvmsample.utils.ApiException
import com.sgufakto.mvvmsample.utils.Coroutines

class AuthViewModel: ViewModel() {

    var email:String?=null
    var password: String?=null

    var listener: AuthListener?=null

    fun onLoginButtonClick(view: View){
        listener?.OnStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            //
            listener?.OnError("Invalid Username or Email")
            return
        }

        // success
        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(email!!, password!!)

                authResponse.user?.let{
                    listener?.OnSuccess(it)
                    return@main
                }

                listener?.OnError(authResponse.message!!)

            } catch (e: ApiException) {
                listener?.OnError(e.message!!)
            }


        }


    }

}