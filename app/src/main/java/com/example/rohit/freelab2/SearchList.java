package com.example.rohit.freelab2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rohit.freelab2.adapter.MyArrayAdapter;
import com.example.rohit.freelab2.model.MyDataModel;
import com.example.rohit.freelab2.parser.JSONParser;
import com.example.rohit.freelab2.utils.InternetConnection;
import com.example.rohit.freelab2.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchList extends AppCompatActivity {

    private ListView listSearchView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new MyArrayAdapter(this, list);

        /**
         * Getting List and Setting List Adapter
         */
        listSearchView = (ListView) findViewById(R.id.listSearchView);
        listSearchView.setAdapter(adapter);
        listSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getDay() + " => " + list.get(position).getTimeSlot(), Snackbar.LENGTH_LONG).show();
            }
        });

        /**
         * Just to know onClick and Printing Hello Toast in Center.
         */
//        Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Load JSON", Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(@NonNull View view) {
//
//                /**
//                 * Checking Internet Connection
//                 */
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                } else {

                    Snackbar.make( findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
//        });
//    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x=list.size();

            if(x==0)
                jIndex=0;
            else
                jIndex=x;

            dialog = new ProgressDialog(SearchList.this);
//            dialog.setTitle("");
            dialog.setMessage("Retrieving Free Lab Slots");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getDataFromWeb();

            String daySearch = getIntent().getStringExtra("Day");
            String timeSearch = getIntent().getStringExtra("Time");
            String roomSearch = getIntent().getStringExtra("Room");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if(jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_CONTACTS);

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                MyDataModel model = new MyDataModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);

                                String day = innerObject.getString(Keys.KEY_DAY);
                                String timeslot = innerObject.getString(Keys.KEY_TIMESLOT);
                                String mntnc = "MAINTENANCE";
                                String clsd = "CLOSED";
                                timeslot = timeslot + ":";
//                                Calendar calendar = Calendar.getInstance();


//                                String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

                                if(roomSearch.matches("All")) {
                                    for (int i = 0; i < Keys.KEY_ROOM.length; i++) {
                                        if (innerObject.getString(Keys.KEY_ROOM[i]).isEmpty()) {
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : FREE";
                                        }
                                        else if (innerObject.getString(Keys.KEY_ROOM[i]).matches(clsd)) {
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : " + clsd;
                                        }
                                        else if (innerObject.getString(Keys.KEY_ROOM[i]).matches(mntnc)) {
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : " + mntnc;
                                        }
                                        else{
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : "+ innerObject.getString(Keys.KEY_ROOM[i]);
                                        }
                                    }
                                }
                                else{
                                    for (int i = 0; i < Keys.KEY_ROOM.length; i++) {
                                        if (Keys.KEY_ROOM[i].matches(roomSearch) && innerObject.getString(Keys.KEY_ROOM[i]).isEmpty()) {
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : FREE";
                                        }
                                        else if (Keys.KEY_ROOM[i].matches(roomSearch) && innerObject.getString(Keys.KEY_ROOM[i]).matches(clsd)) {
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : " + clsd;
                                        }
                                        else if (Keys.KEY_ROOM[i].matches(roomSearch) && innerObject.getString(Keys.KEY_ROOM[i]).matches(mntnc)) {
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : " + mntnc;
                                        }
                                        else if(Keys.KEY_ROOM[i].matches(roomSearch)){
                                            timeslot = timeslot + "\n" + Keys.KEY_ROOM[i] + " : "+ innerObject.getString(Keys.KEY_ROOM[i]);
                                        }
                                    }
                                }

                                Log.v("TIME CHECK","Time:  " + timeslot.substring(0,9));
                                Log.v("TIME CHECK","Searched Time:  " + timeSearch);


                                //if all days

                                if(daySearch.matches("All")){
                                    if(timeSearch.matches("All")) {
                                        model.setDay(day);
                                        model.setTimeSlot(timeslot);
                                        list.add(model);
                                    }
                                        else if(timeSearch.matches(timeslot.substring(0,9))){
                                        model.setDay(day);
                                        model.setTimeSlot(timeslot);
                                        list.add(model);
                                    }
                                }
                                else if(daySearch.matches(day)){
                                    if(timeSearch.matches("All")) {
                                        model.setDay(day);
                                        model.setTimeSlot(timeslot);
                                        list.add(model);
                                    }
                                    else if(timeSearch.matches(timeslot.substring(0,9))){
                                        model.setDay(day);
                                        model.setTimeSlot(timeslot);
                                        list.add(model);
                                    }
                                }


//                                if(day.matches(today) && !timeslot.endsWith(":")) {
//                                    if(currentday.matches(today)) {
//                                        model.setTimeSlot(timeslot);
//                                        list.add(model);
//                                    }
//                                    else{
//                                        model.setDay(day);
//                                        model.setTimeSlot(timeslot);
//                                        list.add(model);
//                                        currentday = today;
//                                    }
//                                }

                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */

            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                    Snackbar.make(findViewById(android.R.id.content), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
