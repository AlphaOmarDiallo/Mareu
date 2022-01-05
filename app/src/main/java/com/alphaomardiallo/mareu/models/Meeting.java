package com.alphaomardiallo.mareu.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Meeting implements Parcelable {

    String meetingName;
    String meetingRoomName;
    String meetingRoomUrl;
    LocalDate meetingDate;
    LocalTime meetingStart;
    LocalTime meetingEnd;
    String topic;
    String participatingCollaborators;

    public Meeting(String meetingName, String meetingRoomName, String meetingRoomUrl, LocalDate meetingDate, LocalTime meetingStart, LocalTime meetingEnd, String topic, String participatingCollaborators) {
        this.meetingName = meetingName;
        this.meetingRoomName = meetingRoomName;
        this.meetingRoomUrl = meetingRoomUrl;
        this.meetingDate = meetingDate;
        this.meetingStart = meetingStart;
        this.meetingEnd = meetingEnd;
        this.topic = topic;
        this.participatingCollaborators = participatingCollaborators;
    }

    protected Meeting(Parcel in) {
        meetingName = in.readString();
        meetingRoomName = in.readString();
        meetingRoomUrl = in.readString();
        topic = in.readString();
        participatingCollaborators = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(meetingName);
        dest.writeString(meetingRoomName);
        dest.writeString(meetingRoomUrl);
        dest.writeString(topic);
        dest.writeString(participatingCollaborators);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }

    public String getMeetingRoomUrl() {
        return meetingRoomUrl;
    }

    public void setMeetingRoomUrl(String meetingRoomUrl) {
        this.meetingRoomUrl = meetingRoomUrl;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    public LocalTime getMeetingStart() {
        return meetingStart;
    }

    public void setMeetingStart(LocalTime meetingStart) {
        this.meetingStart = meetingStart;
    }

    public LocalTime getMeetingEnd() {
        return meetingEnd;
    }

    public void setMeetingEnd(LocalTime meetingEnd) {
        this.meetingEnd = meetingEnd;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getParticipatingCollaborators() {
        return participatingCollaborators;
    }

    public void setParticipatingCollaborators(String participatingCollaborators) {
        this.participatingCollaborators = participatingCollaborators;
    }

}
