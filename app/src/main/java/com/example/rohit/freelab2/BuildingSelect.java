package com.example.rohit.freelab2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rohit.freelab2.adapter.MyArrayAdapter;
import com.example.rohit.freelab2.model.MyDataModel;
import com.example.rohit.freelab2.parser.JSONParser;
import com.example.rohit.freelab2.utils.InternetConnection;
import com.example.rohit.freelab2.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuildingSelect extends AppCompatActivity {

    private ListView listSearchView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        listSearchView = (ListView) findViewById(R.id.listBuildingView);
        listSearchView.setAdapter(adapter);
//        listSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Snackbar.make(findViewById(R.id.parentLayout), list.get(position).getDay() + " => " + list.get(position).getTimeSlot(), Snackbar.LENGTH_LONG).show();
//            }
//        });

        String title = getIntent().getStringExtra("Title");
        TextView titleName = (TextView) findViewById(R.id.Title);

        titleName.setText(title);

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

            dialog = new ProgressDialog(BuildingSelect.this);
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

            String building = getIntent().getStringExtra("Title");
            String currentDay = "";
            String tempText = "";

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

                        MyDataModel model = new MyDataModel();
                        int lenArray = array.length();
                        if(lenArray > 0) {


                            for (int i = 0; i < Keys.KEY_ROOM.length; i++) {


                                if((building.matches("Gokongwei") && Keys.KEY_ROOM[i].substring(0,1).matches("G")) ||
                                        (building.matches("Velasco") && Keys.KEY_ROOM[i].substring(0,1).matches("V")) ||
                                        (building.matches("Andrew") && Keys.KEY_ROOM[i].substring(0,1).matches("A")) ||
                                        (building.matches("LS") && Keys.KEY_ROOM[i].substring(0,1).matches("L"))||
                                        (building.matches("Others") && Keys.KEY_ROOM[i].substring(0,1).matches("C")) ||
                                        (building.matches("Others") && Keys.KEY_ROOM[i].substring(0,1).matches("J"))||
                                        (building.matches("Others") && Keys.KEY_ROOM[i].substring(0,1).matches("Y"))){
                                    for (; jIndex < lenArray; jIndex++) {

                                        /**
                                         * Creating Every time New Object
                                         * and
                                         * Adding into List
                                         */


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

//                                Calendar calendar = Calendar.getInstance();

                                        if (!currentDay.matches(day)) {

                                            currentDay = day;
                                            tempText = tempText +  currentDay + ":" + "\n";

                                            if (innerObject.getString(Keys.KEY_ROOM[i]).isEmpty()) {
                                                tempText = tempText + timeslot + " : FREE" + "\n";
                                            } else if (innerObject.getString(Keys.KEY_ROOM[i]).matches(clsd)) {
                                                tempText = tempText + timeslot + " : " + clsd + "\n";
                                            } else if (innerObject.getString(Keys.KEY_ROOM[i]).matches(mntnc)) {
                                                tempText = tempText + timeslot + " : " + mntnc + "\n";
                                            } else {
                                                tempText = tempText + timeslot + " : " + innerObject.getString(Keys.KEY_ROOM[i]) + "\n";
                                            }
                                        } else {
                                            if (innerObject.getString(Keys.KEY_ROOM[i]).isEmpty()) {
                                                tempText = tempText + timeslot + " : FREE" + "\n";
                                            } else if (innerObject.getString(Keys.KEY_ROOM[i]).matches(clsd)) {
                                                tempText = tempText + timeslot + " : " + clsd + "\n";
                                            } else if (innerObject.getString(Keys.KEY_ROOM[i]).matches(mntnc)) {
                                                tempText = tempText + timeslot + " : " + mntnc + "\n";
                                            } else {
                                                tempText = tempText + timeslot + " : " + innerObject.getString(Keys.KEY_ROOM[i]) + "\n";
                                            }
                                        }

                                    }
                                    model.setDay(Keys.KEY_ROOM[i]);
                                    model.setTimeSlot(tempText);
                                    list.add(model);
                                }
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
