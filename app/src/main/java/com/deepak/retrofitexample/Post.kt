package com.deepak.retrofitexample

import com.google.gson.annotations.SerializedName

class Post {

    var userId:Int

    var id: Int? = null

    var title:String?

    @SerializedName("body")
    var text:String?

    constructor(userId:Int,title:String?,text:String?){
        this.userId = userId
        this.title = title
        this.text = text
    }
}