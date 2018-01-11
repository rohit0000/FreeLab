package com.example.rohit.freelab2;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem;
import com.example.rohit.freelab2.adapter.MyArrayAdapter;
import com.example.rohit.freelab2.model.MyDataModel;
import com.example.rohit.freelab2.parser.JSONParser;
import com.example.rohit.freelab2.utils.InternetConnection;
import com.example.rohit.freelab2.utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class Main3Activity extends AppCompatActivity {

    HorizontalScrollMenuView menu;
    private ListView listView;
    private ArrayList<MyDataModel> list;
    private MyArrayAdapter adapter;
    private Spinner spinner, spinner2;
    private Button goks, velasco, ls, others;

    private String startSelected, endSelected, buttonBuilding = "", selectedDay = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        menu = (HorizontalScrollMenuView) findViewById(R.id.menu);
        list = new ArrayList<>();
        adapter = new MyArrayAdapter(this, list);

        listView = (ListView) findViewById(R.id.listItems);
        listView.setAdapter(adapter);


        spinner = (Spinner) findViewById(R.id.spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.start_time_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
    // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.end_time_array, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinner2.setAdapter(adapter);

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");

        String currentTime = sdf.format(new Date());

        long diff1, nearest = 999999999;
        int positionStart = 0;
        Resources res = getResources();
        String[] startTime = res.getStringArray(R.array.start_time_array);

        for(int i=0; i < startTime.length; i++) {
            SimpleDateFormat choice = new SimpleDateFormat("HHmm");

            try {
                Date date = choice.parse(startTime[i]);
                Date current = sdf.parse(currentTime);

                diff1 = Math.abs(date.getTime() - current.getTime());

                if(diff1 < nearest) {
                    nearest = diff1;
                    positionStart = i;
                }
                Log.v("entwined8", "Start Time: " + startTime[i]);
                Log.v("entwined8", "Difference: " + diff1);

                Log.v("entwined8", "Nearest: " + nearest);


                String out = date.toString();

                Log.e("Time", out);
            } catch (ParseException e) {
            }


        }

//        spinner.set
        spinner.setSelection(positionStart);
        Log.v("entwined8", "Selected Item: " + spinner.getSelectedItem().toString());



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ArrayAdapter<CharSequence> adapter3;

                if (spinner.getSelectedItem().equals("0730")){
                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);


                    Log.v("spinner2","0730");
                }else if(spinner.getSelectedItem().equals("0915")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time2_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                    Log.v("spinner2","0915");
                }else if(spinner.getSelectedItem().equals("1100")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time3_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                }else if(spinner.getSelectedItem().equals("1245")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time4_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                }else if(spinner.getSelectedItem().equals("1430")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time5_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                }else if(spinner.getSelectedItem().equals("1615")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time6_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                }
                else if(spinner.getSelectedItem().equals("1800")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time7_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                }else if(spinner.getSelectedItem().equals("1940")){

                    adapter3 = ArrayAdapter.createFromResource(Main3Activity.this,R.array.end_time8_array, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner2.setAdapter(adapter3);

                }

                new SendRequest().execute();


                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }

                startSelected = spinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                startSelected = spinner.getSelectedItem().toString();            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endSelected = spinner2.getSelectedItem().toString();
                new SendRequest().execute();

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                endSelected = spinner2.getSelectedItem().toString();
            }
        });

        initMenu();

        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
        }

        goks = (Button) findViewById(R.id.buttonGokongwei);
        velasco = (Button) findViewById(R.id.buttonVelasco);
        ls = (Button) findViewById(R.id.buttonLS);
        others = (Button) findViewById(R.id.buttonOthers);

        goks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                    buttonBuilding = "Gokongwei";
                    new SendRequest().execute();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        velasco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                    buttonBuilding = "Velasco";
                    new SendRequest().execute();

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        ls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                    buttonBuilding = "LS";
                    new SendRequest().execute();

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                    buttonBuilding = "Others";
                    new SendRequest().execute();

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        menu = (HorizontalScrollMenuView) findViewById(R.id.menu);
        menu.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
            @Override
            public void onHSMClick(MenuItem menuItem, int position) {
                menu.setItemSelected(position);
                selectedDay = menuItem.getText();
                new SendRequest().execute();

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    new GetDataTask().execute();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();




    }

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

            dialog = new ProgressDialog(Main3Activity.this);
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
            String tempText = "";
            list.clear();

            if (buttonBuilding.isEmpty())
                buttonBuilding = "Gokongwei";
            if (selectedDay.isEmpty()) {
                selectedDay = "TODAY";
            }

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
                            for (int i = 0; i < Keys.KEY_ROOM.length; i++) {
                                MyDataModel model = new MyDataModel();
                                Log.v("entwined8", "Building selected is " + buttonBuilding);
                                Log.v("entwined8", "Day selected is " + selectedDay);
                                Log.v("entwined8", "Start Time: " + startSelected);
                                Log.v("entwined8", "End Time: " + endSelected);
                                Log.v("entwined8", "Room: " + Keys.KEY_ROOM[i]);

                                if ((buttonBuilding.matches("Gokongwei") && Keys.KEY_ROOM[i].substring(0, 1).matches("G")) ||
                                        (buttonBuilding.matches("Velasco") && Keys.KEY_ROOM[i].substring(0, 1).matches("V")) ||
                                        (buttonBuilding.matches("Andrew") && Keys.KEY_ROOM[i].substring(0, 1).matches("A")) ||
                                        (buttonBuilding.matches("LS") && Keys.KEY_ROOM[i].substring(0, 1).matches("L")) ||
                                        (buttonBuilding.matches("Others") && Keys.KEY_ROOM[i].substring(0, 1).matches("C")) ||
                                        (buttonBuilding.matches("Others") && Keys.KEY_ROOM[i].substring(0, 1).matches("J")) ||
                                        (buttonBuilding.matches("Others") && Keys.KEY_ROOM[i].substring(0, 1).matches("Y"))) {
                                    Log.v("entwined8", "ENTERED");
                                    model.setDay(Keys.KEY_ROOM[i]);
                                        for (jIndex = 0; jIndex < lenArray; jIndex++) {


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

                                            Calendar calendar = Calendar.getInstance();

//                                            Log.v("TAG", "TODAY is  " + day);



                                            if (selectedDay.matches("TODAY")) {

                                                String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
                                                selectedDay = today;
                                            }

                                            SimpleDateFormat sdf = new SimpleDateFormat("HHmm");

                                            long diffEnd = 99999, diffStart = 99999;

                                                SimpleDateFormat choice = new SimpleDateFormat("HHmm");

                                                try {
                                                    Date current = choice.parse(timeslot.substring(5));
                                                    Date selected = sdf.parse(endSelected);

                                                    diffEnd = selected.getTime() - current.getTime();

                                                    String out = current.toString();

//                                                    Log.v("Time", out);
//                                                    Log.v("Time", "Diff: " + diffEnd);

                                                    current = choice.parse(timeslot.substring(0,4));
                                                    selected = sdf.parse(startSelected);

                                                    diffStart = current.getTime() - selected.getTime();

                                                    out = current.toString();

//                                                    Log.v("Time", out);
//                                                    Log.v("Time", "Diff: " + diffEnd);
                                                } catch (ParseException e) {
                                                }

                                            Log.v("entwined8", "Day: " + selectedDay);


                                            if(selectedDay.matches(day) && diffEnd >= 0 && diffStart >= 0) {

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
                                            Log.v("entwined8", "Temp Text: " + tempText);

                                        }


                                    model.setTimeSlot(tempText);
                                    list.add(model);
                                    tempText = "";

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
            Calendar calendar = Calendar.getInstance();
            String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                if(today.matches("Sunday"))
                    Snackbar.make(findViewById(R.id.parentLayout), "Today is Sunday", Snackbar.LENGTH_LONG).show();
                else
                Snackbar.make(findViewById(android.R.id.content), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                URL url = new URL("https://script.google.com/a/dlsu.edu.ph/macros/s/AKfycbz8rE-gwrWDCeZwHlajr0gPMnNcUZWSaHEeYsE8CInDYq15bm94/exec");
                // https://script.google.com/macros/s/AKfycbyuAu6jWNYMiWt9X5yp63-hypxQPlg5JS8NimN6GEGmdKZcIFh0/exec
                JSONObject postDataParams = new JSONObject();

                //int i;
                //for(i=1;i<=70;i++)


                //    String usn = Integer.toString(i);

                String id= "1Hj-G94vl9LrEWNDs2u4Kqzfiiaezk86M-15bQQsX624";

                postDataParams.put("room",buttonBuilding);
                postDataParams.put("starttime",startSelected);
                postDataParams.put("endtime",endSelected);
                postDataParams.put("daytoday",selectedDay);



                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(getApplicationContext(), result,
//                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }


    private void initMenu(){

        Calendar calendar = Calendar.getInstance();
        String today = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

        for(int i = 0; i< Keys.KEY_WEEK.length; i++){

            if(today.matches(Keys.KEY_WEEK[i])){
                menu.addItem("TODAY",R.mipmap.ic_launcher, true);
            }
            else
                menu.addItem(Keys.KEY_WEEK[i],R.mipmap.ic_launcher);
        }

    }





}
