package com.example.juancastrejonautomotriz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.juancastrejonautomotriz.Adapters.VehiculoaAdapter
import com.example.juancastrejonautomotriz.Entity.EntityVehiculoBusqueda
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.databinding.ActivityCatalogBinding

import org.json.JSONObject

class CatalogActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCatalogBinding
    private lateinit var queue: RequestQueue
    private val url = "http://192.168.100.17:8090/api/Vehiculo"
    private val id_user:Int by lazy {
        intent.getIntExtra(Constants.ID_U,-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle(R.string.txt_catalog)

        loadRecycler("")

        binding.floatingActionButtonSearch.setOnClickListener {
            loadRecycler(binding.editTextTextBusqueda.text.toString().trim())
        }
        binding.editTextTextBusqueda.addTextChangedListener{
            loadRecycler(binding.editTextTextBusqueda.text.toString().trim())
        }
    }

    override fun onResume() {
        super.onResume()
        loadRecycler("")
    }

    private fun loadRecycler(busqueda: String){
        val answerList= arrayListOf<EntityVehiculoBusqueda>()
        queue= Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,url+"?busqueda=$busqueda",
            Response.Listener<String>{ response ->
                Log.d("UdelP","response is $response")
                val jsonObject = JSONObject(response)
                val vehicleArray = jsonObject.getJSONArray("values")
                Log.d("UdelP","Message is ${jsonObject["message"]}")
                for (i in 0 until vehicleArray.length()){
                    val vehicle = EntityVehiculoBusqueda()
                    vehicle.id=vehicleArray.getJSONObject(i).getInt("id")
                    vehicle.vehiculo=vehicleArray.getJSONObject(i).getString("vehiculo")
                    vehicle.img=vehicleArray.getJSONObject(i).getString("img")
                    Log.d("UdelP","data ${vehicle.vehiculo}")
                    answerList.add(vehicle)
                }
                val adapter = VehiculoaAdapter(answerList,this@CatalogActivity,id_user)
                val linearLayout = LinearLayoutManager(this@CatalogActivity,
                    LinearLayoutManager.VERTICAL,false)
                binding.rvwVehicles.layoutManager=linearLayout
                binding.rvwVehicles.setHasFixedSize(true)
                binding.rvwVehicles.adapter=adapter
            },
            Response.ErrorListener { error ->
                Log.d("UdelP","error is $error")
            }
        )
        queue.add(stringRequest)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_catalog,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itmContact->{
                val intent = Intent(this@CatalogActivity,ContactActivity::class.java)
                startActivity(intent)
            }
            R.id.itmLogin->{
                val intent = Intent(this@CatalogActivity,LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

