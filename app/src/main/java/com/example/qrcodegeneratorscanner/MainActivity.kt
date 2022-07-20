package com.example.qrcodegeneratorscanner

import android.Manifest
//import android.app.Activity
import android.content.Context
import android.content.Intent
//import android.content.pm.PackageManager
import android.graphics.Bitmap
//import android.icu.lang.UProperty.NAME
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.support.v4.app.ActivityCompat.startActivity
import android.view.MotionEvent
//import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.view.inputmethod.InputMethodManager as InputMethodManager1

class MainActivity : AppCompatActivity() {
    var im: ImageView? = null
    var bGenerate: Button? = null
    var bReset: Button? = null
    var bScan: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        im = findViewById(R.id.qrimage)
        bGenerate = findViewById(R.id.btnGenerate)
        bReset = findViewById(R.id.btnReset)
        bScan = findViewById(R.id.btnScan)
        val edT: EditText = findViewById(R.id.editText)


        //scan QR code
        bScan?.setOnClickListener{
            val intent = Intent( this, Scanner::class.java)
            startActivity(intent)
        }

        //generate QR code
        bGenerate?.setOnClickListener {
            try {
                val barcodeEncode = BarcodeEncoder()
                val bitmap: Bitmap =
                    barcodeEncode.encodeBitmap(edT.text.toString(), BarcodeFormat.QR_CODE, 350, 350)
                im?.setImageBitmap(bitmap)   //align generated QR code to the viewImage
            } catch (e: WriterException) {
            }
        }

        //clear EditText field and remove generated QR code
        bReset?.setOnClickListener {
            edT.setText("")
            im?.setImageBitmap(null)
        }
    }

    //hide the keyboard when tap the screen out of the keyboard
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager1
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}

