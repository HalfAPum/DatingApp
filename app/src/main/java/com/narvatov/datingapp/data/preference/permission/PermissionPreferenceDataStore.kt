package com.narvatov.datingapp.data.preference.permission

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.narvatov.datingapp.data.preference.PreferenceDataStore
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

abstract class PermissionPreferenceDataStore : PreferenceDataStore() {

    abstract val permissionPreferenceName: Preferences.Key<String>

    suspend fun get(): PermissionPreference {
        return dataStore.data.map { preferences ->
            val locationPreference = preferences[permissionPreferenceName]

            locationPreference?.let {
                PermissionPreference.valueOf(locationPreference)
            } ?: PermissionPreference.NONE
        }.firstOrNull() ?: PermissionPreference.NONE
    }

    suspend fun save(locationPreference: PermissionPreference) {
        dataStore.edit { preferences ->
            preferences[permissionPreferenceName] = locationPreference.name
        }
    }

}