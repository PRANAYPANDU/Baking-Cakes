package com.example.pranaykumar.bakingcakes.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.pranaykumar.bakingcakes.R;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 30-06-2017.
 */

public class WidgetAdapter implements RemoteViewsService.RemoteViewsFactory {

  public static final String PREFS_NAME ="LastVisitedRecipeIngredients" ;
  Context context;
  String[] list;
  public WidgetAdapter(Context context) {
    this.context=context;
  }

  @Override
  public void onCreate() {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
    String ingredients="";
    String initial="1.Graham Cracker crumbs:2 CUP`2.unsalted butter, melted:6 TBLSP`3.granulated sugar:0.5 CUP`4.salt:1.5 TSP`5.vanilla:5 TBLSP`6.Nutella or other chocolate-hazelnut spread:1 K`7.Mascapone Cheese(room temperature):500 G`8.heavy cream(cold):1 CUP`9.cream cheese(softened):4 OZ`";
    ingredients=prefs.getString("Ingredients",initial);
    Log.d("O_MY","Ingredients text is::"+ingredients);
    list = ingredients.split("`");
  }

  @Override
  public void onDataSetChanged() {

  }

  @Override
  public void onDestroy() {

  }

  @Override
  public int getCount() {
    return list.length;
  }

  @Override
  public RemoteViews getViewAt(int position) {
    RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.list_item);
    remoteViews.setTextViewText(R.id.textView,list[position]);
    return remoteViews;
  }

  @Override
  public RemoteViews getLoadingView() {
    return null;
  }

  @Override
  public int getViewTypeCount() {
    return 1;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }
}
