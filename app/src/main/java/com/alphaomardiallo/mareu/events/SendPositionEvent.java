package com.alphaomardiallo.mareu.events;

import com.alphaomardiallo.mareu.models.Meeting;

public class SendPositionEvent {
    /**
     * Meeting to open
     */
    public int position;

    /**
     * Constructor
     *
     * @param position-
     */

    public SendPositionEvent(int position) {
        this.position = position;
    }
}
