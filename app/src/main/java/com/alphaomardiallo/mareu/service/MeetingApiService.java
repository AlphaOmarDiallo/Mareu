package com.alphaomardiallo.mareu.service;

import com.alphaomardiallo.mareu.models.Meeting;

import java.util.List;

public interface MeetingApiService {
    /**
     * Gets all the meetings
     * @return
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a meeting from the list
     * @param meeting-
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Creates a meeting
     * @param meeting-
     */
    void createMeeting(Meeting meeting);
}
