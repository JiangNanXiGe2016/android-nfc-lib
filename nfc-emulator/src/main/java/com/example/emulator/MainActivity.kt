package com.example.emulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.emulator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var main: ActivityMainBinding
    lateinit var log: Log
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)
        log = Log(main.logTv)
        HostCardEmulatorService.act(this)
    }


}