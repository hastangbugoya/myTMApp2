package com.jjrz.mytmtestapplication.model.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jjrz.mytmtestapplication.databinding.ListItemBinding
import com.jjrz.mytmtestapplication.model.Summary

class MyListAdapter : RecyclerView.Adapter<MyListAdapter.ListViewHolder>() {
    var myDisplayList: MutableList<Summary>? = null

    fun updateList(f: MutableList<Summary>?) {
        myDisplayList = f
        notifyDataSetChanged()
    }

    inner class ListViewHolder(binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ListItemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        with(holder.binding) {
            textCompanyName.text =
                myDisplayList?.get(position)?.companyname
            textTitle.text = myDisplayList?.get(position)?.posttitle
            textBody.text = myDisplayList?.get(position)?.postbody
        }
    }

    override fun getItemCount(): Int = myDisplayList?.size ?: 0
}