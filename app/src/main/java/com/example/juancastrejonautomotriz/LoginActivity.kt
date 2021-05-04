package com.example.juancastrejonautomotriz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.Tools.Hasher
import com.example.juancastrejonautomotriz.databinding.ActivityContactBinding
import com.example.juancastrejonautomotriz.databinding.ActivityCorridaBinding
import com.example.juancastrejonautomotriz.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private val url = "http://192.168.100.17:8090/api/Usuario"
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        queue= Volley.newRequestQueue(this)

        binding.buttonLogin.setOnClickListener {
            if(binding.editTextTextUser.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()){
                login()
            }else{
                Toast.makeText(this@LoginActivity,"Ingrese datos completos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonRegister.setOnClickListener {
            if(binding.editTextTextUser.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()){
                val usr = binding.editTextTextUser.text.toString().trim()
                val pass = Hasher().hash(binding.editTextTextPassword.text.toString().trim())
                val jsonBody = JSONObject()
                jsonBody.put("USUARIO_CORREO",usr)
                jsonBody.put("CONTRASENIA",pass)

                val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,url,jsonBody,
                    Response.Listener{ response ->
                        Toast.makeText(this@LoginActivity,"registro creado exitosamente", Toast.LENGTH_SHORT).show()
                        login()
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this@LoginActivity,"Error", Toast.LENGTH_SHORT).show()
                        Log.d("UdelP","error is $error")
                    }
                )
                queue.add(jsonObjectRequest)
            }else{
                Toast.makeText(this@LoginActivity,"Ingrese datos completos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonDelete.setOnClickListener {
            if(binding.editTextTextUser.text.isNotEmpty() && binding.editTextTextPassword.text.isNotEmpty()){
                val usr = binding.editTextTextUser.text.toString().trim()
                val pass = Hasher().hash(binding.editTextTextPassword.text.toString().trim())
                val stringRequest = StringRequest(Request.Method.DELETE,"$url?usr=$usr&pass=$pass",
                    Response.Listener<String>{ response ->
                        val jsonObject = JSONObject(response)
                        val m_id_usr=jsonObject["message"]
                        Log.d("UdelP","response is $response")
                        if(m_id_usr.toString().toInt()>0){
                            Toast.makeText(this@LoginActivity,"Registro eliminado", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@LoginActivity,"No se elimino ningún registro", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(this@LoginActivity,"Error", Toast.LENGTH_SHORT).show()
                        Log.d("UdelP","error is $error")
                    }
                )
                queue.add(stringRequest)
            }else{
                Toast.makeText(this@LoginActivity,"Ingrese datos completos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun goToCatalog(id_user:Int){
        val intent = Intent(this@LoginActivity, CatalogActivity::class.java).apply {
            putExtra(Constants.ID_U,id_user)
        }
        startActivity(intent)
    }
    fun login(){
        val usr = binding.editTextTextUser.text.toString().trim()
        val pass = Hasher().hash(binding.editTextTextPassword.text.toString().trim())
        val stringRequest = StringRequest(
            Request.Method.GET,"$url?usr=$usr&pass=$pass",
            Response.Listener<String>{ response ->
                val jsonObject = JSONObject(response)
                val m_id_usr=jsonObject["message"]
                Toast.makeText(this@LoginActivity,"Bienvenido", Toast.LENGTH_SHORT).show()
                goToCatalog(m_id_usr.toString().toInt())
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@LoginActivity,"Error de autenticación", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(stringRequest)
    }
}