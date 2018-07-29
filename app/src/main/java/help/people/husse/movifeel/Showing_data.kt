package help.people.husse.movifeel

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_showing_data.*
import android.net.Uri
import android.view.Gravity
import android.widget.Toast
import com.example.husse.movifeel.R


// class that recieves the data and displays it
class Showing_data : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_showing_data)
        setSupportActionBar(toolbar)

        // grabbing the array from the bundle
        val data = intent.getStringArrayListExtra("data")
        // instance of custom adapter
        if(data.isEmpty())
        {
            var toast = Toast.makeText(getApplicationContext(), "Hmmmm.. Try to find a different way to word your day ?", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0);
            // setting it to the middle of the screen
            toast.show()

        }
        val custom = CustomAdapter(this, data)
        val list_show = findViewById<ListView>(R.id.stuff)
        // using the custom adapter in combination with the instance to populate the list view
       list_show.setAdapter(custom)
        // click events
            list_show.setOnItemClickListener(
                    {
                        parent: AdapterView<*>?, view: View?, position: Int, l: Long ->
                        // grabbing the position
                        var search_data = data[position]
                        // grabbing the title information for the data
                        val index_end_of_title = search_data.indexOf("\n")
                        // now grabbing the title
                        val full_movie_title = search_data.substring(0, index_end_of_title)
                        // creating the search for the user to get information about the movie
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/#q=" + full_movie_title))
                        startActivity(browserIntent)
                    }
            
            )
    }

}
