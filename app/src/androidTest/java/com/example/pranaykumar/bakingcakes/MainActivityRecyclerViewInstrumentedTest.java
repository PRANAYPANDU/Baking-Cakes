package com.example.pranaykumar.bakingcakes;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.example.pranaykumar.bakingcakes.RecipesAdapter.RecipesAdapterViewHolder;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityRecyclerViewInstrumentedTest {

  @Rule
  public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(
      MainActivity.class);

  @Test
  public void scrollToItemAtPosition_checkItsText() {
    onView(withId(R.id.recipes_recyclerView)).perform(RecyclerViewActions.scrollToPosition(1));

    onView(withText("Brownies")).check(matches(isDisplayed()));
  }

  @Test
  public void scrollToItem_clickItsText() {
    onView(ViewMatchers.withId(R.id.recipes_recyclerView))
        .perform(RecyclerViewActions.scrollToHolder(
            new BoundedMatcher<RecyclerView.ViewHolder, RecipesAdapterViewHolder>(
                RecipesAdapterViewHolder.class) {
              @Override
              public void describeTo(Description description) {
                description.appendText("No ViewHolder found with text: " + "Brownies");
              }

              @Override
              protected boolean matchesSafely(RecipesAdapterViewHolder item) {
                TextView Text = (TextView) item.itemView.findViewById(R.id.recipe_name);
                if (Text == null) {
                  return false;
                }
                return Text.getText().toString().contains("Brownies");
              }
            }));
  }

  @Test
  public void clickItem() {
    onView(ViewMatchers.withId(R.id.recipes_recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    onView(ViewMatchers.withId(R.id.steps_recyclerView))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
  }
}