package com.example.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private val lowBound: Int = 10
    private val highBound: Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prev = findViewById<ImageButton>(R.id.ibPrev)
        val next = findViewById<ImageButton>(R.id.ibNext)
        val play = findViewById<ImageButton>(R.id.ibPlay)
        val songCount = findViewById<TextView>(R.id.tvSongsPlayed)

        val username = findViewById<TextView>(R.id.tvUserName)
        val newUsername = findViewById<EditText>(R.id.etUserName)
        val changeUsername = findViewById<Button>(R.id.btChangeUser)

        newUsername.visibility = View.INVISIBLE

        prev.setOnClickListener {preOnClick()}

        val rndPlay = (lowBound..highBound).random()
        songCount.text = rndPlay.toString()
        play.setOnClickListener {playOnClick(songCount)}

        next.setOnClickListener {nextOnClick()}

        changeUsername.setOnClickListener {changeOnClick(changeUsername, newUsername, username)}
    }

    private fun preOnClick() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_LONG).show()
    }

    private fun playOnClick(songCount: TextView) {
        songCount.text = (songCount.text.toString().toInt() + 1).toString()
    }

    private fun nextOnClick() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_LONG).show()
    }

    private fun changeOnClick(change: Button, new: EditText, username: TextView) {
        if (change.text == "Change User") {
            change.text = "Apply"
            new.visibility = View.VISIBLE
            username.visibility = View.INVISIBLE
        } else {
            if (new.text.toString() == "") {
                Toast.makeText(this, "You need to enter a username!", Toast.LENGTH_LONG).show()
            } else {
                change.text = "Change User"
                new.visibility = View.INVISIBLE
                username.text = new.text
                username.visibility = View.VISIBLE
            }
        }
    }
}