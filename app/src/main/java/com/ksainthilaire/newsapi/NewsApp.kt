package com.ksainthilaire.newsapi

import android.app.Application
import com.ksainthilaire.newsapi.di.module.dataModule
import com.ksainthilaire.newsapi.di.module.presentationModule
import io.klogging.Level
import io.klogging.config.loggingConfiguration
import io.klogging.rendering.RENDER_SIMPLE
import io.klogging.sending.STDOUT
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NewsApiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        loggingConfiguration {
            sink(sinkName = "console", renderer = RENDER_SIMPLE, sender = STDOUT)
            logging {
                fromMinLevel(Level.INFO) { toSink("console" )}
            }
        }
        startKoin {
            androidContext(this@NewsApiApp)
            modules(dataModule)
            modules(presentationModule)
        }
    }
}