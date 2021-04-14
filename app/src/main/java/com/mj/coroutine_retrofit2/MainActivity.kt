package com.mj.coroutine_retrofit2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mj.coroutine_retrofit2.api.addHello
import com.mj.coroutine_retrofit2.databinding.ActivityMainBinding
import com.mj.coroutine_retrofit2.viewModel.MainViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.userData.observe(this, Observer { data ->
            Toast.makeText(
                this,
                "${data.name} 와 ${data.age} 그리고 Extenstion ${data.name.addHello()}",
                Toast.LENGTH_SHORT
            ).show()
        })

    }
}