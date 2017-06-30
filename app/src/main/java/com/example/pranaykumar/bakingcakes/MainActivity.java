package com.example.pranaykumar.bakingcakes;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{

  private static final int RECIPES_LOADER_ID =1;
  @BindView(R.id.recipes_recyclerView)RecyclerView mRecyclerView;
  @BindView(R.id.loading_indicator)
  ProgressBar mLoadingIndicator;
  @BindView(R.id.empty_view)TextView mEmptyView;

  ArrayList<Recipe> recipes;
  private RecipesAdapter mRecipesAdapter;

  private static final String RECIPES_URL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    recipes=new ArrayList<Recipe>();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
    GridLayoutManager gridLayoutManager;
    if(!isTablet){
      gridLayoutManager=new GridLayoutManager(this,1);
    }
    else{
          gridLayoutManager=new GridLayoutManager(this,3);
    }

    mRecyclerView.setLayoutManager(gridLayoutManager);
    mRecyclerView.setHasFixedSize(true);
    mRecipesAdapter=new RecipesAdapter(recipes);
    mRecyclerView.setAdapter(mRecipesAdapter);

    ConnectivityManager connMgr=(ConnectivityManager)getSystemService(
        Context.CONNECTIVITY_SERVICE);

    NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();
    if(networkInfo!=null&&networkInfo.isConnected()){
      LoaderManager loaderManager=getLoaderManager();
      loaderManager.initLoader(RECIPES_LOADER_ID,null,this);
    }
    else{
    mLoadingIndicator.setVisibility(View.GONE);

    mEmptyView.setText(R.string.no_internet_connection);
    }
  }

  @Override
  public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
    return new RecipesLoader(this,RECIPES_URL);
  }

  @Override
  public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
      mLoadingIndicator.setVisibility(View.GONE);
    // Set empty state text to display "No Movies found."
      mEmptyView.setText(R.string.no_recipes);
    if(data!=null&&!data.isEmpty()){
      mRecipesAdapter.setmRecipesData(data);
      mEmptyView.setVisibility(View.GONE);
    }

  }

  @Override
  public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

  }
}
