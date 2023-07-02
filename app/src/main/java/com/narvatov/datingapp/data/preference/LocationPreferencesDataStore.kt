package com.narvatov.datingapp.data.preference

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocationPreferencesDataStore : PreferenceDataStore() {

    override val dataStoreName = LOCATION_PREFERENCES

    suspend fun get(): PermissionPreference {
        return dataStore.data.map { preferences ->
            val locationPreference = preferences[SELECTED_LOCATION_PREFERENCE]

            locationPreference?.let {
                PermissionPreference.valueOf(locationPreference)
            } ?: PermissionPreference.NONE
        }.firstOrNull() ?: PermissionPreference.NONE
    }

    suspend fun save(locationPreference: PermissionPreference) {
        dataStore.edit { preferences ->
            preferences[SELECTED_LOCATION_PREFERENCE] = locationPreference.name
        }
    }

    companion object {
        private const val LOCATION_PREFERENCES = "LOCATION_PREFERENCES"
        private val SELECTED_LOCATION_PREFERENCE = stringPreferencesKey("SELECTED_LOCATION_PREFERENCE")
    }

}