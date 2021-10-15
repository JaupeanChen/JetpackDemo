package com.ylz.jetpackdemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModelProvider
import com.ylz.jetpackdemo.R
import com.ylz.jetpackdemo.base.XEventBus
import com.ylz.jetpackdemo.data.DataViewModel
import com.ylz.jetpackdemo.data.UserProfile
import com.ylz.jetpackdemo.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private val userProfile = UserProfile("Jarvan", "Jay", ObservableInt(0))
//    val viewModel by viewModel{ DataViewModel() }
    val viewModel = ViewModelProvider.NewInstanceFactory().create(DataViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //布局绑定
        val binding: ActivityUserProfileBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_user_profile)
        binding.user = userProfile

        viewModel.result.observe(this){
            binding.lastname.text = it
        }

    }

    fun onLike(view: View) {
        userProfile.likes.set(userProfile.likes.get() + 1)
        viewModel.getData()
        XEventBus.post("Event_1", "事件1")
    }
}