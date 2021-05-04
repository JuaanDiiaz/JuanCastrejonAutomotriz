package com.example.juancastrejonautomotriz

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.juancastrejonautomotriz.databinding.ActivityCatalogBinding
import com.example.juancastrejonautomotriz.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButtonCall.setOnClickListener{
            var intent = Intent(Intent.ACTION_DIAL).apply { data= Uri.parse("tel:5532499504") }
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
            else{
                Toast.makeText(this@ContactActivity,"no hay aplicacion para llmadas disponible", Toast.LENGTH_SHORT).show()
            }
        }
        binding.floatingActionButtonEmail.setOnClickListener {

            val emails = arrayOf("minijorda22@gmail.com")
            var intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL,emails)
                putExtra(Intent.EXTRA_SUBJECT,"Cotizacion")
                putExtra(Intent.EXTRA_TEXT,"Escribe aquí tus dudas")
            }
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
            else{
                Toast.makeText(this@ContactActivity,"no hay aplicacion para llmadas disponible", Toast.LENGTH_SHORT).show()
            }
        }
        binding.floatingActionButtonMapa.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW).apply { data= Uri.parse("geo:19.28958619366017,-99.13938537622406?z=21 &q=19.28958619366017,-99.13938537622406") }
            if(intent.resolveActivity(packageManager)!=null){
                startActivity(intent)
            }
            else{
                Toast.makeText(this@ContactActivity,"no hay mapa disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }
}