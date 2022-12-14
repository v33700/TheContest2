package au.edu.swin.sdmd.thecontest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val sharePref = getSharedPreferences("pref", MODE_PRIVATE)

        count = sharePref.getInt("int", 0)

        Log.i("TAG", "" + count)

        result.setText(String.format("%d",count))


        if (count > 14) {

            sharePref.edit().putInt("int", count).apply()
            count = 0
        }

        fun checkBounds() {
            score.setEnabled(count < 15)
            steal.setEnabled(count > 0)
        }

        checkBounds()

        val mediaPlayer = MediaPlayer.create(this, R.raw.beep_short)

        score.setOnClickListener {
            count++
            result.text = String.format("%d",count)
            sharePref.edit().putInt("int", count).apply()
            checkBounds()
            mediaPlayer.start()
            Log.i("SCORE", "" + count)
        }

        steal.setOnClickListener {
            count--
            result.text = String.format("%d",count)
            sharePref.edit().putInt("int", count).apply()
            checkBounds()
            mediaPlayer.start()
            Log.i("STEAL", "" + count)
        }

        reset.setOnClickListener {
            count = 0
            result.text = String.format("%d",count)
            sharePref.edit().putInt("int", count).apply()
            checkBounds()
            mediaPlayer.start()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putInt("CountINT", count)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        count = savedInstanceState.getInt("CountINT")
    }

}