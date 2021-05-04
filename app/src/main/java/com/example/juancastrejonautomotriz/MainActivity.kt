package com.example.juancastrejonautomotriz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.juancastrejonautomotriz.Tools.NetworkConnectionState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val x = NetworkConnectionState(this)
        if(x.checkNetwork()){
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this@MainActivity,CatalogActivity::class.java)
                startActivity(intent)
                finish()
            },3000L)
        }
        else{
            Toast.makeText(this,"Sin concección a la red, intentelo de nuevo más tarde",Toast.LENGTH_LONG).show()
            finish()
        }
    }
}