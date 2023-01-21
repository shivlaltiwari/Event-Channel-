package com.example.eventchannel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel

class MainActivity : FlutterActivity(), SensorEventListener, EventChannel.StreamHandler {
    private lateinit var sensorManager: SensorManager
    private var magneticSensor: Sensor? = null
    private val CHANNEL = "com.np.tiwari";
    private var eventSink: EventChannel.EventSink? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val event = EventChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        event.setStreamHandler(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val value = listOf(event.values[0], event.values[1], event.values[3])
            eventSink!!.success(value)

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // do nothing
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
        registerSensor()
    }

    override fun onCancel(arguments: Any?) {
        unRegisterSensor()
        eventSink = null
    }

    // register sensor manager
    private fun registerSensor() {
        if (eventSink == null) {
            magneticSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    // unregister sensor
    private fun unRegisterSensor() {
        if (eventSink == null) return
        sensorManager.unregisterListener(this)
    }

}
