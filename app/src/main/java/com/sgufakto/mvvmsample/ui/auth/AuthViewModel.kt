package com.sgufakto.mvvmsample.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.sgufakto.mvvmsample.data.repositories.UserRepository
import com.sgufakto.mvvmsample.utils.ApiException
import com.sgufakto.mvvmsample.utils.Coroutines
import com.sgufakto.mvvmsample.utils.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {

    var email:String?=null
    var password: String?=null

    var listener: AuthListener?=null

    fun getLoggedInUser() = repository.getUser()

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
                val authResponse = repository.userLogin(email!!, password!!)

                authResponse.user?.let{
                    listener?.OnSuccess(it)
                    repository.saveUser(it) // save to local database
                    return@main
                }

                listener?.OnError(authResponse.message!!)

            } catch (e: ApiException) {
                listener?.OnError(e.message!!)
            } catch (e: NoInternetException) {
                listener?.OnError(e.message!!)
            }


        }


    }

}