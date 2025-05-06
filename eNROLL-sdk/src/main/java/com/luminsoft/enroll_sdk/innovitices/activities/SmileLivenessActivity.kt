package com.luminsoft.enroll_sdk.innovitices.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luminsoft.enroll_sdk.innovitices.core.RESULT_INTERRUPTED
import com.luminsoft.enroll_sdk.lumin_sdk.core.LuminSDKHelper
import com.luminsoft.ocr.core.models.OCRMode

class SmileLivenessActivity : AppCompatActivity() {
    var outSmileLivenessUri = "smile-liveness-uri"

    override fun onCreate(savedInstanceState: Bundle?) {

        val lang = "ar"

        super.onCreate(savedInstanceState)
        LuminSDKHelper.initOCR(this, OCRMode.SMILE_LIVENESS, lang)

        setResult(RESULT_INTERRUPTED)
    }
}
