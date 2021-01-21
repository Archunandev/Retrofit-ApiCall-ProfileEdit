package com.example.retrofit

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class MySinglton(context: Context) {

     lateinit var mInstance: MySinglton
    lateinit var mCtx: Context
    private var requestQueue: RequestQueue? = null

    private fun MySingleton(context: Context){
        mCtx = context
        requestQueue = getRequestQueue()
    }

    private fun getRequestQueue(): RequestQueue? {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext())
        }
        return requestQueue
    }

    @Synchronized
    fun getmInstance(context: Context): MySinglton {
        if (mInstance == null) {
            mInstance = MySinglton(context)
        }
        return mInstance
    }

    fun <T> addToRequest(request: Request<T>) {
        getRequestQueue()?.add(request)
    }
}