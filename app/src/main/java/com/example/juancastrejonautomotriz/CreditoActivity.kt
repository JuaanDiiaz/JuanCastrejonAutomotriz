package com.example.juancastrejonautomotriz

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.juancastrejonautomotriz.Tools.Constants
import com.example.juancastrejonautomotriz.Tools.PermissionAplication
import com.example.juancastrejonautomotriz.databinding.ActivityCreditoBinding

class CreditoActivity : AppCompatActivity() {
    private val permissions = PermissionAplication(this)
    lateinit var binding:ActivityCreditoBinding
    private var permissionsOK=false
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
            if(!permissions.hasPermission(Constants.PERMISSIONS_LOCATION[0])){
                permissions.acceptPermission(Constants.PERMISSIONS_LOCATION,1)
            }else{
                permissionsOK=true
            }
            if(permissionsOK){
                abrePag()
            }
        }
        binding.floatingActionButtonCompDom.setOnClickListener {
            if(!permissions.hasPermission(Constants.PERMISSION_MICROPHONE[0])){
                permissions.acceptPermission(Constants.PERMISSION_MICROPHONE,2)
            }else{
                permissionsOK=true
            }
            if(permissionsOK){
                abrePag()
            }
        }
        binding.floatingActionButtonCompIng.setOnClickListener {
            if(!permissions.hasPermission(Constants.PERMISSION_STORAGE[0])){
                permissions.acceptPermission(Constants.PERMISSION_STORAGE,3)
            }else{
                permissionsOK=true
            }
            if(permissionsOK){
                abrePag()
            }
        }
        binding.buttonSendCredit.setOnClickListener {
            Toast.makeText(this,"Solicitud enviada, nosotros nos pondremos en contacto con usted",Toast.LENGTH_LONG).show()
        }

    }
    fun abrePag(){
        val intent = Intent(Intent.ACTION_VIEW).apply { data= Uri.parse("https://images.google.com/") }
        if(intent.resolveActivity(packageManager)!=null){
            startActivity(intent)
        }
        else{
            Toast.makeText(this@CreditoActivity,"no hay navegador disponible", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1->{
                for(r in grantResults){
                    if(r!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Es necesario dar permiso de acceder a la localización",Toast.LENGTH_SHORT).show()
                        permissionsOK=false
                    }else{
                        abrePag()
                    }
                }
            }
            2->{
                for(r in grantResults){
                    if(r!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Es necesario dar permiso de acceder al microfono para continuar",Toast.LENGTH_SHORT).show()
                        permissionsOK=false
                    }else{
                        abrePag()
                    }

                }
            }
            3->{
                for(r in grantResults){
                    if(r!= PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Es necesario dar permiso de escribir daos en el dispositivo",Toast.LENGTH_SHORT).show()
                        permissionsOK=false
                    }else{
                        abrePag()
                    }
                }
            }
        }
    }
}