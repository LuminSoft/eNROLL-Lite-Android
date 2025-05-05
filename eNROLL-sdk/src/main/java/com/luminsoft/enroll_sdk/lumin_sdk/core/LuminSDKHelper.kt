package com.luminsoft.enroll_sdk.lumin_sdk.core

import android.app.Activity
import android.util.Log
import com.luminsoft.ekyc_android_sdk.R
import com.luminsoft.enroll_sdk.innovitices.core.RESULT_SUCCESS
import com.luminsoft.ocr.LocalizationCode
import com.luminsoft.ocr.OCR
import com.luminsoft.ocr.core.models.OCRCallback
import com.luminsoft.ocr.core.models.OCREnvironment
import com.luminsoft.ocr.core.models.OCRFailedModel
import com.luminsoft.ocr.core.models.OCRMode
import com.luminsoft.ocr.core.models.OCRSuccessModel

object LuminSDKHelper {

    fun initOCR(
        activity: Activity,
        ocrMode: OCRMode
    ) {
        try {

            OCR.init(
                environment = OCREnvironment.STAGING,
                licenseResource = R.raw.enroll_cert,
                localizationCode = LocalizationCode.AR,
                ocrMode = ocrMode,
                ocrCallback = object :
                    OCRCallback {
                    override fun success(ocrSuccessModel: OCRSuccessModel) {
                        Log.d(
                            "OCRCallback",
                            "Nature image :${ocrSuccessModel.naturalExpressionImage}"
                        )
                        Log.d(
                            "OCRCallback",
                            "Smile image :${ocrSuccessModel.livenessSmileExpressionImage}"
                        )
                        Log.d(
                            "OCRCallback",
                            "National Id image :${ocrSuccessModel.nationalIdImage}"
                        )
                        Log.d(
                            "OCRCallback",
                            "OCR Message :${ocrSuccessModel.ocrMessage}"
                        )
                        activity.setResult(RESULT_SUCCESS, intent)
                        activity.finish()
//                        text.value = "OCR Message: ${ocrSuccessModel.ocrMessage}"

                    }

                    override fun error(ocrFailedModel: OCRFailedModel) {
                        Log.e(
                            "OCRCallback",
                            "OCR Message :${ocrFailedModel.failureMessage}"
                        )

//                        text.value = "OCR Error: ${ocrFailedModel.failureMessage}"

                    }
                },
            )
        } catch (e: Exception) {
            Log.e("error", e.toString())
        }
        try {
            OCR.launch(activity)
        } catch (e: Exception) {
//            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.e("error", e.toString())
        }
    }
}