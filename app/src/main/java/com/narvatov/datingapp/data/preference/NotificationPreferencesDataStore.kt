package com.narvatov.datingapp.data.preference

import android.os.Build
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.narvatov.datingapp.model.local.notification.PermissionPreference
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class NotificationPreferencesDataStore : PreferenceDataStore() {

    override val dataStoreName = NOTIFICATION_PREFERENCES

    suspend fun get(): PermissionPreference {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return PermissionPreference.GRANTED

        return dataStore.data.map { preferences ->
            val notificationPreference = preferences[SELECTED_NOTIFICATION_PREFERENCE]

            notificationPreference?.let {
                PermissionPreference.valueOf(notificationPreference)
            } ?: PermissionPreference.NONE
        }.firstOrNull() ?: PermissionPreference.NONE
    }

    suspend fun save(notificationPreference: PermissionPreference) {
        dataStore.edit { preferences ->
            preferences[SELECTED_NOTIFICATION_PREFERENCE] = notificationPreference.name
        }
    }

    companion object {
        private const val NOTIFICATION_PREFERENCES = "NOTIFICATION_PREFERENCES"
        private val SELECTED_NOTIFICATION_PREFERENCE = stringPreferencesKey("SELECTED_NOTIFICATION_PREFERENCE")
    }

}