package com.alphaomardiallo.mareu.events;

import com.alphaomardiallo.mareu.models.Meeting;

public class DeleteMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meeting meeting;

    /**
     * Constructor
     *
     * @param meeting-
     */

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
