package help.people.husse.movifeel;

import android.widget.ArrayAdapter;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.husse.movifeel.R;

import java.util.ArrayList;

// Create a customAdapter to take an array and convert to
// our own customView
   public class CustomAdapter extends ArrayAdapter<String> {
        //Constructor for getting String
        public CustomAdapter(@NonNull Context context, ArrayList<String> foods) {
            // context means information
            super(context, R.layout.custom_row,foods);
        }
        @NonNull
        @Override
        // To layout the design
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            //Define Row Layout, Create a new view object from 1 of your layout
            LayoutInflater listInflater= LayoutInflater.from(getContext());
            // IMPORTANT, Set it equal to 1 custom view row
            View customView = listInflater.inflate(R.layout.custom_row,parent,false);


            String singleFoodItems=getItem(position);
            TextView moviText =(TextView) customView.findViewById(R.id.aventext);

            moviText.setText(singleFoodItems); //Display text

            return customView;
        }
    }


