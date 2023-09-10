package com.roy93group.libresudoku

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.roy93group.libresudoku.di.ACRA_SHARED_PREFS_NAME
import dagger.hilt.android.HiltAndroidApp
import org.acra.config.dialog
import org.acra.config.mailSender
import org.acra.data.StringFormat
import org.acra.ktx.initAcra
import java.time.LocalDate

//TODO firebase
//TODO ad

//ic_launcher
//policy
//rate app, more app, share app
//keystore

//done
//leak canary
//permission ad id

@HiltAndroidApp
class LibreSudokuApp : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        if (BuildConfig.DEBUG) {
            //do nothing
        } else {
            // Only user can send a crash report
            initAcra {
                buildConfigClass = BuildConfig::class.java
                reportFormat = StringFormat.KEY_VALUE_LIST

                dialog {
                    title = getString(R.string.app_name)
                    text = getString(R.string.dialog_crash_report_text)
                    negativeButtonText = getString(R.string.dialog_no)
                    positiveButtonText = getString(R.string.dialog_yes)
                    resTheme = android.R.style.Theme_DeviceDefault_Dialog
                }

                mailSender {
                    mailTo = "roy93group@gmail.com"
                    reportFileName = "Report_${LocalDate.now()}_v${BuildConfig.VERSION_NAME}.txt"
                    subject = "LibreSudoku crash report"
                    reportAsFile = true
                }
                sharedPreferencesName = ACRA_SHARED_PREFS_NAME
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Toast.makeText(this, "$packageName onCreate", Toast.LENGTH_SHORT).show()
        }
    }
}
