package com.example.juancastrejonautomotriz.Tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

class NetworkConnectionState(val context: Context) {
    fun checkNetwork():Boolean{
        var answer=false
        val connectivityManager =context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.M){
            Log.d(Constants.LOG_TAG,"Version mayor o igual a M")
            val network =  connectivityManager.activeNetwork
            if(network!=null){
                connectivityManager.getNetworkCapabilities(network)?.run {
                    answer = when{
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)-> {
                            Log.d(Constants.LOG_TAG,"TRANSPORT_CELLULAR activo")
                            true
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)-> {
                            Log.d(Constants.LOG_TAG,"TRANSPORT_WIFI activo")
                            true
                        }
                        else-> {
                            Log.d(Constants.LOG_TAG,"Sin acceso a internet")
                            false
                        }
                    }
                }
            }else{
                Log.d(Constants.LOG_TAG,"network null")
            }
        }else{
            Log.d(Constants.LOG_TAG,"Version menor a M")
            connectivityManager.activeNetworkInfo?.run {
                answer = isConnected
            }
        }

        return answer
    }
}
