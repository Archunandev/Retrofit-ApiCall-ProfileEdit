package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.retrofit.adapter.MyAdapter
import com.example.retrofit.api.RetrofitInstance
import com.example.retrofit.model.Data2
import com.example.retrofit.model.jsonBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.android.volley.toolbox.ImageLoader


class PostFragment : Fragment() {

    var dataList = ArrayList<Data2>()
    lateinit var  adapter: MyAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    @BindView(R.id.recyclerViewgrid)
    lateinit var recyclerView2: RecyclerView


    lateinit var gridLayoutManager: GridLayoutManager

   lateinit var  mRequestQueue: RequestQueue
   lateinit var mStringRequest: StringRequest

   var url = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)

        ButterKnife.bind(this,view)

         getDataGrid()

         gridLayoutManager = GridLayoutManager(activity, 2)

          recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerViewgrid)

        return view

    }


     fun getDataGrid() {

        val call: Call<jsonBase> = RetrofitInstance.getClient2.getPostGrid()


        call.enqueue(object : Callback<jsonBase> {

            override fun onResponse(call: Call<jsonBase>, response: Response<jsonBase>) {

                if (response.isSuccessful) {

                    dataList.clear()
                    dataList.addAll(response!!.body()!!.data)
                    adapter = MyAdapter(dataList, context!!)
                    Log.e("log", "" + dataList)
                    recyclerView2.layoutManager = (gridLayoutManager)
                    recyclerView2.adapter = MyAdapter(dataList, context!!)
                    recyclerView2.setHasFixedSize(true)




                }else {
                    Log.e("response", "error4")


                }


            }

            override fun onFailure(call: Call<jsonBase>, throwable: Throwable) {


            }
        })

    }


}