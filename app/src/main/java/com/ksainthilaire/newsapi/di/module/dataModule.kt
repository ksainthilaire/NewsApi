package com.ksainthilaire.newsapi.di.module

import android.content.res.Resources
import androidx.room.Room
import com.ksainthilaire.data.local.AppDatabase
import com.ksainthilaire.data.remote.NewsService
import com.ksainthilaire.data.repository.NewsRepositoryImpl
import com.ksainthilaire.domain.repository.NewsRepository
import com.ksainthilaire.newsapi.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    single<Resources> { androidContext().resources }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "database-newsapi"
        ).build()
    }
    single { get<AppDatabase>().articleDao() }
    single { get<AppDatabase>().articleSourceDao() }

    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val newUrl = original.url.newBuilder()
                .addQueryParameter("apikey", BuildConfig.NEWS_API_KEY)
                .build()
            val newRequest = original.newBuilder()
                .url(newUrl)
                .build()
            chain.proceed(newRequest)
        }

        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .apply { if (BuildConfig.DEBUG) addInterceptor(logging) }
            .cache(Cache(androidContext().cacheDir.resolve("http_cache"), 50L * 1024 * 1024))
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(
                if (BuildConfig.NEWS_API_URL.endsWith("/")) BuildConfig.NEWS_API_URL
                else BuildConfig.NEWS_API_URL + "/"
            )
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<NewsService> { get<Retrofit>().create(NewsService::class.java) }

    single<NewsRepository> {
        NewsRepositoryImpl(get(), get(), get())
    }
}
