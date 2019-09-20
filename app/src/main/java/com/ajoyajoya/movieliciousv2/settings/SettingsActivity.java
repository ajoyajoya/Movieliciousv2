package com.ajoyajoya.movieliciousv2.settings;

import android.content.Intent;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;

import com.ajoyajoya.movieliciousv2.R;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ArrayList<SettingsItem> list;
    private String[] settingsId;
    private String[] settingsName;
    private String[] settingsDesc;
    private String[] settingsSwitch;
    private TypedArray settingsIcon;
    private SettingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle(R.string.setting_text);
        }

        adapter = new SettingsAdapter(this);

        ListView listView = findViewById(R.id.lv_settings_menu);
        listView.setAdapter(adapter);

        prepare();
        addItem();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(SettingsActivity.this, list.get(i).getIdsetting(), Toast.LENGTH_SHORT).show();
                switch (list.get(i).getIdsetting()){
                    case "1":
                        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                        startActivity(mIntent);
                        break;

                    case "2":
                        final Switch dailySwitch = view.findViewById(R.id.switch_setting);
                        final Boolean switchState = dailySwitch.isChecked();
                        if (switchState.equals(false)){
                            dailySwitch.setChecked(true);
                            //Toast.makeText(SettingsActivity.this, "Success Daily Switch On", Toast.LENGTH_SHORT).show();
                        } else {
                            dailySwitch.setChecked(false);
                            //Toast.makeText(SettingsActivity.this, "Success Daily Switch Off", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case "3":
                        final Switch releaseSwitch = view.findViewById(R.id.switch_setting);
                        final Boolean releaseSwitchState = releaseSwitch.isChecked();
                        if (releaseSwitchState.equals(false)){
                            releaseSwitch.setChecked(true);
                            //Toast.makeText(SettingsActivity.this, "Success Release Switch On", Toast.LENGTH_SHORT).show();
                        } else {
                            releaseSwitch.setChecked(false);
                            //Toast.makeText(SettingsActivity.this, "Success Release Switch Off", Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

            }
        });


    }

    private void prepare() {
        settingsId = getResources().getStringArray(R.array.id_settings);
        settingsName = getResources().getStringArray(R.array.name_settings);
        settingsDesc = getResources().getStringArray(R.array.detail_settings);
        settingsIcon = getResources().obtainTypedArray(R.array.icon_settings);
        settingsSwitch = getResources().getStringArray(R.array.switch_settings);
    }

    private void addItem() {
        list = new ArrayList<>();

        for (int i = 0; i < settingsId.length; i++) {
            SettingsItem settingsItem = new SettingsItem();
            settingsItem.setIdsetting(settingsId[i]);
            settingsItem.setSettingname(settingsName[i]);
            settingsItem.setMenudesc(settingsDesc[i]);
            settingsItem.setIconsetting(settingsIcon.getResourceId(i, -1));
            settingsItem.setSwitchsetting(settingsSwitch[i]);
            list.add(settingsItem);
        }

        adapter.setSettings(list);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
