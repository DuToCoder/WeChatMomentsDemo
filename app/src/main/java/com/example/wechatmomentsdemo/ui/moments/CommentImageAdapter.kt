package com.example.wechatmomentsdemo.ui.moments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wechatmomentsdemo.R
import com.example.wechatmomentsdemo.databinding.ItemImagesMoreBinding
import com.example.wechatmomentsdemo.databinding.ItemImagesSingleBinding
import com.example.wechatmomentsdemo.databinding.ItemTweetsCommentBinding
import com.example.wechatmomentsdemo.logic.model.TweetsInfoBean

/**
 * Created by Lim  on 2020/11/8.
 */
class CommentImageAdapter(val context:Context, private val imageList:List<TweetsInfoBean.Image>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val TYPE_SINGLE = 0

        const val TYPE_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_SINGLE){
            val view = LayoutInflater.from(context).inflate(R.layout.item_images_single,parent,false);
            SingleViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.item_images_more,parent,false);
            MoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SingleViewHolder){
            holder.bind(imageList[position])
        }else if (holder is MoreViewHolder){
            holder.bind(imageList[position])
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (imageList.size <= 1) {
            TYPE_SINGLE
        }else{
            TYPE_MORE
        }
    }

    class SingleViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        private val binding: ItemImagesSingleBinding? = DataBindingUtil.bind(itemView)

        fun bind(image: TweetsInfoBean.Image) {
            binding?.image = image
        }
    }

    class MoreViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        private val binding: ItemImagesMoreBinding? = DataBindingUtil.bind(itemView)

        fun bind(image: TweetsInfoBean.Image) {
            binding?.image = image
        }
    }
}