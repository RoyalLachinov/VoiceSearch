package com.kotlin.voicesearch

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.speech.RecognizerIntent
import android.content.Intent
import android.net.Uri
import android.widget.ArrayAdapter
import android.os.Build
import android.provider.Settings


class MainActivity : AppCompatActivity() {

    var listView: TextView? = null
    var btnSearch: TextView? = null
    val VOICE_RECOGNITION_REQUEST_CODE = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list_ivew)
        btnSearch = findViewById(R.id.text_view)

        btnSearch?.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo")
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE)
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            /*
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            listView?.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, matches!!)
            */
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            listView?.text = matches?.get(0)

            /*     matches is the result of voice input. It is a list of what the user possibly said.
                 Using an if statement for the keyword you want to use allows the use of any activity if keywords match
                         it is possible to set up multiple keywords to use the same
                         activity so more than one word will allow the user
                         to use the activity (makes it so the user doesn 't have to
                         memorize words from a list)
                 to use an activity from the voice input information simply use
                 the following format;
                 if (matches.contains("keyword here") {
                         startActivity(Intent("name.of.manifest.ACTIVITY"))
                     }
                     if (matches.contains("information")) {
                         startActivity(Intent("android.intent.action.INFOSCREEN"))
                     }*/
        }
    }
}
