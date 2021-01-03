package com.phoneorientationlibrary.utility

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import com.tesseractphoneorientation.interfaces.IPhoneOrientationData


class PhoneOrientationListener(context: Context, mlistener: IPhoneOrientationData) : SensorEventListener {

    private var mSensorManager: SensorManager? = null
    private var mRotationSensor: Sensor? = null
    private val listener = mlistener


    init {
        try {
            mSensorManager = context.getSystemService(Activity.SENSOR_SERVICE) as SensorManager?
            mRotationSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            mSensorManager!!.registerListener(this, mRotationSensor, Constants.SENSOR_DELAY)
        } catch (e: Exception) {
            Toast.makeText(context, "Hardware compatibility issue", Toast.LENGTH_LONG).show()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == mRotationSensor) {
            if (event.values.size > 4) {
                val truncatedRotationVector = FloatArray(4)
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4)
                update(truncatedRotationVector)
            } else {
                update(event.values)
            }
        }
    }

    private fun update(vectors: FloatArray) {
        val rotationMatrix = FloatArray(9)
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors)
        val worldAxisX = SensorManager.AXIS_X
        val worldAxisZ = SensorManager.AXIS_Z
        val adjustedRotationMatrix = FloatArray(9)
        SensorManager.remapCoordinateSystem(
            rotationMatrix,
            worldAxisX,
            worldAxisZ,
            adjustedRotationMatrix
        )
        val orientation = FloatArray(3)
        SensorManager.getOrientation(adjustedRotationMatrix, orientation)
        val pitch = orientation[1] * Constants.RADIOUS_DEGREE
        val roll = orientation[2] * Constants.RADIOUS_DEGREE
       listener.getOrientationdata(pitch =pitch.toString(),roll = roll.toString())
    }


}