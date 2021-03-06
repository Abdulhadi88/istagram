package com.example.istagram.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.istagram.R
import com.example.istagram.model.Post
import com.squareup.picasso.Picasso

class MyImagesAdapter(private val mathContext: Context, mPost: List<Post>)
    :RecyclerView.Adapter<MyImagesAdapter.ViewHolder?>() {

    private var mPost: List<Post>? = null

    init {
        this.mPost = mPost
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mathContext).inflate(R.layout.images_item_layout,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyImagesAdapter.ViewHolder, position: Int) {
        val post: Post = mPost!![position]

        Picasso.get().load(post.getPostimage()).into(holder.postImage)

    }

    override fun getItemCount(): Int {
        return mPost!!.size

    }
    class ViewHolder(@NonNull itemView: View)
        :RecyclerView.ViewHolder(itemView) {

        var postImage: ImageView = itemView.findViewById(R.id.post_image_grid)
    }
}