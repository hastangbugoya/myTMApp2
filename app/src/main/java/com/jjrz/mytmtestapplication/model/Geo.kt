package com.jjrz.mytmtestapplication.model


import com.google.gson.annotations.SerializedName

data class Geo(
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("lng")
    var lng: String?
)