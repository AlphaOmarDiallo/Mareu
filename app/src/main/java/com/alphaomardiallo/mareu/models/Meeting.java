package com.alphaomardiallo.mareu.models;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Meeting implements Parcelable {
    String mMeetingName;
    String mMeetingRoomName;
    int mMeetingRoomDrawable;
    long mDate;
    long mStartingTime;
    long mFinishingTime;
    String mTopic;
    String mParticipatingCollaborators;

    public Meeting(String meetingName, String meetingRoomName, int meetingRoomDrawable, long date, long startingTime, long finishingTime, String topic, String participatingCollaborators) {
        mMeetingName = meetingName;
        mMeetingRoomName = meetingRoomName;
        mMeetingRoomDrawable = meetingRoomDrawable;
        mDate = date;
        mStartingTime = startingTime;
        mFinishingTime = finishingTime;
        mTopic = topic;
        mParticipatingCollaborators = participatingCollaborators;
    }

    protected Meeting(Parcel in) {
        mMeetingName = in.readString();
        mMeetingRoomName = in.readString();
        mMeetingRoomDrawable = in.readInt();
        mDate = in.readLong();
        mStartingTime = in.readLong();
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

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public long getStartingTime() {
        return mStartingTime;
    }

    public void setStartingTime(long startingTime) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMeetingName);
        parcel.writeString(mMeetingRoomName);
        parcel.writeInt(mMeetingRoomDrawable);
        parcel.writeLong(mDate);
        parcel.writeLong(mStartingTime);
        parcel.writeLong(mFinishingTime);
        parcel.writeString(mTopic);
        parcel.writeString(mParticipatingCollaborators);
    }
}
