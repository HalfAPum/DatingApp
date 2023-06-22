package com.narvatov.datingapp.data.preference

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.narvatov.datingapp.model.local.user.UserAuth
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class UserPreferencesDataStore : PreferenceDataStore() {

    override val dataStoreName = USER_PREFERENCE

    suspend fun getUserPreferences(): UserAuth {
        return dataStore.data.map { preferences ->
            val email = preferences[USER_EMAIL] ?: ""
            val password = preferences[USER_PASSWORD] ?: ""

            UserAuth(email, password)
        }.firstOrNull() ?: UserAuth.emptyUser
    }

    suspend fun saveUserPreferences(userAuth: UserAuth) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = userAuth.email
            preferences[USER_PASSWORD] = userAuth.password
        }
    }

    suspend fun clearUserPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private const val USER_PREFERENCE = "USER_PREFERENCE"

        private val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        private val USER_PASSWORD = stringPreferencesKey("USER_PASSWORD")
    }

}