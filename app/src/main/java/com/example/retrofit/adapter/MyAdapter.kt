package com.example.retrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.R
import com.example.retrofit.model.Data2


class MyAdapter(private var dataList: ArrayList<Data2>, private val context: Context) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

       // var textView1 = itemView.findViewById<TextView>(R.id.id_txt)
        var textView2 = itemView.findViewById<TextView>(R.id.email_txt)
        var textView3 = itemView.findViewById<TextView>(R.id.first_txt)
       // var textView4 = itemView.findViewById<TextView>(R.id.last_txt)
        var imageview = itemView.findViewById<ImageView>(R.id.url_txt)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)

        return MyViewHolder(view)


    }

    override fun getItemCount(): Int {
        return dataList.size

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val dataModel=dataList.get(position)

        holder.textView2.text= dataModel.email
        holder.textView3.text = dataModel.first_name
        Glide.with(context)
                .load(dataModel.avatar)
                .circleCrop()
                .into(holder.imageview)



    }


}