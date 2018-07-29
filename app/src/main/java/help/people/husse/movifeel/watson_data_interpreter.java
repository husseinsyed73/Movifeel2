package help.people.husse.movifeel;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

public class watson_data_interpreter {
    ArrayList<String> all_converted_json = new ArrayList<String>();
    // looping through all the entries in the data sparse array
    // this class will contain the code to interpret watson data and to see how to send it for a query in the movie data
    // emotions to genre function
    // use the normal search and screen out for income comedies
    String all_json_information;
public int[] emotionstogenre (String emotion)
{
    // may have to tweak the reccomendations
    int reccomended_genres [] = new int [10];
    switch(emotion)
    {
        case "Joy":
            reccomended_genres[0]=35;
            reccomended_genres[1]=28;
            reccomended_genres[2]=35;
            reccomended_genres[3]=99;
            reccomended_genres[4]=12;
            reccomended_genres[5]=16;
            reccomended_genres[6]=10751;
            reccomended_genres[7]=14;
            reccomended_genres[8]=10749;
            reccomended_genres[9]=878;
        case "Anger":
            reccomended_genres[0]=80;
            reccomended_genres[1]=18;
            reccomended_genres[2]=28;
            reccomended_genres[3]=10751;
            reccomended_genres[4]=27;
            reccomended_genres[5]=53;
            reccomended_genres[6]=10752;
            reccomended_genres[7]=99;
        case "Disgust":
            reccomended_genres[0]=99;
            reccomended_genres[1]=80;
            reccomended_genres[2]=18;
            reccomended_genres[3]=99;
            reccomended_genres[4]=36;
            reccomended_genres[5]=27;
            reccomended_genres[6]=9648;
            reccomended_genres[7]=10752;
        case "Sadness":
            reccomended_genres[0]=10752;
            reccomended_genres[1]=18;
            reccomended_genres[2]=10749;
            reccomended_genres[3]=80;
            reccomended_genres[4]=16;
            reccomended_genres[5]=36;
            reccomended_genres[6]=10749;

// people who are fearful dont want to be for long
        case "Fear":
            reccomended_genres[0]=28;
            reccomended_genres[1]=18;
            reccomended_genres[2]=35;
            reccomended_genres[3]=16;
            reccomended_genres[4]=14;
            reccomended_genres[5]=878;
            reccomended_genres[6]=12;
            reccomended_genres[7]=10751;

    }

    return reccomended_genres;
}

// sending out the search queries
    // must be used in the main class or a front end class because context is needed _______________________

// using sparse array instead of hash map because we can get the
// integer value as a key
public ArrayList<String> sort(ArrayList<String> data, String emotions[]) {
    // getting rid of any left over data if this isnt the first call
    all_converted_json.clear();
    int json_sorted_object_counter =0;

    for (int z = 0; z < data.size(); z++) {
        // the emotions are being passed into by an array so we are going to loop through it and use the
            String x = emotions[z];
        // if nothing skip the loop
        if (!(x.equals("Nothing"))) {
            // first getting our array of numbers
            int genres[] = emotionstogenre(x);
            // creating the loop to process the json
            for (int y : genres) {
                try {
                    // creating the json object
                    // data is the sparsed array for our json
                    JSONObject total = new JSONObject(data.get(z));
                    // getting the results portion
                    JSONArray next_down = total.getJSONArray("results");
                    // moving from results to collect the genre id's from each json object
                    for (int id = 0; id < next_down.length(); id++) {
                        // grabbing each movie object
                        JSONObject MOVIE = next_down.getJSONObject(id);
                        // grabbing the id array from the movie object
                        JSONArray movie_id = MOVIE.getJSONArray("genre_ids");
                        // creating an array of ints from that json array of movie id's
                        for (int genre_id_list = 0; genre_id_list < movie_id.length(); genre_id_list++) {
                            int id_number = movie_id.getInt(genre_id_list);
                            // genres is the converted list based of ibm's sentiment
                            // y is the current value from the genres list
                            if (id_number == y) {
                                // add the information to the data structure
                                // adding the title and the description into our sparse array
                                // we are actually looking at the json in terms of each object

                                all_converted_json.add(json_sorted_object_counter,MOVIE.get("title")+"\n"+MOVIE.get("overview"));
                                // moving ahead to program the json

                                json_sorted_object_counter++;

                            }

                        }


                    }


                } catch (final JSONException e) {

                    // toast for error message


                }


            }
        }


    }
    // main problem when the sparse array deletes a value needs to be accounted for
    // getting rid of duplicate values from the sparse array

// removing all the tagged data
    // sparse array faster than a hashmap but just the space complexity is worse but in this case we good
    all_converted_json = deletion(all_converted_json);
// after the recursive deletion we are still left will null values where the deletion took place

return all_converted_json;
}
    // looping through all values for deletion
    // when deleting all the values change
    // using recursion to delete the values
    public ArrayList<String> deletion (ArrayList<String> tagged_data)
    {

    for(int eliminating_duplicates=0;eliminating_duplicates<tagged_data.size();eliminating_duplicates++)
    {

        // getting all movie information
        if(tagged_data.get(eliminating_duplicates)==null)
            continue;
        String full_movie_information = tagged_data.get(eliminating_duplicates);
        // getting the index of where the title ends
        int index_end_of_title = full_movie_information.indexOf("\n");
        // now grabbing the title
        String full_movie_title = full_movie_information.substring(0,index_end_of_title);
        // now comparing this title to all other titles in the array
        for(int comparing_duplicates=0; comparing_duplicates<tagged_data.size();comparing_duplicates++)
        {
            // grabbing the duplicate to compare
            if(tagged_data.get(comparing_duplicates)==null)
                continue;
            String full_compared_movie_information = tagged_data.get(comparing_duplicates);
            // grabbing the index for the last part of the title
            int indexof_compared_full_title = full_compared_movie_information.indexOf("\n");
            // grabbing the full string
            String full_compared_title = full_compared_movie_information.substring(0,indexof_compared_full_title);
            // comparing the strings also we dont want to delete the same string if we find it
            if ((full_movie_title.equals(full_compared_title))&&(eliminating_duplicates!=comparing_duplicates))
            {
                // for the recursion should move into here
                // inserting \n just so no errors get called
                tagged_data.remove(eliminating_duplicates);
                // using recursion
                deletion(tagged_data);


            }


        }

     }
     // if it makes it through all calls returns the deleted array
    return tagged_data;
    }



}
