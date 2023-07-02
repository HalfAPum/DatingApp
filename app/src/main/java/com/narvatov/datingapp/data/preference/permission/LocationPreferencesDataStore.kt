package com.narvatov.datingapp.data.preference.permission

import androidx.datastore.preferences.core.stringPreferencesKey
import org.koin.core.annotation.Single

@Single
class LocationPreferencesDataStore : PermissionPreferenceDataStore() {

    override val dataStoreName = "LOCATION_PREFERENCES"

    override val permissionPreferenceName = stringPreferencesKey("SELECTED_LOCATION_PREFERENCE")

}