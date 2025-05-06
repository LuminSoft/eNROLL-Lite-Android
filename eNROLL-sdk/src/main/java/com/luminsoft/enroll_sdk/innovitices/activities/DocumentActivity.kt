package com.luminsoft.enroll_sdk.innovitices.activities

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luminsoft.ekyc_android_sdk.R
import com.luminsoft.enroll_sdk.innovitices.core.RESULT_INTERRUPTED
import com.luminsoft.enroll_sdk.lumin_sdk.core.LuminSDKHelper
import com.luminsoft.ocr.core.models.OCRMode
import java.util.Locale


class DocumentActivity : AppCompatActivity() {
    val frontScan = 0
    val backScan = 11
    val passportScan = 2

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
    }

    @Suppress("DEPRECATION")
    private fun setLocale(lang: String?) {
        val locale = lang?.let { Locale(it) }
        if (locale != null) {
            Locale.setDefault(locale)
        }
        val config: Configuration = baseContext.resources.configuration
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val extras = intent.extras
        var scanType = 0
        var lang = "ar"
        if (extras != null) {
            scanType = extras.getInt("scanType", frontScan)
            lang = extras.getString("localCode", "ar")
            setLocale(lang)
            when (scanType) {
                frontScan -> this.setTitle(R.string.document_front_screen)
                backScan -> this.setTitle(R.string.document_back_screen)
                passportScan -> this.setTitle(R.string.passport_screen)
            }
        }
        val ocrMode: OCRMode = when (scanType) {
            frontScan ->
                OCRMode.NATIONAL_ID_DETECTION

            backScan ->
                OCRMode.NATIONAL_ID_DETECTION

            else ->
                OCRMode.PASSPORT_DETECTION

        }
        super.onCreate(savedInstanceState)
        LuminSDKHelper.initOCR(this, ocrMode, lang)

        setResult(RESULT_INTERRUPTED)
    }

}
