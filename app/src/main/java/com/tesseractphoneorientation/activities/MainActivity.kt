package com.tesseractphoneorientation.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.phoneorientationlibrary.utility.PhoneOrientationListener
import com.tesseractphoneorientation.R
import com.tesseractphoneorientation.interfaces.IPhoneOrientationData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPhoneOrientationData {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PhoneOrientationListener(this, this)

    }

    override fun getOrientationdata(pitch: String, roll: String) {
        txtPitch.text = pitch
        txtRoll.text = roll
        Log.e("sensor", "$pitch \n  $roll")
    }


}