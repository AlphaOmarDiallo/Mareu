package com.alphaomardiallo.mareu.models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable {
    String mMeetingName;
    String mMeetingRoomName;
    int mMeetingRoomDrawable;
    String mMeetingRoomUrl;
    String mDate;
    String mStartingTime;
    long mFinishingTime;
    String mTopic;
    String mParticipatingCollaborators;

    public Meeting(String meetingName, String meetingRoomName, int meetingRoomDrawable, String meetingRoomUrl, String date, String startingTime, String topic, String participatingCollaborators) {
        mMeetingName = meetingName;
        mMeetingRoomName = meetingRoomName;
        mMeetingRoomDrawable = meetingRoomDrawable;
        mMeetingRoomUrl = meetingRoomUrl;
        mDate = date;
        mStartingTime = startingTime;
        mTopic = topic;
        mParticipatingCollaborators = participatingCollaborators;
    }

    protected Meeting(Parcel in) {
        mMeetingName = in.readString();
        mMeetingRoomName = in.readString();
        mMeetingRoomDrawable = in.readInt();
        mMeetingRoomUrl = in.readString();
        mDate = in.readString();
        mStartingTime = in.readString();
        mFinishingTime = in.readLong();
        mTopic = in.readString();
        mParticipatingCollaborators = in.readString();
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

    public String getMeetingRoomName() {
        return mMeetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        mMeetingRoomName = meetingRoomName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getStartingTime() {
        return mStartingTime;
    }

    public void setStartingTime(String startingTime) {
        mStartingTime = startingTime;
    }

    public long getFinishingTime() {
        return mFinishingTime;
    }

    public void setFinishingTime(long finishingTime) {
        mFinishingTime = finishingTime;
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

    public int getMeetingRoomDrawable() {
        return mMeetingRoomDrawable;
    }

    public void setMeetingRoomDrawable(int meetingRoomDrawable) {
        mMeetingRoomDrawable = meetingRoomDrawable;
    }

    public String getMeetingName() {
        return mMeetingName;
    }

    public void setMeetingName(String meetingName) {
        mMeetingName = meetingName;
    }
    public String getMeetingRoomUrl() {
        return mMeetingRoomUrl;
    }

    public void setMeetingRoomUrl(String meetingRoomUrl) {
        mMeetingRoomUrl = meetingRoomUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMeetingName);
        parcel.writeString(mMeetingRoomName);
        parcel.writeInt(mMeetingRoomDrawable);
        parcel.writeString(mMeetingRoomUrl);
        parcel.writeString(mDate);
        parcel.writeString(mStartingTime);
        parcel.writeLong(mFinishingTime);
        parcel.writeString(mTopic);
        parcel.writeString(mParticipatingCollaborators);
    }
}
