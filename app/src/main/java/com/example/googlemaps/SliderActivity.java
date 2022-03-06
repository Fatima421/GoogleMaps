package com.example.googlemaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import java.util.ArrayList;

public class SliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        // Bundle
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> imageUrl = (ArrayList<String>) bundle.getSerializable("imageUrl");

        // Properties
        ViewPager mPager = findViewById(R.id.vpager);

        if(imageUrl!=null) {
            mPager.setAdapter(new SliderAdapter(SliderActivity.this, imageUrl));
        }
    }
}