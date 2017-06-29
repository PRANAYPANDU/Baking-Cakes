package com.example.pranaykumar.bakingcakes;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.pranaykumar.bakingcakes.DetailsIngredientsFragment.OnStepSelectedInterface;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder>{
  Context context;
  private final DetailsIngredientsFragment.OnStepSelectedInterface mListener;
  private ArrayList<ArrayList<String>> mStepsData;
  public StepsAdapter(OnStepSelectedInterface mListener, ArrayList<ArrayList<String>> arrayLists) {
    this.mListener = mListener;
    mStepsData=arrayLists;


  }

  @Override
  public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context=parent.getContext();
    int layoutForListItem=R.layout.list_item_step_details;
    LayoutInflater inflater=LayoutInflater.from(context);
    View view=inflater.inflate(layoutForListItem,parent,false);

    return new StepsAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(StepsAdapterViewHolder holder, final int position) {
    final ArrayList<String> currentStep=mStepsData.get(position);
    holder.mStepDesc.setText(currentStep.get(0));
    holder.mStepDesc.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mListener.onListStepSelected(position,mStepsData);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mStepsData.size();
  }

  public class StepsAdapterViewHolder extends ViewHolder{
    public final TextView mStepDesc;
    public final LinearLayout mLayout;
    public StepsAdapterViewHolder(View itemView) {
      super(itemView);
      mStepDesc=(TextView)itemView.findViewById(R.id.step_desc);
      mLayout=(LinearLayout)itemView.findViewById(R.id.stepLayout);
    }

  }
}
