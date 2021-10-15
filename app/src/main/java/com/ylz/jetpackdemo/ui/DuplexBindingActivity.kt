package com.ylz.jetpackdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ylz.jetpackdemo.R
import com.ylz.jetpackdemo.data.DuplexViewModel
import com.ylz.jetpackdemo.data.ProfileViewModel
import com.ylz.jetpackdemo.databinding.ActivityDuplexBindingBinding
import com.ylz.jetpackdemo.databinding.ActivityProfileViewModelBinding

class DuplexBindingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(DuplexViewModel::class.java)
        val binding: ActivityDuplexBindingBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_duplex_binding)
        binding.viewModel = viewModel
    }
}