package com.luminsoft.enroll_sdk.lumin_sdk.core

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

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


                        val file = getDisc(activity)

                        if (!file.exists() && !file.mkdirs()) {
                            file.mkdir()
                        }
                        val dir = File(file.absolutePath)
                        val filename = String.format("${System.currentTimeMillis()}.jpeg")
                        val outfile = File(dir, filename)


                        val fOut: OutputStream = FileOutputStream(outfile)
                        val pictureBitmap: Bitmap =
                            ocrSuccessModel.nationalIdImage!! // obtaining the Bitmap

                        pictureBitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            100,
                            fOut
                        ) // saving the Bitmap to a file compressed as a JPEG with 85% compression rate

                        fOut.flush() // Not really required

                        fOut.close() // do not forget to close the stream


                        val intent = Intent()
                        val uri: Uri = Uri.fromFile(outfile)


                        intent.data = uri


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

    private fun getDisc(activity: Activity): File {
        val file = activity.cacheDir
        return File(file, "/scanned/")
    }
}