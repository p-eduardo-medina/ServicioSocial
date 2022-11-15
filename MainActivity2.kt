package com.example.controladorsm

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat

class MainActivity2 : AppCompatActivity() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    lateinit var pairedDevice: Set<BluetoothDevice>
    private val REQUEST_ENEABLE_BLUETTOTH = 1
    private var selecDeviceRefresh: ListView? = null



    var botonbm : Button?= null
    fun gotoativity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        botonbm!!.setOnClickListener {
            gotoativity()
        }

    }

}