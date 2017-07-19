package com.example.sectiona;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    Switch mySwitch;
    SharedPreferences spref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySwitch = (Switch)findViewById(R.id.mySwitch);

        spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int secID = spref.getInt("secID", 0);
        final SharedPreferences.Editor prefEditor = spref.edit();

        if(secID==0)
            mySwitch.setChecked(false);
        else
            mySwitch.setChecked(true);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    prefEditor.putInt("secID", 1);
                }else{
                    prefEditor.putInt("secID", 0);
                }

                prefEditor.apply();

                Intent intent = new Intent(MainActivity.this, MyAppWidgetProvider.class);
                intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MyAppWidgetProvider.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                sendBroadcast(intent);

            }
        });

        findViewById(R.id.man_ref).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyAppWidgetProvider.class);
                intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
                int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MyAppWidgetProvider.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                sendBroadcast(intent);
            }
        });

    }


}
