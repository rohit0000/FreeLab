package com.example.rohit.freelab2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Buildings extends AppCompatActivity {

    Button goks, vel, ls, andrew, others;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    @Override
    protected void onStart() {
        super.onStart();

        goks = (Button) findViewById(R.id.buttonGokongwei);
        vel = (Button) findViewById(R.id.buttonVelasco);
        ls  = (Button) findViewById(R.id.buttonLS);
        andrew = (Button) findViewById(R.id.buttonAndrew);
        others = (Button) findViewById(R.id.buttonOthers);

        goks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBuildingSelect = new Intent(Buildings.this, BuildingSelect.class);
                toBuildingSelect.putExtra("Title", "Gokongwei");

                startActivity(toBuildingSelect);
            }
        });

        vel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent toBuildingSelect = new Intent(Buildings.this, BuildingSelect.class);
                toBuildingSelect.putExtra("Title", "Velasco");

                startActivity(toBuildingSelect);

            }
        });

        ls.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent toBuildingSelect = new Intent(Buildings.this, BuildingSelect.class);
                toBuildingSelect.putExtra("Title", "LS");

                startActivity(toBuildingSelect);

            }
        });

        andrew.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent toBuildingSelect = new Intent(Buildings.this, BuildingSelect.class);
                toBuildingSelect.putExtra("Title", "Andrew");

                startActivity(toBuildingSelect);

            }
        });

        others.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent toBuildingSelect = new Intent(Buildings.this, BuildingSelect.class);
                toBuildingSelect.putExtra("Title", "Others");

                startActivity(toBuildingSelect);

            }
        });
    }

}
