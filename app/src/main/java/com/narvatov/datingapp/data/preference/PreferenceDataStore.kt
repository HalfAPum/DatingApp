package com.narvatov.datingapp.data.preference

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.narvatov.datingapp.utils.inject
import kotlin.jvm.internal.PropertyReference1Impl

abstract class PreferenceDataStore {

    private val context: Context by inject()

    abstract val dataStoreName: String

    protected val dataStore by lazy {
        preferencesDataStore(dataStoreName).getValue(
            context,
            PropertyReference1Impl(
                PreferenceDataStore::class,
                dataStoreName,
                "get" + dataStoreName.replaceFirstChar(Char::uppercaseChar)
            )
        )
    }

}