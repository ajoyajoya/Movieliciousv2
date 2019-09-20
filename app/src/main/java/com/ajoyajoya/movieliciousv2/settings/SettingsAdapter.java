package com.ajoyajoya.movieliciousv2.settings;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ajoyajoya.movieliciousv2.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class SettingsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SettingsItem> settingsItems;
    private DailyNotif dailyNotif;
    private GetReleaseMovie getReleaseMovie;
    private int jobId = 10;

    public void setSettings(ArrayList<SettingsItem> settingsItems) {
        this.settingsItems = settingsItems;
    }

    public SettingsAdapter(Context context) {
        this.context = context;
        settingsItems = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return settingsItems.size();
    }

    @Override
    public Object getItem(int position) {
        return settingsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_settings, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        SettingsItem settingsItem = (SettingsItem) getItem(position);
        viewHolder.bind(settingsItem);
        return convertView;
    }

    private class ViewHolder {
        private TextView txtSettingsName;
        private TextView txtSettingsDescription;
        private ImageView imgSettingsIcon;
        private Switch switchSettings;
        ViewHolder(View view) {
            txtSettingsName = view.findViewById(R.id.tv_item_name);
            txtSettingsDescription = view.findViewById(R.id.tv_item_from);
            imgSettingsIcon = view.findViewById(R.id.img_item_icon);
            switchSettings = view.findViewById(R.id.switch_setting);
        }
        void bind(final SettingsItem settingsItem) {
            txtSettingsName.setText(settingsItem.getSettingname());
            txtSettingsDescription.setText(settingsItem.getMenudesc());
            imgSettingsIcon.setImageResource(settingsItem.getIconsetting());
            String setVisibleSwitch = settingsItem.getSwitchsetting();
            if(setVisibleSwitch.equals("GONE")) {
                switchSettings.setVisibility(View.INVISIBLE);
            } else {
                switchSettings.setVisibility(View.VISIBLE);
            }

            Intent intent = new Intent(context, DailyNotif.class);
            int requestCode = 101;
            boolean isWorking = (PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) !=null);

            Intent intentNew = new Intent(context, GetReleaseMovie.class);
            int requestCodeRelease = 101;
            boolean isWorkingRelease = (PendingIntent.getBroadcast(context, requestCodeRelease, intentNew, PendingIntent.FLAG_NO_CREATE) !=null);

            Log.d(TAG, "alarm is " + (isWorking ? "" : "not") + " working...");
            Log.d(TAG, "alarm is Release" + (isWorkingRelease ? "" : "not") + " working...");
            switch (settingsItem.getIdsetting()){
                case "2":
                    if (isWorking){
                        switchSettings.setChecked(true);
                    } else {
                        switchSettings.setChecked(false);
                    }
                    break;
                case "3":
                    if (isWorkingRelease){
                        switchSettings.setChecked(true);
                    } else {
                        switchSettings.setChecked(false);
                    }
                    break;
            }

            switchSettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //you can modify Action here.
                    switch (settingsItem.getIdsetting()){
                        case "2":
                            dailyNotif = new DailyNotif();
                            if (isChecked==true){
                                //Toast.makeText(context, "Success Daily Adapter Switch On", Toast.LENGTH_SHORT).show();

                                String repeatTime = "07:00";
                                String repeatMessage = context.getString(R.string.notif_message);
                                dailyNotif.setRepeatingNotif(context, DailyNotif.TYPE_REPEATING,
                                        repeatTime, repeatMessage);
                                //Toast.makeText(context, context.getString(R.string.success_daily_notif)+" / "+context.getString(R.string.notif_message), Toast.LENGTH_SHORT).show();
                                //break;
                            } else {
                                dailyNotif.cancelAlarm(context, DailyNotif.TYPE_REPEATING);
                                //Toast.makeText(context, "Success Daily Adapter Switch Off", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "3":
                            getReleaseMovie = new GetReleaseMovie();
                            if (isChecked==true){
                                String repeatTime = "08:00";
                                String repeatMessage = context.getString(R.string.release_today);
                                getReleaseMovie.setReleaseNotif(context, GetReleaseMovie.TYPE_REPEATING,
                                        repeatTime, repeatMessage);
                            } else {
                                getReleaseMovie.cancelAlarm(context, GetReleaseMovie.TYPE_REPEATING);

                            }
                            break;
                    }


                }
            });

        }


    }
}
