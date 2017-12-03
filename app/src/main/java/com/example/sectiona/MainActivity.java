package com.example.sectiona;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    Switch mySwitch;
    int secID;
    SharedPreferences spref;
    TextView tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySwitch = (Switch)findViewById(R.id.mySwitch);

        spref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        secID = spref.getInt("secID", 0);
        final SharedPreferences.Editor prefEditor = spref.edit();

        tt=(TextView)findViewById(R.id.ttt);

        setTT(secID);

        if(secID==0)
            mySwitch.setChecked(false);
        else
            mySwitch.setChecked(true);

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    prefEditor.putInt("secID", 1);
                    setTT(1);
                }else{
                    prefEditor.putInt("secID", 0);
                    setTT(0);
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

    String ttext;

    public void setTT(int chk){
        if (chk == 0) {
            ttext="";
            Scanner s = new Scanner(getResources().openRawResource(R.raw.bat1));
            s.useDelimiter("break");
            try {
                while (s.hasNext()) {
                    ttext+="<br><br>"+s.next();
                }
            } finally {
                s.close();
            }
        }
        else{
            ttext="";
            Scanner s = new Scanner(getResources().openRawResource(R.raw.bat2));
            s.useDelimiter("break");
            try {
                while (s.hasNext()) {
                    ttext+= "<br><br>" + s.next();
                }
            } finally {
                s.close();
            }
        }

        tt.setText(fromHtml(ttext));
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String text){
        Spanned res;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            res = Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY);}
        else{
            res = Html.fromHtml(text);
        }
        return res;
    }
}
