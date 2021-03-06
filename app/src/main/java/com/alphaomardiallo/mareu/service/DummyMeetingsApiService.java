package com.alphaomardiallo.mareu.service;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alphaomardiallo.mareu.models.Meeting;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DummyMeetingsApiService implements MeetingApiService {

    private final List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }
}
