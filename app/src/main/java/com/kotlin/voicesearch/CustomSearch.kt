package com.kotlin.voicesearch

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

import kotlinx.android.synthetic.main.activity_custom_search.*
import java.util.*
import android.view.View.OnTouchListener


class CustomSearch : AppCompatActivity() {

    lateinit var sr: SpeechRecognizer
    lateinit var display: TextView
    val TAG = "Listener"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_search)

        display = custom_text_ivew


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 527)
            }
        }

        sr = SpeechRecognizer.createSpeechRecognizer(this)
        //voiceListener()

        custom_button.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                sr.startListening(intent)

            } else if (event.action == MotionEvent.ACTION_UP) {
                voiceListener()

            }
            true
        };
    }


    fun voiceListener() {
        sr.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "onReadyForSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d(TAG, "onRmsChanged")
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d(TAG, "onBufferReceived")
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d(TAG, "onPartialResults")
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "onEvent")
            }

            override fun onBeginningOfSpeech() {
                Toast.makeText(this@CustomSearch, "Speech started", Toast.LENGTH_LONG).show()
            }

            override fun onEndOfSpeech() {
                Toast.makeText(this@CustomSearch, "Speech finished", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: Int) {
                var string = when (error) {
                    6 -> "No speech input"
                    4 -> "Server sends error status"
                    8 -> "RecognitionService busy."
                    7 -> "No recognition result matched."
                    1 -> "Network operation timed out."
                    2 -> "Other network related errors."
                    9 -> "Insufficient permissions"
                    5 -> " Other client side errors."
                    3 -> "Audio recording error."
                    else -> "unknown!!"
                }
                Toast.makeText(this@CustomSearch, "sorry error occurred: $string", Toast.LENGTH_LONG).show()
            }

            override fun onResults(results: Bundle?) {
                Log.d(TAG, "onResults $results")
                val data = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val s = data
                custom_text_ivew.text = data!![0]
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            527 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
