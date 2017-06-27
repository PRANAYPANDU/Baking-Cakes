package com.example.pranaykumar.bakingcakes;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;


public class RecipeDetailsActivity extends AppCompatActivity implements DetailsIngredientsFragment.OnStepSelectedInterface {
  private static final String DETAILS_INGREDIENTS_FRAGMENT = "method_fragment";
  public static final String INGREDIENTS_FRAGMENT ="ingredients_fragment";
  private static final String STEPS_VIDEO_DESCRIPTION_FRAGMENT ="steps_video_details_fragment" ;
  Bundle b;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details);

    DetailsIngredientsFragment detailsIngredientsFragment =new DetailsIngredientsFragment();
    Intent intent=getIntent();
    b=intent.getExtras();
    detailsIngredientsFragment.setArguments(b);
    final FragmentManager fragmentManager=getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .add(R.id.placeHolder, detailsIngredientsFragment, DETAILS_INGREDIENTS_FRAGMENT)
        .commit();
  }

  @Override
  public void onListStepSelected(int index) {
    StepVideoDescriptionFragment videoFragment=new StepVideoDescriptionFragment();
    Recipe currentRecipe=b.getParcelable("recipe");
    ArrayList<String> currentStep=currentRecipe.getmSteps().get(index);
    Bundle bundle=new Bundle();
    bundle.putStringArrayList("step",currentStep);
    videoFragment.setArguments(bundle);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.placeHolder,videoFragment,STEPS_VIDEO_DESCRIPTION_FRAGMENT)
        .commit();
  }
}
