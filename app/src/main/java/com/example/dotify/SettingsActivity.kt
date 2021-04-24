package com.example.dotify

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.Song

fun navigateToSettingsActivity(context: Context)  {
    val intent = Intent(context, SettingsActivity::class.java)
//    val bundle = Bundle()
//    bundle.putParcelable(SONG_KEY, song)
//    intent.putExtras(bundle)
    context.startActivity(intent)
}

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}