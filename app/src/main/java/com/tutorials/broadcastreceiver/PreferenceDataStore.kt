//package com.tutorials.broadcastreceiver
//
//import android.content.Context
//import androidx.datastore.preferences.core.stringPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import com.tutorials.broadcastreceiver.Constants.USER_PREFERENCES_NAME
//import kotlinx.coroutines.flow.Flow
//
//class PreferenceDataStore {
//    private val Context.dataStore by preferencesDataStore(
//        name = USER_PREFERENCES_NAME
//    )
//
//    private object preferencesKeys {
//        val AIRPLANE_MODE = stringPreferencesKey("airplane_mode")
//    }
//
//
//    private val userPreferencesFlow: Flow<String> = dataStore.data.map { preferences ->
//        val sortOrder =
//            SortOrder.valueOf(preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.NONE.name)
//        val showCompleted = preferences[PreferencesKeys.SHOW_COMPLETED] ?: false
//
//        UserPreferences(showCompleted, sortOrder)
//    }
//
//    suspend fun updateShowCompleted(showCompleted: Boolean) {
//        dataStore.edit { preferences ->
//            preferences[PreferencesKeys.SHOW_COMPLETED] = showCompleted
//        }
//    }
//}