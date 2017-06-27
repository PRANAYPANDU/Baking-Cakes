package com.example.pranaykumar.bakingcakes;

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


/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

public class DetailsIngredientsFragment extends Fragment {
  View view;
  Bundle b;

  public interface OnStepSelectedInterface{
    void onListStepSelected(int index);
  }
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
   view=inflater.inflate(R.layout.fragment_details_ingredients,container,false);
    RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.steps_recyclerView);
    b=getArguments();
    OnStepSelectedInterface mListener= (OnStepSelectedInterface) getActivity();
    Recipe currentRecipe=b.getParcelable("recipe");
    StepsAdapter stepsAdapter=new StepsAdapter(mListener, currentRecipe.getmSteps());
    LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(stepsAdapter);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    final FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
    TextView ingredientsTextView= (TextView)view.findViewById(R.id.ingredients);
    ingredientsTextView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        IngredientsFragment ingredientsFragment=new IngredientsFragment();
        ingredientsFragment.setArguments(b);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeHolder,ingredientsFragment,RecipeDetailsActivity.INGREDIENTS_FRAGMENT).commit();
      }
    });
  }
}
