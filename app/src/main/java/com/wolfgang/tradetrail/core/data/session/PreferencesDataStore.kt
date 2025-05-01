package com.wolfgang.tradetrail.core.data.session

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.userPrefs by preferencesDataStore(name = "user_prefs")