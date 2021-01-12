package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.retrofit.adapter.MyAdapter
import com.example.retrofit.api.RetrofitInstance
import com.example.retrofit.model.Data2
import com.example.retrofit.model.jsonBase
import kotlinx.android.synthetic.main.fragment_get.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetFragment() : Fragment() {

    var dataList = ArrayList<Data2>()
    lateinit var  adapter: MyAdapter
    lateinit var swipeRefreshLayout:SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_get, container, false)

        val  recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.main_swiperefresh)

        recyclerView.setHasFixedSize(true)


        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.setRefreshing(false)
            swiferefresh()
        }

        getData()

 return view
    }

    private fun swiferefresh() {
        dataList.clear()
        getData()
    }


    private fun getData() {

        val call: Call<jsonBase> = RetrofitInstance.getClient2.getPost()
        call.enqueue(object : Callback<jsonBase> {

            override fun onResponse(call: Call<jsonBase>?, response: Response<jsonBase>?) {
                dataList.clear()
                dataList.addAll(response!!.body()!!.data)

                adapter = MyAdapter(dataList, context!!)
                shimmer_view_container.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                Log.e("log", "" + dataList)
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = MyAdapter(dataList, context!!)
                recyclerView.setHasFixedSize(true)

            }

            override fun onFailure(call: Call<jsonBase>?, t: Throwable?) {


            }

        })
    }

    override fun onResume() {
        super.onResume()
        shimmer_view_container.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        shimmer_view_container.stopShimmer()
    }





    }
