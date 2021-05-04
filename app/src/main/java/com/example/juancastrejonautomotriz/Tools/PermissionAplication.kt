package com.example.juancastrejonautomotriz.Tools

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionAplication(val context: Context) {
    fun hasPermissions(listPermissions:Array<String>):Boolean{

        for(permission in listPermissions){
            if(ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
    fun acceptPermission(listPermissions:Array<String>, requestCode:Int){
        ActivityCompat.requestPermissions(context as Activity, listPermissions, requestCode)
    }
}