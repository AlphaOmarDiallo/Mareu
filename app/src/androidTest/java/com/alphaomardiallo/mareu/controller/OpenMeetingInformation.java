package com.alphaomardiallo.mareu.controller;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.alphaomardiallo.mareu.R;

import org.hamcrest.Matcher;

public class OpenMeetingInformation implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "click on meeting avatar to open detailed information about it";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.imageViewMeetingCard);
        button.performClick();
    }
}
