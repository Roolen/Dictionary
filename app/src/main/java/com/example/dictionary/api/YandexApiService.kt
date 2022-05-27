package com.example.dictionary.api

import com.example.dictionary.api.requests.TranslateRequest
import com.example.dictionary.api.responses.TranslateResponse
import com.example.dictionary.api.responses.DetectLanguageResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface YandexApiService {

    @POST("translate/v2/translate")
    fun getTranslateAsync(
        @Body data: TranslateRequest
    ): Deferred<TranslateResponse>

    @POST("translate/v2/detect")
    suspend fun detectLanguage(
        @Query("text") text: String
    ): DetectLanguageResponse

    @POST
    suspend fun getSpeech(
        @Url url: String = "https://tts.api.cloud.yandex.net/speech/v1/tts:synthesize",
        @Query("text") text: String,
        @Query("lang") language: String,
        @Query("format") format: String = "mp3",
        @Query("voice") voice: String = "filipp",
        @Query("emotion") emotion: String = "good"
    ): retrofit2.Response<ResponseBody>

    companion object {

        private const val key = "AQVN1EmmVnd5tWq3PzUydoi2oIVYHxeCeNVbk1gN"
        private const val baseUrl = "https://translate.api.cloud.yandex.net/"

        private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val API: YandexApiService by lazy {
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.callTimeout(30, TimeUnit.SECONDS)
            okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
            okHttpClient.readTimeout(10, TimeUnit.SECONDS)
            okHttpClient.writeTimeout(10, TimeUnit.SECONDS)
            okHttpClient.authenticator(HTTPAuthenticator())
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
                okHttpClient.addInterceptor(this)
            }
            val retrofit = Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            retrofit.create(YandexApiService::class.java)
        }

    }

    class HTTPAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request {
            return response.request.newBuilder()
                .header(
                    "Authorization",
                    "Api-Key $key"
                )
                .build()
        }
    }

}