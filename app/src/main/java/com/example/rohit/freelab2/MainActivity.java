package com.example.rohit.freelab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button today, search, building;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         today = (Button)findViewById(R.id.button);
        building= (Button) findViewById(R.id.building);
        search = (Button)findViewById(R.id.search);


    }

    @Override
    protected void onStart() {
        super.onStart();
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toToday= new Intent(MainActivity.this, Main3Activity.class);
                startActivity(toToday);
            }
        });

        building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toBuilding= new Intent(MainActivity.this, Buildings.class);
                startActivity(toBuilding);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toSearch= new Intent(MainActivity.this, Search.class);
                startActivity(toSearch);

            }
        });
    }



    }
