package help.people.husse.movifeel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.example.husse.movifeel.R


class Start_screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)


        // creating the button objects
        val button = findViewById<ImageButton>(R.id.startbutton)
        val information_button = findViewById<ImageButton>(R.id.imageButton3)
        // onclick for the toast
        information_button.setOnClickListener(
                View.OnClickListener {

                    // creating the toast
                   var toast = Toast.makeText(getApplicationContext(), "Welcome to Movie Feel!, tell the app about your day and it will reccomend current movies, click on the movies to get information about them", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    // setting it to the middle of the screen
                    toast.show()


                }
        )
        // onclick to jump to next button object
        button.setOnClickListener(
                View.OnClickListener(function = {
                    // creating the intent to change activites
                    var x = Intent(this, MainActivity::class.java).apply {

                    }
                            startActivity(x)


                })
        )

    }

}
