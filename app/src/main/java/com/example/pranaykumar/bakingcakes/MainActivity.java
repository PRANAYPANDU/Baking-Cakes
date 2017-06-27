package com.example.pranaykumar.bakingcakes;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import com.example.pranaykumar.bakingcakes.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{

  private static final int RECIPES_LOADER_ID =1;
  ActivityMainBinding mainBinding;

  ArrayList<Recipe> recipes;
  private RecipesAdapter mRecipesAdapter;

  private static final String RECIPES_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    recipes=new ArrayList<Recipe>();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

    GridLayoutManager gridLayoutManager
        =new GridLayoutManager(this,1);

    mainBinding.recipesRecyclerView.setLayoutManager(gridLayoutManager);
    mainBinding.recipesRecyclerView.setHasFixedSize(true);
    mRecipesAdapter=new RecipesAdapter(recipes);
    mainBinding.recipesRecyclerView.setAdapter(mRecipesAdapter);

    ConnectivityManager connMgr=(ConnectivityManager)getSystemService(
        Context.CONNECTIVITY_SERVICE);

    NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
    if(networkInfo!=null&&networkInfo.isConnected()){
      LoaderManager loaderManager=getLoaderManager();

      loaderManager.initLoader(RECIPES_LOADER_ID,null,this);
    }
  }

  @Override
  public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
    return new RecipesLoader(this,RECIPES_URL);
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
    if(data!=null&&!data.isEmpty()){

      mRecipesAdapter.setmRecipesData(data);
    }

  }

  @Override
  public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

  }
}
