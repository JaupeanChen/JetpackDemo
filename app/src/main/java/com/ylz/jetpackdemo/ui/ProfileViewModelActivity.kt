package com.ylz.jetpackdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ylz.jetpackdemo.R
import com.ylz.jetpackdemo.data.ProfileViewModel
import com.ylz.jetpackdemo.databinding.ActivityProfileViewModelBinding

class ProfileViewModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val binding: ActivityProfileViewModelBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_profile_view_model)
        binding.viewModel = viewModel
    }
}