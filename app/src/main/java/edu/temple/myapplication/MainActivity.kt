package edu.temple.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private var binder: TimerService.TimerBinder? = null
    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            object : ServiceConnection{
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    binder = service as TimerService.TimerBinder
                    isBound = true
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    binder = null
                    isBound = false
                }
            },
            Context.BIND_AUTO_CREATE

        )

        findViewById<Button>(R.id.startButton).setOnClickListener {
            val countdownValue = 10
            binder?.start(countdownValue)
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
            binder?.stop()
        }
    }
}