package com.example.juancastrejonautomotriz.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.juancastrejonautomotriz.DetailActivity
import com.example.juancastrejonautomotriz.Entity.EntityVehiculoBusqueda
import com.example.juancastrejonautomotriz.R
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.databinding.ItemVehicleBinding
import com.squareup.picasso.Picasso

class VehiculoaAdapter(private var vehicleList:ArrayList<EntityVehiculoBusqueda>, val context: Context, var id_u:Int): RecyclerView.Adapter<VehiculoHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiculoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return VehiculoHolder(inflater.inflate(R.layout.item_vehicle,parent,false))
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }
    override fun onBindViewHolder(holder: VehiculoHolder, position: Int) {
        Picasso.get().load(vehicleList[position].img).fit()
                .into(holder.imageViewImg)
        holder.textViewVehicle.text=vehicleList[position].vehiculo
        holder.imageViewImg.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(Constants.ID,vehicleList[position].id)
                putExtra(Constants.ID_U,id_u)
            }
            context.startActivity(intent)
        }
    }
}
class VehiculoHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemVehicleBinding.bind(view)
    val imageViewImg = binding.imageView
    val textViewVehicle = binding.textView
}
