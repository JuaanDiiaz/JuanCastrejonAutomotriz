package com.example.juancastrejonautomotriz

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.databinding.ActivityCatalogBinding
import com.example.juancastrejonautomotriz.databinding.ActivityCorridaBinding
import com.squareup.picasso.Picasso
import org.json.JSONObject
import kotlin.math.round

class CorridaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCorridaBinding
    private lateinit var queue: RequestQueue
    private val url_Corrida = "http://192.168.100.17:8090/api/Corrida"
    private val url = "http://192.168.100.17:8090/api/Vehiculo"
    var price:Double =0.0
    var enganche=0.0
    var mensualidad=0.0
    private val id_vehicle:Int by lazy {
        intent.getIntExtra(Constants.ID,-1)
    }
    private val id_user:Int by lazy {
        intent.getIntExtra(Constants.ID_U,-1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorridaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(id_vehicle!=-1){
            loadVehicle()
            binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    enganche=(price*(i.toDouble()/100.toDouble()))
                    binding.textViewPercentage.text="$i %"
                    binding.textViewInitialPayment.text="$ $enganche"
                    calculaCorrida()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {

                }
            })
            binding.buttonSend.setOnClickListener {
                if(id_user!=-1){
                    sendCorrida()
                }else{
                    Toast.makeText(this@CorridaActivity,"Debe ingresar su cuenta antes de continuar",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    fun sendCorrida(){
        if(price!=0.0 && enganche!=0.0 && mensualidad!=0.0){
            val jsonBody = JSONObject()
            jsonBody.put("idUsuario",id_user)
            jsonBody.put("idVehiculo",id_vehicle)
            jsonBody.put("enganche",enganche)
            val plazo = when(binding.spinnerMonthlyPayments.selectedItemPosition){
                0->12
                1->24
                2->36
                else->0
            }
            jsonBody.put("plazo",plazo)
            val tasa = when(binding.rdgTasa.checkedRadioButtonId){
                binding.rdb10.id->10
                binding.rdb15.id->15
                binding.rdb20.id->20
                else->0
            }
            jsonBody.put("tasa",tasa)

            val jsonObjectRequest = JsonObjectRequest(Request.Method.POST,url_Corrida,jsonBody,
                Response.Listener{ response ->
                    val intent = Intent(this, CreditoActivity::class.java).apply {
                        putExtra(Constants.ID,id_vehicle)
                        putExtra(Constants.ID_U,id_user)
                        putExtra(Constants.RES,"Precio:$$price\nEnganche:$$enganche\nMensualidades:$${round(mensualidad)}")
                    }
                    startActivity(intent)
                    Toast.makeText(this@CorridaActivity,"Datos enviados exitosamente", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this@CorridaActivity,"Error", Toast.LENGTH_SHORT).show()
                    Log.d("UdelP","error is $error")
                }
            )
            queue.add(jsonObjectRequest)
        }else{
            Toast.makeText(this@CorridaActivity,"Ingrese datos completos", Toast.LENGTH_SHORT).show()
        }
    }
    fun loadVehicle(){
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
                        Picasso.get().load(imageArray.getJSONObject(j).getString("img")).fit()
                            .into(binding.imageView2)
                        Log.d("UdelP","val is ${imageArray.getJSONObject(j).getString("img")}")
                    }
                }
            },
            Response.ErrorListener { error ->
                Log.d("UdelP","error is $error")
            }
        )
        queue.add(stringRequest)
    }
    fun cargaDatos(vehicle:JSONObject){
        binding.textViewVehicle.text=vehicle.getString("vehiculo")
        binding.textViewModel.text="Modelo: ${vehicle.getString("modelo")}"
        binding.textViewType.text="Tipo: ${if(vehicle.getInt("tipo")==1) "sedán" else "hatchback"}"
        binding.textViewColor.text="color"
        price=vehicle.getDouble("precio")

    }
    fun calculaCorrida(){
        val financial=price-enganche
        val meses = binding.spinnerMonthlyPayments.selectedItemPosition
        val lista = arrayListOf<String>()
        when (meses){
            0->{
                mensualidad=financial/12.toDouble()
                for(i in 1..12){
                    lista.add("Mensualidad: $i con valor de: $${round(mensualidad)}" )
                }
            }
            1->{
                mensualidad=financial/24.toDouble()
                for(i in 1..24){
                    lista.add("Mensualidad: $i con valor de: $${round(mensualidad)}" )
                }
            }
            2->{
                mensualidad=financial/36.toDouble()
                for(i in 1..36){
                    lista.add("Mensualidad: $i con valor de: $${round(mensualidad)}" )
                }
            }
            else->{

            }
        }
        getList(lista.toTypedArray())
    }
    private fun getList(list:kotlin.Array<String>){
        val adapter = ArrayAdapter<String>(this@CorridaActivity, R.layout.simple_list_item_1,list)
        binding.listViewCorrida.adapter = adapter
    }
}