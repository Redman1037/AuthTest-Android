package com.appstyx.authtest.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.appstyx.authtest.R
import com.appstyx.authtest.ui.home.HomeFragment
import com.appstyx.authtest.ui.main.MainViewModel.Destination.Home
import com.appstyx.authtest.ui.main.MainViewModel.Destination.Signup
import com.appstyx.authtest.ui.signup.SignupFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initViewModelObservers()
    }

 /*   private fun initViewModelObservers() {
        val lifecycleOwner: LifecycleOwner = this
        with(viewModel) {
            changeDestinationEvent.observe(lifecycleOwner) { destination ->
                val fragment = when (destination) {
                    Signup -> SignupFragment.newInstance()
                    Home -> HomeFragment.newInstance()
                    else -> throw IllegalArgumentException("Destination not supported")
                }
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            }
        }
    }*/

}