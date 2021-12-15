package com.alphaomardiallo.mareu.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final int ITEM_COUNT = 10;
    private static final int MEETING_TO_TEST = 1;

    private MeetingApiService mService;
    private List<Meeting> mMeetingListList;

    @Rule
    public ActivityTestRule<MainActivity> mActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        MainActivity activity = mActivityActivityTestRule.getActivity();
        assertThat(activity,notNullValue());

        mService = DI.getMeetingsApiService();
        assertThat(mService, notNullValue());
    }

    /**
     * Test to check that the recyclerview is not empty
     */
    @Test
    public void recyclerView_ShouldNotBeEmpty() {
        onView(allOf(withId(R.id.container), isCompletelyDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Test to check that when deleted, the item is removed from the screen
     */
    @Test
    public void recyclerView_ShouldDeleteNeighbourWithSuccess() {

    }

    @Test
    public void recyclerView_ShouldOpenMeetingDetail_WithCorrectData() {

    }
}