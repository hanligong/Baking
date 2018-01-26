package com.baking.action.baking;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.baking.action.baking.activity.RecipeActivity;
import com.baking.action.baking.model.IngredientModel;
import com.baking.action.baking.model.RecipeModel;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by hanyuezi on 18/1/26.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void clickListItem(){
        onView(withId(R.id.recipeRv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    public static Matcher<Object> withIngredientTitle(final String ingredient) {
        return new BoundedMatcher<Object, RecipeModel>(RecipeModel.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with id: " + ingredient);
            }

            @Override
            protected boolean matchesSafely(RecipeModel recipeModel) {
                return ingredient.equals(recipeModel.getName());
            }
        };
    }
}
