package com.jjrz.mytmtestapplication.model.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.jjrz.mytmtestapplication.databinding.ActivityMainBinding
import com.jjrz.mytmtestapplication.viewmodel.MyVM

class MainActivity : AppCompatActivity() {
    private lateinit var myVM : MyVM
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        var adapter = MyListAdapter()
        binding.mainRecyclerview.adapter = adapter
        myVM = ViewModelProvider(this).get(MyVM::class.java)
        myVM.getPostsData()
        myVM.getUserData()
        myVM.userList.observe(this) {
            if (myVM.postsList.value?.size!! > 0) {
                myVM.consolidateLists()
            }
        }
        myVM.postsList.observe(this) {
            if (myVM.userList.value?.size!! > 0) {
                myVM.consolidateLists()
            }
        }
        myVM.summaryList.observe(this) {
            adapter.updateList(myVM.summaryList.value)
        }
    }
}