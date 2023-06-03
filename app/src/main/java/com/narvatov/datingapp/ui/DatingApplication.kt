package com.narvatov.datingapp.ui

import android.app.Application
import android.util.Log
import com.halfapum.general.coroutines.exception.DefaultCoroutineExceptionHandler
import com.halfapum.general.coroutines.exception.ExceptionPropagator
import com.halfapum.general.coroutines.exception.generalCoroutineExceptionHandler
import com.narvatov.datingapp.di.appModule
import com.narvatov.datingapp.utils.plantTimberDebug
import org.koin.core.component.KoinComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.defaultModule
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class DatingApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        generalCoroutineExceptionHandler = object : DefaultCoroutineExceptionHandler() {
            override fun handleException(context: CoroutineContext, exception: Throwable) {
                Timber.e(coroutineTag, exceptionMessage, exception)

                ExceptionPropagator.propagate(exception)
            }
        }

        plantTimberDebug()

        startKoin {
            androidLogger()
            androidContext(this@DatingApplication)
            modules(
                defaultModule,
                appModule,
            )
        }
    }
}