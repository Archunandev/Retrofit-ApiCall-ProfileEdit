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

class VolleyAdapter(private var dataList2: ArrayList<Data2>, private val context: Context) : RecyclerView.Adapter<VolleyAdapter.MyViewHolder2>() {


    class MyViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){

        // var textView1 = itemView.findViewById<TextView>(R.id.id_txt)
        var textView2 = itemView.findViewById<TextView>(R.id.email_txt)
        var textView3 = itemView.findViewById<TextView>(R.id.first_txt)
        // var textView4 = itemView.findViewById<TextView>(R.id.last_txt)
        var imageview = itemView.findViewById<ImageView>(R.id.url_txt)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout2,parent,false)

        return MyViewHolder2(view)


    }

    override fun getItemCount(): Int {
        return dataList2.size

    }

    override fun onBindViewHolder(holder: VolleyAdapter.MyViewHolder2, position: Int) {

        val dataModel2=dataList2.get(position)

        holder.textView2.text= dataModel2.email
        holder.textView3.text = dataModel2.first_name
        Glide.with(context)
                .load(dataModel2.avatar)
                .circleCrop()
                .into(holder.imageview)

    }


}