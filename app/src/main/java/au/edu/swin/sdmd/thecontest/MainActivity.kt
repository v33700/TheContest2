package au.edu.swin.sdmd.thecontest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val score = findViewById<Button>(R.id.score_btn)
        val steal = findViewById<Button>(R.id.steal_btn)
        val reset = findViewById<Button>(R.id.reset_btn)
        val result = findViewById<TextView>(R.id.clicks)

        val sharedPreference = getSharedPreferences("pref", MODE_PRIVATE)

        count = sharedPreference.getInt("num", 0)

        result.setText(Integer.toString(count))


        if (count > 14) {

            sharedPreference.edit().putInt("num", count).apply()
            count = 0
        }

        fun checkBounds() {
            score.setEnabled(count < 15)
            steal.setEnabled(count > 0)
        }

        checkBounds()
        var mediaPlayer = MediaPlayer.create(this, R.raw.beep_short) //audio

        score.setOnClickListener {
            count++
            result.text = count.toString()
            sharedPreference.edit().putInt("num", count).apply()
            mediaPlayer.start()
        }

        steal.setOnClickListener {
            count--
            result.text = count.toString()
            sharedPreference.edit().putInt("num", count).apply()
            mediaPlayer.start()
        }

        reset.setOnClickListener {
            count = 0

            result.text = count.toString()
            sharedPreference.edit().putInt("num", count).apply()
            mediaPlayer.start()
        }
    }

}