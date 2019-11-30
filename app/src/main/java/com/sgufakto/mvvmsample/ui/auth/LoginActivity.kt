package com.sgufakto.mvvmsample.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sgufakto.mvvmsample.R
import com.sgufakto.mvvmsample.data.db.AppDatabase
import com.sgufakto.mvvmsample.data.db.entities.User
import com.sgufakto.mvvmsample.data.network.MyApi
import com.sgufakto.mvvmsample.data.network.NetworkConnectionInterceptor
import com.sgufakto.mvvmsample.data.repositories.UserRepository
import com.sgufakto.mvvmsample.databinding.ActivityLoginBinding
import com.sgufakto.mvvmsample.ui.home.HomeActivity
import com.sgufakto.mvvmsample.utils.hide
import com.sgufakto.mvvmsample.utils.show
import com.sgufakto.mvvmsample.utils.snackbar
import com.sgufakto.mvvmsample.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this, factory).get(AuthViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.listener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if( user != null ) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })


    }

    override fun OnStarted() {
        progress_bar.show()
    }

    override fun OnSuccess(user: User) {
        progress_bar.hide()
        root_layout.snackbar("${user.email} success login")
    }

    override fun OnError(msg: String) {
        progress_bar.hide()
        root_layout.snackbar(msg)
    }
}
