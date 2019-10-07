package com.deepak.retrofitexample

import com.google.gson.annotations.SerializedName

class Comment {

    var postId:Int = 0

    var id:Int = 0

    var name:String = ""

    var email:String = ""

    @SerializedName("body")
    var text:String = ""

}