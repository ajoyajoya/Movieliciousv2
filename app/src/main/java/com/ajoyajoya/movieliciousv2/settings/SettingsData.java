package com.ajoyajoya.movieliciousv2.settings;

import com.ajoyajoya.movieliciousv2.R;

import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;

public class SettingsData extends AppCompatActivity {

    public static String[][] dataSettings = new String[][]{
            {"1", String.valueOf(R.string.settings_name), String.valueOf(R.string.settings_detail), String.valueOf(R.drawable.ic_language_24dp)},
            {"2", String.valueOf(R.string.settings_daily), String.valueOf(R.string.settings_daily_detail), String.valueOf(R.drawable.ic_notifications_none_white_24dp)},
            {"3", String.valueOf(R.string.settings_release), String.valueOf(R.string.settings_release_detail), String.valueOf(R.drawable.ic_movie_filter_white_24dp)},

    };

    public static ArrayList<SettingsItem> getListData(){
        ArrayList<SettingsItem> list = new ArrayList<>();
        for (String[] aData : dataSettings) {
            SettingsItem settingsItem = new SettingsItem();
            settingsItem.setIdsetting(aData[0]);
            settingsItem.setSettingname(aData[1]);
            settingsItem.setMenudesc(aData[2]);
            settingsItem.setIconsetting(Integer.parseInt(aData[3]));
            list.add(settingsItem);
        }
        return list;
    }

}
