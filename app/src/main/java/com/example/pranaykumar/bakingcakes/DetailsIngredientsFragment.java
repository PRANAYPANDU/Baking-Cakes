package com.example.pranaykumar.bakingcakes;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

public class DetailsIngredientsFragment extends Fragment {
  View view;
  Bundle b;
Context context;
  public interface OnStepSelectedInterface{
    void onListStepSelected(int index, ArrayList<ArrayList<String>> mStepsData);
    void onIngredientsSelected(Bundle b);
  }
  DetailsIngredientsFragment.OnStepSelectedInterface mListener;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
   view=inflater.inflate(R.layout.fragment_details_ingredients,container,false);
    context=getContext();
    RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.steps_recyclerView);
    b=getArguments();
    OnStepSelectedInterface mListener= (OnStepSelectedInterface) getActivity();
    Recipe currentRecipe=b.getParcelable(getString(R.string.Recipe));
    getActivity().setTitle(currentRecipe.getmRecipeName());
    StepsAdapter stepsAdapter=new StepsAdapter(mListener, currentRecipe.getmSteps());
    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());

    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(stepsAdapter);

    if (container != null) {
      container.removeAllViews();
    }
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mListener= (OnStepSelectedInterface) getActivity();
    TextView ingredientsTextView= (TextView)view.findViewById(R.id.ingredients);
    ingredientsTextView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mListener.onIngredientsSelected(b);
      }
    });
  }
}
