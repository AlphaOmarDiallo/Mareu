package com.alphaomardiallo.mareu.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import androidx.test.espresso.contrib.RecyclerViewActions;
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

    /**
     * Test to check that the intent is opened with the correct data.
     */
    @Test
    public void recyclerView_ShouldOpenMeetingDetail_WithCorrectData() {
        onView(allOf(withId(R.id.container), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(MEETING_TO_TEST, new OpenMeetingInformation()));
        onView(withId(R.id.meeting_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewMeetingNameDetailActivity)).check(matches(withText(mService.getMeetings().get(MEETING_TO_TEST).getMeetingName())));
    }
}