package com.alphaomardiallo.mareu.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Meeting implements Parcelable {

    String mMeetingName;
    String mMeetingRoomName;
    String mMeetingRoomUrl;
    LocalDate mMeetingDate;
    LocalTime mMeetingTime;
    String mTopic;
    String mParticipatingCollaborators;

    long mLongMeetingTime;
    long mLongMeetingDAte;

    public Meeting(String meetingName, String meetingRoomName, String meetingRoomUrl, LocalDate meetingDate, LocalTime meetingTime, String topic, String participatingCollaborators) {
        mMeetingName = meetingName;
        mMeetingRoomName = meetingRoomName;
        mMeetingRoomUrl = meetingRoomUrl;
        mMeetingDate = meetingDate;
        mMeetingTime = meetingTime;
        mTopic = topic;
        mParticipatingCollaborators = participatingCollaborators;
    }

    protected Meeting(Parcel in) {
        mMeetingName = in.readString();
        mMeetingRoomName = in.readString();
        mMeetingRoomUrl = in.readString();
        mTopic = in.readString();
        mParticipatingCollaborators = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMeetingName);
        dest.writeString(mMeetingRoomName);
        dest.writeString(mMeetingRoomUrl);
        dest.writeString(mTopic);
        dest.writeString(mParticipatingCollaborators);
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
        return mMeetingName;
    }

    public void setMeetingName(String meetingName) {
        mMeetingName = meetingName;
    }

    public String getMeetingRoomName() {
        return mMeetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        mMeetingRoomName = meetingRoomName;
    }

    public String getMeetingRoomUrl() {
        return mMeetingRoomUrl;
    }

    public void setMeetingRoomUrl(String meetingRoomUrl) {
        mMeetingRoomUrl = meetingRoomUrl;
    }

    public LocalDate getMeetingDate() {
        return mMeetingDate;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        mMeetingDate = meetingDate;
    }

    public LocalTime getMeetingTime() {
        return mMeetingTime;
    }

    public void setMeetingTime(LocalTime meetingTime) {
        mMeetingTime = meetingTime;
    }

    public String getTopic() {
        return mTopic;
    }

    public void setTopic(String topic) {
        mTopic = topic;
    }

    public String getParticipatingCollaborators() {
        return mParticipatingCollaborators;
    }

    public void setParticipatingCollaborators(String participatingCollaborators) {
        mParticipatingCollaborators = participatingCollaborators;
    }

}
