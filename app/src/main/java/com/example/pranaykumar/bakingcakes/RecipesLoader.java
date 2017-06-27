package com.example.pranaykumar.bakingcakes;

import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.ArrayList;


/**
 * Created by PRANAYKUMAR on 24-06-2017.
 */

public class RecipesLoader extends AsyncTaskLoader<ArrayList<Recipe>>{
private String mUrl;
  public RecipesLoader(Context context,String recipes_url) {
    super(context);
    mUrl=recipes_url;

  }

  @Override
  protected void onStartLoading() {
    forceLoad();
  }

  @Override
  public ArrayList<Recipe> loadInBackground() {
    if(mUrl==null)
      return null;
    ArrayList<Recipe> recipes=QueryUtils.fetchRecipes(mUrl);
    return recipes;
  }
}
