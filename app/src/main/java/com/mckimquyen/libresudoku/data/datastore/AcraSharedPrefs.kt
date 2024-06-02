package com.mckimquyen.libresudoku.data.datastore

import android.content.Context
import com.mckimquyen.libresudoku.di.ACRA_SHARED_PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import org.acra.ACRA
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AcraSharedPrefs @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val acraEnabledKey = ACRA.PREF_ENABLE_ACRA

    private var prefs = context.getSharedPreferences(
        /* p0 = */ ACRA_SHARED_PREFS_NAME,
        /* p1 = */Context.MODE_PRIVATE
    )

    fun getAcraEnabled(): Boolean = prefs.getBoolean(/* p0 = */ acraEnabledKey, /* p1 = */ true)

    fun setAcraEnabled(enabled: Boolean) {
        with(prefs.edit()) {
            putBoolean(/* p0 = */ acraEnabledKey, /* p1 = */ enabled)
            apply()
        }
    }
}
