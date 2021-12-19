package com.alphaomardiallo.mareu.events;

import com.alphaomardiallo.mareu.models.Meeting;

/**
 * Event fired when avatar of a meeting is clicked
 */
public class OpenMeetingEvent {

     /**
     * Meeting to delete
     */
    public Meeting meeting;

    /**
     * Constructor
     *
     * @param meeting-
     */

    public OpenMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
