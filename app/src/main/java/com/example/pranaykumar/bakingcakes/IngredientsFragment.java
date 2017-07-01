package com.example.pranaykumar.bakingcakes;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.ArrayList;

/**
 * Created by PRANAYKUMAR on 27-06-2017.
 */

public class IngredientsFragment extends Fragment {
  @BindView(R.id.ingredientsTextView) TextView txtView;
  Unbinder unbinder;
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_ingredients,container,false);
    unbinder= ButterKnife.bind(this,view);
    Bundle b=getArguments();
    Recipe currentRecipe=b.getParcelable(getString(R.string.Recipe));
    String textViewText="";
    ArrayList<ArrayList<String>> currentIngredients=currentRecipe.getmIngredients();
    int i=0;
    for(ArrayList<String> ingredient:currentIngredients){
      ingredient=currentIngredients.get(i);
      textViewText=textViewText+String.valueOf(i+1)+"."+ingredient.get(2)+":"+ingredient.get(0)+" "+ingredient.get(1)+"\n";
    i++;
    }
    txtView.setText(textViewText);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
