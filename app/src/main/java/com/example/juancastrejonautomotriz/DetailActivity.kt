package com.example.juancastrejonautomotriz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.juancastrejonautomotriz.Adapters.ImgPagerAdapter
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.databinding.ActivityCatalogBinding
import com.example.juancastrejonautomotriz.databinding.ActivityDetailBinding
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private lateinit var queue: RequestQueue
    private val url = "http://192.168.100.17:8090/api/Vehiculo"
    var viewPager: ViewPager?=null
    var images=arrayListOf<String>()
    var myAdapter:ImgPagerAdapter?=null
    private lateinit var binding : ActivityDetailBinding
    private val id_vehicle:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    private val id_user:Int by lazy {
        intent.getIntExtra(Constants.ID_U,-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(id_vehicle!=-1){
            loadImages()
            binding.buttonCorrida.setOnClickListener {
                val intent = Intent(this, CorridaActivity::class.java).apply {
                    putExtra(Constants.ID,id_vehicle)
                    putExtra(Constants.ID_U,id_user)
                }
                startActivity(intent)
            }
        }
    }
    fun loadImages(){
        queue= Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,url+"/$id_vehicle",
            Response.Listener<String>{ response ->
                Log.d("UdelP","response is $response")
                val jsonObject = JSONObject(response)
                val vehicleArray = jsonObject.getJSONArray("values")
                for (i in 0 until vehicleArray.length()){
                    val vehicleObject = vehicleArray.getJSONObject(i)
                    val imageArray = vehicleObject.getJSONArray("imgns")
                    cargaDatos(vehicleObject)
                    for (j in 0 until imageArray.length()){
                        images.add(imageArray.getJSONObject(j).getString("img"))
                        Log.d("UdelP","val is ${imageArray.getJSONObject(j).getString("img")}")
                    }
                }
                viewPager = binding.viewPager
                myAdapter = ImgPagerAdapter(this,images)
                viewPager!!.adapter=myAdapter
            },
            Response.ErrorListener { error ->
                Log.d("UdelP","error is $error")
            }
        )
        queue.add(stringRequest)
    }
    fun cargaDatos(vehicle:JSONObject){
        binding.textViewVhicle.text=vehicle.getString("vehiculo")
        binding.textViewModelo.text="Modelo: ${vehicle.getString("modelo")}"
        binding.textViewTipo.text="Tipo: ${if(vehicle.getInt("tipo")==1) "sedán" else "hatchback"}"
        binding.textViewColor.text="color"
        //binding.textViewColor.setTextColor(vehicle.getString("color").toInt())
        binding.textViewAccesorios.text="Accesorios: ${vehicle.getString("accesorios")}"
        binding.textViewPrecio.text="$${vehicle.getString("precio")}"
    }

}