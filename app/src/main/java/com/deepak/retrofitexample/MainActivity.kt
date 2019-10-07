package com.deepak.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var jsonPlaceHolderApi:JsonPlaceHolderApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        var okHttpClient:OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(object : Interceptor{
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

                    var originalReqest = chain.request()

                    var newRequest = originalReqest.newBuilder().header("Intercepting","This is glory").build()

                    return chain.proceed(newRequest)
                }

            })
            .addInterceptor(loggingInterceptor).build()


//        var retrofit = Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()


        //Use this to serialize nulls
        var gson = GsonBuilder().serializeNulls().create()
        var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi::class.java)

        getPosts()

//        getComments()

//        createPost()

//        updatePost()

//        deletePost()
    }


    private fun getPosts() {
//first api
//        var call = jsonPlaceHolderApi.getPosts()

//second api with query with userid
//        var call = jsonPlaceHolderApi.getPosts(5)


//third api with query with userid, sort and order
//        var call = jsonPlaceHolderApi.getPosts(5,"id","desc")

//fourth api with array
//        var call = jsonPlaceHolderApi.getPosts(arrayOf(5,10),null,null)

//fifth api with querymap
        var parameters = mutableMapOf<String,String>()
        parameters.put("userId","3")
        parameters.put("_sort","id")
        parameters.put("_order","asc")

        var call = jsonPlaceHolderApi.getPosts(parameters)

        call.enqueue(object : Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                text_view_result.setText(t.message)
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(!response.isSuccessful)
                {
                    text_view_result.setText("Code : " + response.code())
                    return
                }

                var posts = response.body()
                if (posts != null) {
                    for(post:Post in posts){
                        var content:String = ""

                        content+="ID: " + post.id + "\n"
                        content+="UserId: " + post.userId+ "\n"
                        content+="Title: " + post.title+ "\n"
                        content+="Text: " + post.text+ "\n\n"

                        text_view_result.append(content)
                    }
                }
            }

        })
    }

    private fun getComments() {

        //first api
//        var call = jsonPlaceHolderApi.getComments(10)

        //passing url in the api
        var call = jsonPlaceHolderApi.getComments("posts/3/comments")

        call.enqueue(object : Callback<List<Comment>>{
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                text_view_result.setText(t.message)
            }

            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(!response.isSuccessful)
                {
                    text_view_result.setText("Code : " + response.code())
                    return
                }

                var comments = response.body()
                if (comments != null) {
                    for(comment:Comment in comments){
                        var content:String = ""

                        content+="ID: " + comment.id + "\n"
                        content+="PostId: " + comment.postId+ "\n"
                        content+="Name: " + comment.name+ "\n"
                        content+="email: " + comment.email+ "\n"
                        content+="Text: " + comment.text+ "\n\n"

                        text_view_result.append(content)
                    }
                }
            }

        })
    }


    private fun createPost() {

        //Api posting Post object
//        var post:Post = Post(21,"Final Title","New Text")
//
//        val call = jsonPlaceHolderApi.createPost(post)

        //Api using formurlencoded
//        val call = jsonPlaceHolderApi.createPost(25,"Super Title","Probably Final Text")

        ////Api using FieldMap
        var parameters = mutableMapOf<String,String>()
        parameters.put("userId","30")
        parameters.put("title","Super Super Title")
        parameters.put("body","Final Final Text")

        val call = jsonPlaceHolderApi.createPost(parameters)


        call.enqueue(object : Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                text_view_result.setText(t.message)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(!response.isSuccessful)
                {
                    text_view_result.setText("Code : " + response.code())
                    return
                }

                var postResponse = response.body()

                var content:String = ""
                content+="Code:" + response.code() + "\n"
                content+="ID: " + postResponse?.id + "\n"
                content+="UserId: " + postResponse?.userId+ "\n"
                content+="Title: " + postResponse?.title+ "\n"
                content+="Text: " + postResponse?.text+ "\n\n"

                text_view_result.append(content)
            }

        })

    }


    private fun updatePost() {


        var post:Post = Post(28,null,null)
//        val call = jsonPlaceHolderApi.putPost(5,post)

//        val call = jsonPlaceHolderApi.putPostWithStaticHeader(5,post)

//        val call = jsonPlaceHolderApi.putPostWithDynamicHeader("this is definitely as per convenience",5,post)

//        val call = jsonPlaceHolderApi.patchPost(5,post)


        var parameters = mutableMapOf<String,String>()
        parameters.put("Map-Header1","patch1")
        parameters.put("Map-Header2","patch2")

        val call = jsonPlaceHolderApi.patchPostWithHeaderMap(parameters,5,post)


        call.enqueue(object : Callback<Post>{
            override fun onFailure(call: Call<Post>, t: Throwable) {
                text_view_result.setText(t.message)
            }

            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(!response.isSuccessful)
                {
                    text_view_result.setText("Code : " + response.code())
                    return
                }

                var postResponse = response.body()

                var content:String = ""
                content+="Code:" + response.code() + "\n"
                content+="ID: " + postResponse?.id + "\n"
                content+="UserId: " + postResponse?.userId+ "\n"
                content+="Title: " + postResponse?.title+ "\n"
                content+="Text: " + postResponse?.text+ "\n\n"

                text_view_result.append(content)
            }

        })
    }

    private fun deletePost() {
        val call:Call<Void> = jsonPlaceHolderApi.deletePost(28)

        call.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                text_view_result.setText("t.message")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                text_view_result.setText("Code : " + response.code())
            }

        })
    }



    companion object{
        val BASE_URL:String = "https://jsonplaceholder.typicode.com/"
    }
}
