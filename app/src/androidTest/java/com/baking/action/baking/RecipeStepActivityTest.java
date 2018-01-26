package com.baking.action.baking;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;
import com.baking.action.baking.activity.RecipeDetailStepActivity;
import com.baking.action.baking.model.StepModel;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by hanyuezi on 18/1/26.
 */
public class RecipeStepActivityTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(RecipeDetailStepActivity.class);

    @Test
    public void clickListItem(){
        onData(anything())
                .inAdapterView(withId(R.id.recipeVideosLv))
                .atPosition(0)
                .perform(click());

        onData(allOf(is(instanceOf(StepModel.class)), withTitle("Starting prep")))
                .inAdapterView(withId(R.id.recipeVideosLv))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    public static Matcher<Object> withTitle(final String desc) {
        return new BoundedMatcher<Object, StepModel>(StepModel.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                description.appendText("with id: " + desc);
            }

            @Override
            protected boolean matchesSafely(StepModel stepModel) {
                return desc.equals(stepModel.getShortDescription());
            }
        };
    }


}
