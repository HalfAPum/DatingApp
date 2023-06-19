package com.narvatov.datingapp.ui

import android.app.Application
import android.util.Log
import com.halfapum.general.coroutines.exception.DefaultCoroutineExceptionHandler
import com.halfapum.general.coroutines.exception.ExceptionPropagator
import com.halfapum.general.coroutines.exception.generalCoroutineExceptionHandler
import com.narvatov.datingapp.R
import com.narvatov.datingapp.di.appModule
import com.narvatov.datingapp.di.networkModule
import com.narvatov.datingapp.di.viewModelModule
import com.narvatov.datingapp.utils.plantTimberDebug
import com.parse.Parse
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class DatingApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        generalCoroutineExceptionHandler = object : DefaultCoroutineExceptionHandler() {
            override fun handleException(context: CoroutineContext, exception: Throwable) {
                Timber.e(coroutineTag + exceptionMessage + exception)

                ExceptionPropagator.propagate(exception)
            }
        }

        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))

                    //TODO CONSIDER REMOVING OR CHANGING TO REAL NUMBER
                .maxRetries(1)
                .build()
        )

        Parse.setLogLevel(Log.VERBOSE)

        plantTimberDebug()

        startKoin {
            androidLogger()
            androidContext(this@DatingApplication)
            modules(
                defaultModule,
                appModule,
                viewModelModule,
                networkModule,
            )
        }
    }
}