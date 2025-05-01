package com.wolfgang.tradetrail.core.data.session

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PrefKeys {
    val TOKEN = stringPreferencesKey("token")
    val USER_ID = intPreferencesKey("userId")
}