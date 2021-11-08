package com.example.photoalbumapp.di

import android.content.Context
import android.util.Log
import com.example.photoalbumapp.data.api.API_BASE_URL
import com.example.photoalbumapp.data.api.ApiService
import com.example.photoalbumapp.data.api.StatusCode.SUCCESS
import com.example.photoalbumapp.data.auth.AuthInjectionInterceptor
import com.example.photoalbumapp.data.auth.HttpLoggingInterceptor
import com.example.photoalbumapp.utils.Helper
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.CacheControl
import okhttp3.Interceptor

import android.net.ConnectivityManager
import com.example.photoalbumapp.utils.Constants


@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApiService(client: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    @Reusable
    fun provideOkHttpClient(
        authInjection: AuthInjectionInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .addInterceptor(emptyBodyInterceptor)
            .addInterceptor(authInjection)
            .addInterceptor(provideOfflineCacheInterceptor())
            .addNetworkInterceptor(provideCacheInterceptor())
            .cache(provideCache())
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .also {
                if (Helper.isDebug) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    it.addInterceptor(httpLoggingInterceptor)
                }
            }
            .build()
    }

    /**
     * Interceptor for okHttp that doesn't fail when the server responds with a content length of zero
     */
    private val emptyBodyInterceptor = object : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val response = chain.proceed(chain.request())
            if (response.isSuccessful.not() ||
                response.code().let { it != 204 && it != 205 && it !=201 }) {
                return response
            }
            return response
                .newBuilder()
                .code(SUCCESS)
                .also {
                    if ((response.body()?.contentLength() ?: -1) < 0 ||
                        response.body()?.string().isNullOrBlank()) {
                        val mediaType = MediaType.parse("text/plain")
                        val emptyBody = ResponseBody.create(mediaType, "{}")
                        it.body(emptyBody)
                    }
                }
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideCache(): Cache? {
        var cache: Cache? = null
        try {
            cache = Cache(File(PhotoAlbumApp.getContext().cacheDir, "http-cache"), 20 * 1024 * 1024) // 10 MB
        } catch (e: Exception) {
            Log.e("Cache", "Could not create Cache!")
        }
        return cache
    }

    @Singleton
    @Provides
    fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val response = chain.proceed(chain.request())
            val cacheControl: CacheControl
            cacheControl = if (isNetworkConnected(PhotoAlbumApp.getContext())) {
                CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()
            } else {
                CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
            }
            response.newBuilder()
                .removeHeader(Constants().HEADER_PRAGMA)
                .removeHeader(Constants().HEADER_CACHE_CONTROL)
                .header(Constants().HEADER_CACHE_CONTROL, cacheControl.toString())
                .build()
        }
    }

    @Singleton
    @Provides
    fun provideOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            var request = chain.request()
            if (isNetworkConnected(PhotoAlbumApp.getContext())) {
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()
                request = request.newBuilder()
                    .removeHeader(Constants().HEADER_PRAGMA)
                    .removeHeader(Constants().HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            }
            chain.proceed(request)
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }


    companion object {
        const val TIMEOUT_SECONDS: Long = 100
    }
}