package com.example.shemajamebelin10.presentation.screen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shemajamebelin10.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}