package com.example.memorableplace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ListView listView;
    public double latitude;
    public double longitude;
    static ArrayList<String> places=new ArrayList<>();
    static ArrayList<LatLng> locations=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lv);

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.memorableplace", Context.MODE_PRIVATE);
        ArrayList<String> lattitude=new ArrayList<>();
        ArrayList<String> longitude=new ArrayList<>();

        places.clear();
        lattitude.clear();
        longitude.clear();
        locations.clear();
        try {

            places=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places",ObjectSerializer.serialize(new ArrayList<>())));
            sharedPreferences.edit().putString("latitude",ObjectSerializer.serialize(lattitude)).apply();
            sharedPreferences.edit().putString("places",ObjectSerializer.serialize(longitude)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(places.size()>0 && lattitude.size()>0 && longitude.size()>0){
            if(places.size()==lattitude.size() && lattitude.size()==longitude.size());
            {
                for(int i=0;i<lattitude.size();i++){
                    locations.add(new LatLng(Double.parseDouble(lattitude.get(i)),Double.parseDouble(longitude.get(i))));

                }
            }
        }else {

            places.add("add an new place");
            locations.add(new LatLng(0, 0));
        }
        arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,places);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(MainActivity.this, position, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
                intent.putExtra("placenumber",position);
                startActivity(intent);
            }
        });

    }
}
