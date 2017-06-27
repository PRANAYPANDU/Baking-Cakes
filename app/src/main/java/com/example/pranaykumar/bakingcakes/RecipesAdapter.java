package com.example.pranaykumar.bakingcakes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by PRANAYKUMAR on 24-06-2017.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

  private ArrayList<Recipe> mRecipesData;
  private Context context;

  public RecipesAdapter(ArrayList<Recipe> recipes) {
    mRecipesData=recipes;
  }

  @Override
  public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    int layoutForGridItem=R.layout.grid_item;
    context=parent.getContext();
    LayoutInflater inflater=LayoutInflater.from(context);

    View view=inflater.inflate(layoutForGridItem,parent,false);
    return new RecipesAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecipesAdapterViewHolder holder, final int position) {
    holder.mRecipeName.setText(mRecipesData.get(position).getmRecipeName());
    holder.mLayout.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(context,RecipeDetailsActivity.class);
        intent.putExtra("recipe",mRecipesData.get(position));
        context.startActivity(intent);
      }
    });
  }
  @Override
  public int getItemCount() {
    if(null==mRecipesData)return 0;
    return mRecipesData.size();
  }

  public void setmRecipesData(ArrayList<Recipe> mRecipesData) {
    this.mRecipesData = mRecipesData;
    notifyDataSetChanged();
  }

  public class RecipesAdapterViewHolder extends ViewHolder {

    public final TextView mRecipeName;
    public final LinearLayout mLayout;
    public RecipesAdapterViewHolder(View itemView) {
      super(itemView);
      mRecipeName=(TextView)itemView.findViewById(R.id.recipe_name);
      mLayout=(LinearLayout)itemView.findViewById(R.id.layout);
    }
  }
}
