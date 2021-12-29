package com.jasontsh.interviewkickstart.loadingstartupexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val manager = supportFragmentManager
        manager.commit {
            add(R.id.frame, ItemFragment())
        }
        val serviceIntent = Intent(this, LoadingService::class.java)
        startService(serviceIntent)
    }
}