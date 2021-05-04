package com.example.juancastrejonautomotriz

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.databinding.ActivityCreditoBinding

class CreditoActivity : AppCompatActivity() {
    lateinit var binding:ActivityCreditoBinding
    private val id_vehicle:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    private val id_user:Int by lazy {
        intent.getIntExtra(Constants.ID_U,-1)
    }
    lateinit var  resumen: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resumen= intent.getStringExtra(Constants.RES).toString()
        binding.textViewDetalle.text=resumen
        binding.floatingActionButtonINE.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).apply { data= Uri.parse("https://images.google.com/") }
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
            else{
                Toast.makeText(this@CreditoActivity,"no hay navegador disponible", Toast.LENGTH_SHORT).show()
            }
        }
        binding.floatingActionButtonCompDom.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).apply { data= Uri.parse("https://images.google.com/") }
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
            else{
                Toast.makeText(this@CreditoActivity,"no hay navegador disponible", Toast.LENGTH_SHORT).show()
            }
        }
        binding.floatingActionButtonCompIng.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).apply { data= Uri.parse("https://images.google.com/") }
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
            else{
                Toast.makeText(this@CreditoActivity,"no hay navegador disponible", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonSendCredit.setOnClickListener {
            Toast.makeText(this,"Solicitud enviada, nosotros nos pondremos en contacto con usted",Toast.LENGTH_LONG).show()
        }
    }
}