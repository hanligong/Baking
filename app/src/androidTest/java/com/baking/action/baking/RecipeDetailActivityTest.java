package com.baking.action.baking;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.baking.action.baking.activity.RecipeDetailActivity;
import com.baking.action.baking.model.IngredientModel;
import com.baking.action.baking.model.RecipeModel;
import com.baking.action.baking.uitls.SharePreferenceUtils;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by hanyuezi on 18/1/26.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void clickStepBtn(){
        onView(withId(R.id.recipeDetailStepBtn)).check(matches(withText("Start Making")));
    }

    @Test
    public void clickListItem(){
        onData(anything())
                .inAdapterView(withId(R.id.recipeIngredientsLv))
                .atPosition(0)
                .perform(click());

        onData(allOf(is(instanceOf(IngredientModel.class)), withIngredientTitle("unsalted butter, melted")))
                .inAdapterView(withId(R.id.recipeIngredientsLv))
                .perform(click())
                .check(matches(isDisplayed()));
//
        onData(allOf(is(instanceOf(IngredientModel.class)), withIngredientTitle("salt")))
                .inAdapterView(withId(R.id.recipeIngredientsLv))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    public static Matcher<Object> withIngredientTitle(final String ingredient) {
        return new BoundedMatcher<Object, IngredientModel>(IngredientModel.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with id: " + ingredient);
            }

            @Override
            protected boolean matchesSafely(IngredientModel ingredientModel) {
                return ingredient.equals(ingredientModel.getIngredient());
            }
        };
    }
}
