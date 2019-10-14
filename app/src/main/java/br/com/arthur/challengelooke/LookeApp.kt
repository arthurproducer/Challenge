package br.com.arthur.challengelooke

import android.app.Application
import br.com.arthur.challengelooke.di.repositoryModule
import br.com.arthur.challengelooke.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class LookeApp : Application(){
    override fun onCreate(){

        //Stetho.initializeWithDefaults(this)

        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@LookeApp)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule)
            )
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}