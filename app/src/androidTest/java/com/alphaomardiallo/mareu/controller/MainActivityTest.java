package com.alphaomardiallo.mareu.controller;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.alphaomardiallo.mareu.controller.WithItemCount.withItemCount;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import android.content.pm.ActivityInfo;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Collaborators;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.MeetingApiService;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.OrderWith;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Alphanumeric;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@OrderWith(Alphanumeric.class)
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final int ITEM_COUNT = 10;
    private static final int MEETING_TO_TEST = 1;

    private MeetingApiService service;
    private List<Meeting> meetingList;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        MainActivity activity = activityTestRule.getActivity();
        MatcherAssert.assertThat(activity,notNullValue());;

        service = DI.getMeetingsApiService();
        MatcherAssert.assertThat(service, notNullValue());

        meetingList = service.getMeetings();
    }

    /**
     * Test to check that the recyclerview is not empty
     */
    @Test
    public void testA_RecyclerView_ShouldNotBeEmpty() {
        onView(allOf(withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * Test to check that when deleted, the item is removed from the screen
     */
    @Test
    public void testB_recyclerView_ShouldDeleteMeetingWithSuccess() throws InterruptedException {
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed()))
                .check(withItemCount(ITEM_COUNT));
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(MEETING_TO_TEST, new DeleteMeeting()));
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed())).check(withItemCount(meetingList.size()));
    }

    /**
     * Test to check that the intent is opened with the correct data.
     */
    @Test
    public void testC_recyclerView_ShouldOpenMeetingDetail_WithCorrectData() {
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(MEETING_TO_TEST , new OpenMeetingInformation()));
        onView(withId(R.id.meeting_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewMeetingNameDetailActivity)).check(matches(withText(service.getMeetings().get(MEETING_TO_TEST).getMeetingName())));
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    /**
     * Test to check if meeting creation activity is opened when FAB clicked and meeting is created
     */
    @Test
    public void testD_FAB_OpensCreationActivityAndMeetingIsCreated() {
        long meetingsSize = meetingList.size();
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed())).check(withItemCount((int) meetingsSize));
        onView(withId(R.id.FABCreateMeetingMainActivity)).perform(click());
        onView(withId(R.id.createMeetingActivity)).check(matches(isDisplayed()));
        onView(withId(R.id.textfieldInputMeetingNameCreation)).perform(typeText("Meeting Test"));
        onView(withId(R.id.textfieldInputMeetingTopicCreation)).perform(typeText("Testing the meeting creation activity"));
        onView(withId(R.id.spinnerMeetingCreation)).perform(click());
        onView(withText("AMSTERDAM")).perform(click());
        onView(withId(R.id.buttonSetDateMeetingCreation)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.buttonStartTimeMeetingCreation)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.buttonAddParticipantsMeetingCreation)).perform(click());
        onView(withText(Collaborators.JOHNDOE.getEmail())).perform(click());
        onView(withText("Done")).perform(click());
        onView(withId(R.id.floatingActionButtonValidationMeetingCreation)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        List<Meeting> meetingsAfterTestMeetingCreated = service.getMeetings();
        long meetingsAfterTestMeetingCreatedSize = meetingsAfterTestMeetingCreated.size();
        assertNotEquals(meetingsSize, meetingsAfterTestMeetingCreatedSize);
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed())).check(withItemCount(meetingList.size()));
    }

    @Test
    public void testE_filterByDateOrByMeetingRoom() throws InterruptedException {
        LocalDate today = LocalDate.now();
        List<Meeting> filteredMeetings = meetingList.stream()
                .filter(meeting -> meeting.getMeetingDate().toEpochDay() == today.toEpochDay())
                .collect(Collectors.toList());
        onView(withId(R.id.imageButtonFilterMainActivity)).perform(click());
        onView(withText(R.string.validate)).perform(click());
        onView(withId(R.id.buttonApplyFiltersMainActivity)).perform(click());
        Thread.sleep(1000);
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed())).check(withItemCount(filteredMeetings.size()));
        onView(withId(R.id.buttonResetFiltersMainActivity)).perform(click());
        Thread.sleep(1000);
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed())).check(withItemCount(meetingList.size()));
        filteredMeetings.clear();
        filteredMeetings = meetingList.stream()
                .filter(meeting -> meeting.getMeetingRoomName().equalsIgnoreCase(MeetingRooms.AMSTERDAM.getCity()))
                .collect(Collectors.toList());
        Thread.sleep(1000);
        onView(withId(R.id.imageButtonFilterMainActivity)).perform(click());
        onView(withId(R.id.radioButtonMeetingRoom)).perform(click());
        onView(withText(R.string.validate)).perform(click());
        onView(withId(R.id.buttonApplyFiltersMainActivity)).perform(click());
        Thread.sleep(1000);
        onView(allOf(ViewMatchers.withId(R.id.recyclerViewMainActivity), isCompletelyDisplayed())).check(withItemCount(filteredMeetings.size()));
        Thread.sleep(2000);
    }
}
