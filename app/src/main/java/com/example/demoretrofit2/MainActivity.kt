package com.example.demoretrofit2


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), Interceptor {
    var retrofit: Retrofit? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var interceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


        var client = OkHttpClient.Builder().addInterceptor(this).addInterceptor(interceptor).build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .client(client)
            .build()
        retrofit?.create(ApiCalls::class.java)?.run {
            with(get(10,"id","desc")) {
                enqueue(object : Callback<MutableList<Post>> {

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<MutableList<Post>>,
                        response: retrofit2.Response<MutableList<Post>>
                    ) {
                        if (!response.isSuccessful) {
                            tv1.text = "code:" + response.code()
                            return
                        }
                        with(response.body()) {
                            this?.forEach { i ->
                                tv1.append("userId: ${i.userId}\n" +
                                           "id: ${i.id}\n" +
                                           "title: ${i.title}\n" +
                                           "body: ${i.body}") } }
                    }

                    override fun onFailure(call: Call<MutableList<Post>>, t: Throwable) {
                        tv1.text = t.message

                    }


                })
            }

            /*   with(post(100, Commet(1, 1, "santhoshpatel", "isanthoshpatel@gmail.com", "hi...hellow...how r u...."))) {
                  enqueue(object : Callback<Commet> {
                      override fun onResponse(
                          call: Call<Commet>,
                          response: retrofit2.Response<Commet>
                      ) {
                          if (!response.isSuccessful) {
                              tv1.setText(response.code())
                          }
                          with(response.body()) {
                              tv1.append(
                                  "postId:${this?.postId}\n" +
                                          "id:${this!!.id}\n" +
                                          "name:${this.name}\n" +
                                          "email:${this.email}\n" +
                                          "body:${this.body}\n"
                              )

                          }


                      }

                      override fun onFailure(call: Call<Commet>, t: Throwable) {
                          tv1.text = t.message
                      }
                  })
              } */
        }


    }

    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().header("header", "xyz").build().let {
            return chain.proceed(it)
        }
    }
}
