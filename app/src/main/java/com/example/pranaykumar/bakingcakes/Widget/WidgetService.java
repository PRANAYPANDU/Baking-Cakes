package com.example.pranaykumar.bakingcakes.Widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by PRANAYKUMAR on 30-06-2017.
 */

public class WidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new WidgetAdapter(this);
  }
}
