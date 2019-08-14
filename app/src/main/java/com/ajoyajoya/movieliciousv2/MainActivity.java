package com.ajoyajoya.movieliciousv2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ajoyajoya.movieliciousv2.favorite.FavoriteActivity;
import com.ajoyajoya.movieliciousv2.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_main, fab1_favorite, fab2_language;
    private Animation fab_open;
    private Animation fab_close;
    private TextView textview_favorite;
    private TextView textview_language;

    private Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        fab_main = findViewById(R.id.fab);
        fab1_favorite = findViewById(R.id.fab1);
        fab2_language = findViewById(R.id.fab2);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        Animation fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        Animation fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_favorite = findViewById(R.id.textview_favorite);
        textview_language = findViewById(R.id.textview_language);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {

                    textview_favorite.setVisibility(View.INVISIBLE);
                    textview_language.setVisibility(View.INVISIBLE);
                    fab2_language.startAnimation(fab_close);
                    fab1_favorite.startAnimation(fab_close);
                    fab2_language.setClickable(false);
                    fab1_favorite.setClickable(false);
                    isOpen = false;
                    fab_main.setImageResource(R.drawable.ic_menu_white_24dp);

                } else {
                    textview_favorite.setVisibility(View.VISIBLE);
                    textview_language.setVisibility(View.VISIBLE);
                    fab2_language.startAnimation(fab_open);
                    fab1_favorite.startAnimation(fab_open);
                    fab2_language.setClickable(true);
                    fab1_favorite.setClickable(true);
                    isOpen = true;
                    fab_main.setImageResource(R.drawable.ic_close_black_24dp);
                }

            }
        });


        fab2_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                textview_favorite.setVisibility(View.INVISIBLE);
                textview_language.setVisibility(View.INVISIBLE);
                fab2_language.startAnimation(fab_close);
                fab1_favorite.startAnimation(fab_close);
                fab2_language.setClickable(false);
                fab1_favorite.setClickable(false);
                isOpen = false;
                fab_main.setImageResource(R.drawable.ic_menu_white_24dp);

            }
        });

        fab1_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent moveIntent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(moveIntent);

                textview_favorite.setVisibility(View.INVISIBLE);
                textview_language.setVisibility(View.INVISIBLE);
                fab2_language.startAnimation(fab_close);
                fab1_favorite.startAnimation(fab_close);
                fab2_language.setClickable(false);
                fab1_favorite.setClickable(false);
                isOpen = false;
                fab_main.setImageResource(R.drawable.ic_menu_white_24dp);

            }
        });




    }



}