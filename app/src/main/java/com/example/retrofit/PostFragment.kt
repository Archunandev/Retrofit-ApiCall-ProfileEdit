package com.example.retrofit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.retrofit.adapter.VolleyAdapter
import com.example.retrofit.model.Data2
import com.example.retrofit.model.jsonBase
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text


class PostFragment : Fragment() {

    var dataList2 = ArrayList<Data2>()
    lateinit var  adapter: VolleyAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout




    @BindView(R.id.recyclerViewgrid)
    lateinit var recyclerView2: RecyclerView



    lateinit var gridLayoutManager: GridLayoutManager
    var url = "https://reqres.in/api/users?page=2"

    private var requestQueue: RequestQueue? = null

    private val dividerItemDecoration: DividerItemDecoration? = null


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

         //getDataGrid()
       // getData()
        volleyGet()
       // getdatfromserver()

         gridLayoutManager = GridLayoutManager(activity, 2)

          recyclerView2 = view.findViewById<RecyclerView>(R.id.recyclerViewgrid)



        return view

    }

    fun volleyGet() {

        val url = "https://reqres.in/api/users?page=2"
        val requestQueue = Volley.newRequestQueue(this.context)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, object: com.android.volley.Response.Listener<JSONObject> {
            override fun onResponse(response:JSONObject) {
                try
                {
                    val jsonArray = response.getJSONArray("data")

                    for (i in 0 until jsonArray.length())
                    {
                        val jsonObject = jsonArray.getJSONObject(i)
                        var email = jsonObject.getString("email")
                        var v1 = Data2(
                                "" + jsonObject.getString("id"),
                                "" + jsonObject.getString("email"),
                                "" + jsonObject.getString("first_name"),
                                "" + jsonObject.getString("last_name"),
                                "" + jsonObject.getString("avatar")
                        )
                        dataList2.clear()
                        dataList2.add(v1)
                        Log.e("arry" ,""+v1.toString())

                    }
                    adapter = VolleyAdapter(dataList2, context!!)
                    recyclerView2.layoutManager = GridLayoutManager(context, 2)
                    recyclerView2.adapter = VolleyAdapter(dataList2, context!!)
                    recyclerView2.setHasFixedSize(true)
                }
                catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }, object: com.android.volley.Response.ErrorListener {

            override fun onErrorResponse(error:VolleyError) {
                error.printStackTrace()
            }
        })
        requestQueue.add(jsonObjectRequest)
    }




//     fun getDataGrid() {
//
//        val call: Call<jsonBase> = RetrofitInstance.getClient2.getPostGrid()
//
//
//        call.enqueue(object : Callback<jsonBase> {
//
//            override fun onResponse(call: Call<jsonBase>, response: Response<jsonBase>) {
//
//                if (response.isSuccessful) {
//
//                    dataList.clear()
//                    dataList.addAll(response!!.body()!!.data)
//                    adapter = MyAdapter(dataList, context!!)
//                    Log.e("log", "" + dataList)
//                    recyclerView2.layoutManager = (gridLayoutManager)
//                    recyclerView2.adapter = MyAdapter(dataList, context!!)
//                    recyclerView2.setHasFixedSize(true)
//
//
//
//
//                }else {
//                    Log.e("response", "error4")
//
//
//                }
//
//
//            }
//
//            override fun onFailure(call: Call<jsonBase>, throwable: Throwable) {
//
//
//            }
//        })
//
//    }


}


