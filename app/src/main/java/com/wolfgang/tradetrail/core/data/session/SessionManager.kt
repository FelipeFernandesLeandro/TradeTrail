package com.wolfgang.tradetrail.core.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.wolfgang.tradetrail.core.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val userIdFlow: Flow<Int?> = dataStore.data
        .map { it[PrefKeys.USER_ID] }

    val tokenFlow: Flow<String?> = dataStore.data
        .map { it[PrefKeys.TOKEN] }

    suspend fun saveSession(session: LoginResponse) {
        dataStore.edit {
            it[PrefKeys.USER_ID] = session.id
            it[PrefKeys.TOKEN] = session.token
        }
    }

    suspend fun clear() = dataStore.edit { it.clear() }
}