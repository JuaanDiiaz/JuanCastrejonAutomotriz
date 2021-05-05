package com.example.juancastrejonautomotriz.Tools

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionAplication(val context: Context) {
    fun hasPermission(permissionName:String):Boolean{
        if(ContextCompat.checkSelfPermission(context,permissionName)!= PackageManager.PERMISSION_GRANTED){
            return false
        }
        return true
    }
    fun acceptPermission(listPermissions:Array<String>, requestCode:Int){
        ActivityCompat.requestPermissions(context as Activity, listPermissions, requestCode)
    }
}