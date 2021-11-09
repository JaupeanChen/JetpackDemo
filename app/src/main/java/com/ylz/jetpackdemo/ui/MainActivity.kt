package com.ylz.jetpackdemo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ylz.jetpackdemo.R
import com.ylz.jetpackdemo.base.XEventBus
import com.ylz.jetpackdemo.databinding.MainActivityBinding
import com.ylz.jetpackdemo.ui.ComposeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        val binding: MainActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.main_activity)
//        binding.lifecycleOwner = this

        binding.btnGo.setOnClickListener {
            startActivity(Intent(this, UserProfileActivity::class.java))
        }
        binding.btnGoTwo.setOnClickListener {
            startActivity(Intent(this, ProfileViewModelActivity::class.java))
        }
        binding.btnGoThree.setOnClickListener {
            startActivity(Intent(this, DuplexBindingActivity::class.java))
        }
        XEventBus.observe<String>(this, "event_1", false) {
            binding.tvText.text = it as String
        }

        binding.compose.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}