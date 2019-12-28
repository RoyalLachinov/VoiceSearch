package com.kotlin.voicesearch

import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_custom_search.*

class Listener(context: Context) : RecognitionListener {
    private var ctx = context
    val TAG = "Listener"
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
        Toast.makeText(ctx, "Speech started", Toast.LENGTH_LONG).show()
    }

    override fun onEndOfSpeech() {
        Toast.makeText(ctx, "Speech finished", Toast.LENGTH_LONG).show()
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
        Toast.makeText(ctx, "sorry error occurred: $string", Toast.LENGTH_LONG).show()
    }

    override fun onResults(results: Bundle?) {
        Log.d(TAG, "onResults $results")
        val data = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val s = data
       // contexcustom_text_ivew.text = data!![0]
    }
}