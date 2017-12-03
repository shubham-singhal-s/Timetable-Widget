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
import android.text.Spanned;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by mahe on 19-07-2017.
 */

public class MyAppWidgetProvider extends AppWidgetProvider {

    String[] sArr = {"", "", "", "", "", "", ""};

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        Log.i("ExampleWidget", "Updating widgets " + Arrays.asList(appWidgetIds));

        // Perform this loop procedure for each App Widget that belongs to this
        // provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_main);
            Intent intent = new Intent(context, MyAppWidgetProvider.class);
            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 16) {
                day++;
                if (day > 7) day = 1;
            }

            Intent intent2 = new Intent(context, MainActivity.class);
            intent.setAction("android.appwidget.action.APPWIDGET_OPEN");
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 1, intent2, 0);

            SharedPreferences spref = PreferenceManager.getDefaultSharedPreferences(context);
            int secID = spref.getInt("secID", 0);

            if (secID == 0) textGet(R.raw.bat1, context);
            else textGet(R.raw.bat2, context);

            String head;
            if (secID == 0)
                head = "<h4>Batch 1</h4>";
            else head = "<h4>Batch 2</h4>";
            // To update a label
            views.setTextViewText(R.id.widgetlabel, fromHtml(head + sArr[day - 1]));
            views.setOnClickPendingIntent(R.id.imgref, pendingIntent);
            views.setOnClickPendingIntent(R.id.widgetlabel, pendingIntent2);
            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        super.onReceive(context, intent);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisAppWidget = new ComponentName(context.getPackageName(), MyAppWidgetProvider.class.getName());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
        onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String text) {
        Spanned res;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            res = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else
            res = Html.fromHtml(text);
        return res;}

    public void textGet(int id, Context context) {
        Scanner s = new Scanner(context.getResources().openRawResource(id));
        int cnt = 0;
        s.useDelimiter("break");
        try {
            while (s.hasNext())
                sArr[cnt++] = s.next();
        } finally {
            s.close();
        }
    }
}