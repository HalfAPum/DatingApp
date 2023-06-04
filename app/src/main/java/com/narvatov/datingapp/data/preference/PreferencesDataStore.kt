package com.narvatov.datingapp.data.preference

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.narvatov.datingapp.data.remotedb.throwNoUserId
import com.narvatov.datingapp.model.local.UserAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class PreferencesDataStore(
    private val context: Context,
) {

    private val Context.userPreferencesDataStore by preferencesDataStore(USER_PREFERENCE)

    suspend fun getUserPreferences(): UserAuth {
        return context.userPreferencesDataStore.data.map { preferences ->
            val id = preferences[USER_ID]
            val email = preferences[USER_EMAIL] ?: ""
            val password = preferences[USER_PASSWORD] ?: ""

            UserAuth(email, password, id)
        }.firstOrNull() ?: UserAuth.emptyUser
    }

    suspend fun saveUserPreferences(userAuth: UserAuth) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[USER_ID] = userAuth.id ?: throwNoUserId(context)
            preferences[USER_EMAIL] = userAuth.email
            preferences[USER_PASSWORD] = userAuth.password
        }
    }

    suspend fun clearUserPreferences() {
        saveUserPreferences(UserAuth.emptyUser)
    }

    companion object {
        private const val USER_PREFERENCE = "USER_PREFERENCE"

        private val USER_ID = stringPreferencesKey("USER_ID")
        private val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        private val USER_PASSWORD = stringPreferencesKey("USER_PASSWORD")
    }

}