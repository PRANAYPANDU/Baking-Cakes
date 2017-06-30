package com.example.pranaykumar.bakingcakes.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import com.example.pranaykumar.bakingcakes.R;

/**
 * Created by PRANAYKUMAR on 30-06-2017.
 */

public class WidgetProvider extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    int[] realAppWidgetIds =AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, WidgetProvider.class));

    for (int widgetId : realAppWidgetIds) {
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

      Intent serviceIntent = new Intent(context, WidgetService.class);
      remoteViews.setRemoteAdapter(R.id.listView, serviceIntent);

      Intent intent = new Intent(context, WidgetProvider.class);
      intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, realAppWidgetIds);
      PendingIntent pendingIntent = PendingIntent.getBroadcast(
          context,
          0,
          intent,
          PendingIntent.FLAG_UPDATE_CURRENT
      );
      remoteViews.setOnClickPendingIntent(R.id.frameLayout, pendingIntent);
      SharedPreferences prefs = context.getSharedPreferences(WidgetAdapter.PREFS_NAME,0);
      remoteViews.setTextViewText(R.id.recipeLastVisited,prefs.getString("LastVisitedRecipeName","Nutella Pie"));
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
  }
}
