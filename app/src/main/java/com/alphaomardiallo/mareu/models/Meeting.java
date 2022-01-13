package com.alphaomardiallo.mareu.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable {

    String meetingName;
    String meetingRoomUrl;
    String meetingDate;
    String meetingStart;
    String meetingEnd;
    String topic;
    String participatingCollaborators;

    public Meeting(String meetingName, String meetingRoomName, String meetingRoomUrl, String meetingDate, String meetingStart, String meetingEnd, String topic, String participatingCollaborators) {
        this.meetingName = meetingName;
        this.meetingRoomName = meetingRoomName;
        this.meetingRoomUrl = meetingRoomUrl;
        this.meetingDate = meetingDate;
        this.meetingStart = meetingStart;
        this.meetingEnd = meetingEnd;
        this.topic = topic;
        this.participatingCollaborators = participatingCollaborators;
    }

    String meetingRoomName;

    protected Meeting(Parcel in) {
        meetingName = in.readString();
        meetingRoomUrl = in.readString();
        meetingDate = in.readString();
        meetingStart = in.readString();
        meetingEnd = in.readString();
        topic = in.readString();
        participatingCollaborators = in.readString();
        meetingRoomName = in.readString();
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

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingStart() {
        return meetingStart;
    }

    public void setMeetingStart(String meetingStart) {
        this.meetingStart = meetingStart;
    }

    public String getMeetingEnd() {
        return meetingEnd;
    }

    public void setMeetingEnd(String meetingEnd) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(meetingName);
        parcel.writeString(meetingRoomUrl);
        parcel.writeString(meetingDate);
        parcel.writeString(meetingStart);
        parcel.writeString(meetingEnd);
        parcel.writeString(topic);
        parcel.writeString(participatingCollaborators);
        parcel.writeString(meetingRoomName);
    }
}