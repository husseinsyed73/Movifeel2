package help.people.husse.movifeel
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.husse.movifeel.R
import com.ibm.watson.developer_cloud.service.exception.UnsupportedException

import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.util.*


class MainActivity : AppCompatActivity()
{

    var c =0;
    var d = 0;
    // using a sparse array because hash maps cant use primitive data types
    var singleparsed =  ArrayList<String>();
    // cant make full instance because kotlin has singletons
    val tester = watson_data_client();
    // after the queue is selected then it waits for one more command before it can be used
    val watt = watson_data_interpreter();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        findViewById<ProgressBar>(R.id.loadingPanel).setVisibility(View.GONE);
        val calculate = findViewById<ImageButton>(R.id.process)
        val gettingdata  = findViewById<EditText>(R.id.input)


        // also going to test if the api is still working;
        // setting up the onclick for the button
        calculate.setOnClickListener {


            // both movie database and watson get done here
            // clearing the singleparsed before every request
        // once button is clicked create the animation




    AsyncTask.execute {


        try {
            runOnUiThread {
                findViewById<ProgressBar>(R.id.loadingPanel).setVisibility(View.VISIBLE);

            }
            val getting_results = tester.create_request(gettingdata.getText().toString())
            // arrays of emotions and keywords
            // error is happening near the ibm watson

            var emotions = tester.get_emotions(getting_results) // grabbing emotions from json
            var keywords = tester.get_keywords(getting_results) // filtering keywords from json
            // if no key words were returned, means ok input but not enought information for ibm
            runOnUiThread {
                if (keywords.size == 0) {
                    findViewById<ProgressBar>(R.id.loadingPanel).setVisibility(View.GONE);

                    var toast = Toast.makeText(getApplicationContext(), "Please type more", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    // setting it to the middle of the screen
                    toast.show()

                }
            }
            // making the requests for each word
            get_and_parse(keywords, emotions)


        }catch(e:Exception)
        {
                    runOnUiThread {
                        var toast = Toast.makeText(getApplicationContext(), "Maybe you entered something in wrong ?", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        // setting it to the middle of the screen
                        toast.show()
                        findViewById<ProgressBar>(R.id.loadingPanel).setVisibility(View.GONE);
                    }
        }

    }
            }



                // once you leave this block the thread will be finished and now singleparsed should have the data
    }

    // control flow of this program moves
    // recive the custon url and parse and recieve the data
    fun get_and_parse(keywords:Array<String>,emotions:Array<String>,context:Context = this) {

        var counter =0
       // no c in here because it will leave the method
        // counting till keywords hits a non nothing
        //------------------------------------------------------------------------------------------BIG LEAVE ALONE THIS FILTERS OUT THE NOTHINGS-----------------------------------


            for (x in keywords) {
                // resetting


                val queue = Volley.newRequestQueue(this)
                // creating the if statements onces all pages are met no requests should be sent

                // if sent out keep waiting till next one

                val stringRequest = StringRequest(Request.Method.GET, data_creator(x),
                        // passing a response listener and overriding the methods
                        Response.Listener { response ->
                            // defining a json
                            // json array holds the json objects
                            // just going to get start with grabbing the number of pages
                                var testing = response

                            try {
                                // just adding the data now

                                // if the counter ==0 then grab the page number
                                val testing_total = JSONObject(response)
                                var current_page = testing_total.get("page")

                                // grabbing the current page number


                                singleparsed.add(counter++, response)
                                // if all the page numbers were received then set the finiahed to true



                                if (singleparsed.size == keywords.size) {

                                    var converter = ArrayList<String>();

                                    // passing the emotions and all the received json to the interpreter for solving
                                    var sorter = watson_data_interpreter()
                                    // this function will return all converted json that was sorted from the single parsed

                                    var sorted_data_from_database = sorter.sort(singleparsed, emotions)
                                    // now whatever needs to be done in the program
                                    // waiting till last key word


                                    // take the sorted data from the database
                                    // an move into the next class and use the custom adapter from that class
                                    var display_data = Intent(this, Showing_data::class.java).apply {
                                        putExtra("data", sorted_data_from_database)
                                    }
                                    findViewById<ProgressBar>(R.id.loadingPanel).setVisibility(View.GONE);
                                    startActivity(display_data)


                                    // no need to clear the converter because it gets reinitliazed every time
                                    singleparsed.clear()

                                }


                            } catch (e: JSONException) {
                                // something to catch it

                            }
                        }, Response.ErrorListener {


                })

                // sending the request
                queue.add(stringRequest)

            }


    }



            // creating the string function


// testing debugger




// making it smaller by last 5 years
    fun data_creator(search: String): String {


        // check page numbers later
        var url = "https://api.themoviedb.org/3/search/movie?api_key=1fedb2be93a4a69258c4dd85f602e2d1&language=en-US&query=$search&include_adult=false"
        // returning the string from the function
        // had to grab the context from an activity class
        return url
    }

}












