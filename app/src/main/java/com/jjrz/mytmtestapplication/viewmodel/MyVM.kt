package com.jjrz.mytmtestapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjrz.mytmtestapplication.model.Posts
import com.jjrz.mytmtestapplication.model.Summary
import com.jjrz.mytmtestapplication.model.users
import com.jjrz.mytmtestapplication.utility.DebugHelper.Companion.LogKitty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MyVM : ViewModel() {
    lateinit var userList : MutableLiveData<users?>
    lateinit var postsList : MutableLiveData<Posts?>
    lateinit var summaryList : MutableLiveData<MutableList<Summary>>
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getUserData() {
        val service = retrofit.create(MyDataService::class.java)
        val call = service.getUsers()
        LogKitty("Retrofit1")
        call.enqueue(object : Callback<users> {
            override fun onResponse(call: Call<users>, response: Response<users>) {
                if (response.code() == 200) {
                    LogKitty("Assigning value to userList")
                    userList.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<users>, t: Throwable) {
                userList.postValue(null)
                LogKitty(t.toString())
            }
        })
        LogKitty("Retrofit2" + userList.value?.size)
    }

    fun getPostsData() {
        val service = retrofit.create(MyDataService::class.java)
        val call = service.getPosts()
        LogKitty("Retrofit3")
        call.enqueue(object : Callback<Posts> {
            override fun onResponse(call: Call<Posts>, response: Response<Posts>) {
                if (response.code() == 200) {
                    LogKitty("Assigning value to postsList")
                    postsList.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<Posts>, t: Throwable) {
                postsList.postValue(null)
                LogKitty(t.toString())
            }
        })
        LogKitty("Retrofit4" + postsList.value?.size)
    }

    interface MyDataService {
        @GET("forecast")
        fun getUsers(): Call<users>

        @GET("posts")
        fun getPosts(): Call<Posts>
    }

    fun consolidateLists() {
        userList.value?.forEach { usersItem ->
            val list = postsList.value?.filter {
                it.id == usersItem.id
            }
            list?.forEach {
                summaryList.value?.add(Summary(usersItem.company?.name,it.title,it.body))
            }
        }
    }
}