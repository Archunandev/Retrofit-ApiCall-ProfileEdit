package com.example.retrofit.api

import com.example.retrofit.util.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitInstance {

  var BASE_URL: String = "http://dems.darashmaju.com/"
    var BASE_URL2: String = "https://reqres.in/"
    val getClient: SimpleApi

        get() {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(object: Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain:Interceptor.Chain): Response {
                    val original = chain.request()
                    val request = original.newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJiODkyMTkzMzdiNzM0Yjg0MTc5M2I3ZWQyOWI4NzEyYjQ2ZmQ3ODE2YTM1ZmNjYWEzZGU3MjkxNDczMjg5ZGZhYTEwMjdiMGYzODM0NTljIn0.eyJhdWQiOiIxIiwianRpIjoiMmI4OTIxOTMzN2I3MzRiODQxNzkzYjdlZDI5Yjg3MTJiNDZmZDc4MTZhMzVmY2NhYTNkZTcyOTE0NzMyODlkZmFhMTAyN2IwZjM4MzQ1OWMiLCJpYXQiOjE2MDkxNTg2MzMsIm5iZiI6MTYwOTE1ODYzMywiZXhwIjoxNjQwNjk0NjMzLCJzdWIiOiIzOCIsInNjb3BlcyI6W119.Kz8THoHm8UzzIZ46xtGauh5C3LvTa_25e4_XxrHK7zX9Phw0LuvC8swGrA198YIr945wUJ13oy9BqgeRZRiAEuC7g1d_btFDinR8x1cfFfq_HxTNBxoAU5SL0F2uOI3ilKJtty_CfiJw-Fb4TRRfmcxavqhtN_4REDiaOoflHPxBbcTQj7hJEtbOA5HxjY5X5mCvTJaeGdLSF0VzPFXOyiCA81R0I6svD1Dyze8sIspV67RKa30GFhUTmdM611auUHY3WbQbUME0WCfIJZZVMHz-6vD42Bp-OYXAbdbvtqsPZFDiYuGB0_ug4PZDwKSfscqi3RxaL90umSn9yU9xsCHlDCUjry_sntRFy5NYLDqhhfarxecQQXSegWT7_XGntaqQl6d2cutXTovw0kC6N6pL54p0YMV6iZxdQyCUNl88n-TX0NNN6G9G2Y4QbBlBYHE8zY8S3cryunxo3m8hzPIo7g2eAl58iWPlqsXKzlLmIX3i1yZnvPzxB8ip9zLKTOOkT0bfBpBUqKi7pqLGwk1AyyBLjpAYvCpGk0cRSYBpLBofPq3SFFv2Gphd0uUs2hx6gGVO2dl_weFp1GZMQmT9kmiywj7Mdwuru-WHrylkPfYShoJ90XkOUj48kqTAZuNsp5GU08Spu1tM88eDgY-OEnId5VK42pujUNz-3Bw")
                            .build()
                    return chain.proceed(request)
                }
            }).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(SimpleApi::class.java)


        }

    val getClient2: SimpleApi

        get() {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(SimpleApi::class.java)


        }
}

