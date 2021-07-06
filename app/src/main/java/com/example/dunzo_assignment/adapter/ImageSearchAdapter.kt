package com.example.dunzo_assignment.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.dunzo_assignment.R
import com.example.dunzo_assignment.model.Photo


class ImageSearchAdapter(private val context: Context, private var data: ArrayList<Photo>): RecyclerView.Adapter<ImageSearchAdapter.ImageViewHolder>(){
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image=itemView.findViewById<ImageView>(R.id.image_view_item_image)
        var title=itemView.findViewById<TextView>(R.id.text_view_item_title)

    }
    fun setData(data: ArrayList<Photo>)
    {
        this.data=data
        notifyDataSetChanged()
    }
    fun addData(data: ArrayList<Photo>)
    {

        this.data.addAll(data)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
       return    ImageViewHolder(
           LayoutInflater.from(context).inflate(R.layout.image_tile, null, false)
       )
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val photo=data[position]
        holder.title.text=photo.title
        val imageLink="https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_m.jpg"
        Log.d("ErrorInCode", "Network Image Link $imageLink")
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(16))
        Glide.with(context)
            .load(imageLink)
            .apply(requestOptions)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return data.size
    }
}