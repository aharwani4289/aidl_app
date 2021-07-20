package com.ajayh.aidl.aidlapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.ajayh.aidl.IRemoteService
import com.ajayh.aidl.aidlapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var service: IRemoteService? = null
    private val UPDATE_INTERVAL = 8L
    private val updateWidgetHandler = Handler()

    private var updateWidgetRunnable: Runnable = Runnable {
        run {
            binding.sensorData.text = service?.phoneOrientation.toString()
            updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
        }
    }

    override fun onResume() {
        super.onResume()
        updateWidgetHandler.postDelayed(updateWidgetRunnable, UPDATE_INTERVAL)
    }

    override fun onPause() {
        super.onPause()
        updateWidgetHandler.removeCallbacks(updateWidgetRunnable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        connectService()
    }

    private fun connectService() {
        val intent = Intent("com.ajayh.aidl.sdk.IRemoteSensorService")
        intent.setPackage("com.ajayh.aidl.sdk")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, boundService: IBinder) {
            service = IRemoteService.Stub.asInterface(boundService)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}