package org.apps.githubuser.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.apps.githubuser.R
import org.apps.githubuser.api.ItemsItem

class UserAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val avatarUrl: ImageView = itemView.findViewById(R.id.img_user)
        val tvUser : TextView = itemView.findViewById(R.id.tv_user)
    }

    var users: List<ItemsItem> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickCallback{
        fun onItemClicked(data: ItemsItem)
    }

    private var onItemClickedCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickedCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.tvUser.text = user.login
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .centerCrop()
            .into(holder.avatarUrl)

        holder.itemView.setOnClickListener {
            onItemClickedCallback?.onItemClicked(user)
        }

    }

    override fun getItemCount(): Int = users.size

}