package com.example.pranaykumar.bakingcakes;

import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;


public class RecipeDetailsActivity extends AppCompatActivity implements
    DetailsIngredientsFragment.OnStepSelectedInterface,
    StepVideoDescriptionFragment.OnButtonClickedInterface {

  private static final String DETAILS_INGREDIENTS_FRAGMENT = "method_fragment";
  public static final String INGREDIENTS_FRAGMENT = "ingredients_fragment";
  private static final String STEPS_VIDEO_DESCRIPTION_FRAGMENT = "steps_video_details_fragment";
  private ArrayList<ArrayList<String>> StepsAll;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details);
    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
    Intent intent = getIntent();
    Bundle b;
    b = intent.getExtras();
    if (!isTablet) {
      if (savedInstanceState == null) {
        DetailsIngredientsFragment detailsIngredientsFragment = new DetailsIngredientsFragment();

        detailsIngredientsFragment.setArguments(b);
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager()
            .beginTransaction();
        fragmentTransaction
            .add(android.R.id.content, detailsIngredientsFragment, DETAILS_INGREDIENTS_FRAGMENT);
        fragmentTransaction.commit();
      }
    } else {
      if (savedInstanceState == null) {
        DetailsIngredientsFragment detailsIngredientsFragment = new DetailsIngredientsFragment();
        detailsIngredientsFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
            .add(R.id.listlayoutFragment, detailsIngredientsFragment, DETAILS_INGREDIENTS_FRAGMENT)
            .commit();

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        ingredientsFragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
            .add(R.id.videoPlayerFragment, ingredientsFragment, INGREDIENTS_FRAGMENT)
            .commit();

      }
    }
  }

  @Override
  public void onListStepSelected(int index, ArrayList<ArrayList<String>> mStepsData) {
    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);

    Bundle allSteps = new Bundle();
    StepsAll = mStepsData;
    for (int i = 0; i < mStepsData.size(); i++) {
      allSteps.putStringArrayList(String.valueOf(i), mStepsData.get(i));
    }
    allSteps.putInt("position", index);
    allSteps.putInt("position", index);
    StepVideoDescriptionFragment videoFragment = new StepVideoDescriptionFragment();
    videoFragment.setArguments(allSteps);
    if (!isTablet) {

      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(android.R.id.content, videoFragment, STEPS_VIDEO_DESCRIPTION_FRAGMENT);
      transaction.addToBackStack("ListSteps");
      transaction.commit();
    } else {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction
          .replace(R.id.videoPlayerFragment, videoFragment, STEPS_VIDEO_DESCRIPTION_FRAGMENT);
      transaction.commit();

    }
  }

  @Override
  public void onIngredientsSelected(Bundle b) {
    boolean isTablet = getResources().getBoolean(R.bool.is_tablet);

    final FragmentManager fragmentManager = getSupportFragmentManager();
    IngredientsFragment ingredientsFragment = new IngredientsFragment();
    ingredientsFragment.setArguments(b);
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    if (!isTablet) {
      fragmentTransaction.replace(android.R.id.content, ingredientsFragment,
          RecipeDetailsActivity.INGREDIENTS_FRAGMENT);
      fragmentTransaction.addToBackStack("BackToStepsList");
      fragmentTransaction.commit();
    } else {
      fragmentTransaction.replace(R.id.videoPlayerFragment, ingredientsFragment,
          RecipeDetailsActivity.INGREDIENTS_FRAGMENT);
      fragmentTransaction.commit();
    }
  }

  @Override
  public void OnPreNextButtonClicked(int position, Bundle bundle) {
    StepVideoDescriptionFragment videoFragment = new StepVideoDescriptionFragment();
    Bundle allSteps = new Bundle();
    for (int i = 0; i < bundle.size() - 1; i++) {
      allSteps.putStringArrayList(String.valueOf(i), bundle.getStringArrayList(String.valueOf(i)));
    }
    allSteps.putInt("position", position);
    videoFragment.setArguments(allSteps);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(android.R.id.content, videoFragment, STEPS_VIDEO_DESCRIPTION_FRAGMENT);
    transaction.commit();
  }
}
