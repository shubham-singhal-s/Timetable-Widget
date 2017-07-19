package com.example.sectiona;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by mahe on 19-07-2017.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    public static String WIDGET_BUTTON = "android.appwidget.action.APPWIDGET_UPDATE";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        Log.i("ExampleWidget",  "Updating widgets " + Arrays.asList(appWidgetIds));

        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity


            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            Intent intent = new Intent(context, MyAppWidgetProvider.class);
            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            //appWidgetManager.updateAppWidget(appWidgetId, views);


            String text="nullday";

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(context);
            int secID = spref.getInt("secID", 0);


            if(secID==0) {
                switch (day) {

                    case Calendar.MONDAY:
                        // Current day is Monday
                        text = "<h4>A1</h4>Monday<br><br><b>08:00</b> - Operating System<br><b>09:00</b> - Computer Networks<br><b>10:30</b> - Software Engineering<br><b>11:30</b> - Computer Architecture";
                        break;

                    case Calendar.TUESDAY:
                        // Current day is Monday
                        text = "<h4>A1</h4>Tuesday<br><br><b>01:00</b> - Program Elective<br><b>02:00</b> - Operating System<br><b>03:30</b> - Computer Networks";
                        break;

                    case Calendar.WEDNESDAY:
                        // Current day is Monday
                        text = "<h4>A1</h4>Wednesday<br><br><b>08:00</b> - Software Engineering<br><b>09:00</b> - Comp Architecture<br><b>10:30</b> - Program Elective<br><br><b>02:00</b> - OS Lab (04)";
                        break;

                    case Calendar.THURSDAY:
                        // Current day is Monday
                        text = "<h4>A1</h4>Thursday<br><br><b>08:30</b> - AL Lab (04)<br><br><b>01:00</b> - Operating System<br><b>02:00</b> - Computer Networks<br><b>03:30</b> - Software Engineering<br><b>04:30</b> - Computer Architecture";
                        break;

                    case Calendar.FRIDAY:
                        // Current day is Monday
                        text = "<h4>A1</h4>Friday<br><br><b>08:00</b> - Program Elective<br><b>09:00</b> - Software Engineering<b>*</b><br><b>10:30</b> - Operating Systems<br><b>11:30</b> - Computer Network";
                        break;

                    case Calendar.SATURDAY:
                        // Current day is Monday
                        text = "<h4>A1</h4>Saturday<br><br><b>08:30</b> - CN Lab (02)";
                        break;

                    case Calendar.SUNDAY:
                        // Current day is Monday
                        text = "<h2>Sunday!!!</h2>";
                        break;

                }
            }
            else {

                switch (day) {

                    case Calendar.MONDAY:
                        // Current day is Monday
                        text = "<h4>A2</h4>Monday<br><br><b>08:00</b> - Operating System<br><b>09:00</b> - Computer Networks<br><b>10:30</b> - Software Engineering<br><b>11:30</b> - Computer Architecture<br><br><b>02:00</b> - OS Lab (04)";
                        break;

                    case Calendar.TUESDAY:
                        // Current day is Monday
                        text = "<h4>A2</h4>Tuesday<br><br><b>01:00</b> - Program Elective<br><b>02:00</b> - Operating System<br><b>03:30</b> - Computer Networks";
                        break;

                    case Calendar.WEDNESDAY:
                        // Current day is Monday
                        text = "<h4>A2</h4>Wednesday<br><br><b>08:00</b> - Software Engineering<br><b>09:00</b> - Comp Architecture<br><b>10:30</b> - Program Elective<br><br><b>02:00</b> - CN Lab (02)";
                        break;

                    case Calendar.THURSDAY:
                        // Current day is Monday
                        text = "<h4>A2</h4>Thursday<br><br><b>01:00</b> - Operating System<br><b>02:00</b> - Computer Networks<br><b>03:30</b> - Software Engineering<br><b>04:30</b> - Computer Architecture";
                        break;

                    case Calendar.FRIDAY:
                        // Current day is Monday
                        text = "<h4>A2</h4>Friday<br><br><b>08:00</b> - Program Elective<br><b>09:00</b> - Software Engineering<b>*</b><br><b>10:30</b> - Operating Systems<br><b>11:30</b> - Computer Network";
                        break;

                    case Calendar.SATURDAY:
                        // Current day is Monday
                        text = "<h4>A2</h4>Saturday<br><br><b>11:00</b> - AL Lab (04)";
                        break;

                    case Calendar.SUNDAY:
                        // Current day is Monday
                        text = "<h2>Sunday!!!</h2>";
                        break;

                }
            }

            // To update a label
            views.setTextViewText(R.id.widget1label, Html.fromHtml(text));
            views.setOnClickPendingIntent(R.id.imgref, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app
            // widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);

        //Bundle extras = intent.getExtras();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), MyAppWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);

    }


}