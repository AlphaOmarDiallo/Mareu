package com.alphaomardiallo.mareu.controller;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.bumptech.glide.Glide;

import java.util.Objects;


public class MeetingDetails extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        //Vars
        Toolbar toolbar = findViewById(R.id.toolbarDetailActivity);
        ImageView imageView = findViewById(R.id.imageViewDetailActivity);
        TextView textViewRoomName = findViewById(R.id.textViewMeetingRoomNameDetailActivity);
        TextView textViewMeetingName = findViewById(R.id.textViewMeetingNameDetailActivity);
        TextView textViewParticipantsDisplay = findViewById(R.id.textViewParticipantsDisplayDetailActivity);
        TextView textViewTopicDisplay = findViewById(R.id.textViewTopicDisplayDetailActivity);
        TextView textViewDateDisplay = findViewById(R.id.textViewDateDisplayDetailActivity);
        TextView textViewStartingTime = findViewById(R.id.textViewStartingTimeDisplayDetailActivity);
        TextView textViewEndingTime = findViewById(R.id.textViewEndingTimeDisplayDetailActivity);

        //Toolbar Settings
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        //Retrieving data from intent
        Bundle extras = getIntent().getExtras();
        Meeting meeting = extras.getParcelable("Meeting");
        String meetingName = meeting.getMeetingName();
        String meetingRoom = meeting.getMeetingRoomName();
        String meetingDate = extras.getString("MeetingDate");
        String meetingTopic = meeting.getTopic();
        String meetingStartTime = extras.getString("MeetingStart");
        String meetingEndTime = extras.getString("MeetingEnd");
        String meetingParticipants = meeting.getParticipatingCollaborators();
        String meetingUrl = meeting.getMeetingRoomUrl(); 

        //Setting the data
        Glide.with(this)
                .load(meetingUrl)
                .placeholder(getPlaceholderDrawable(meeting))
                .into(imageView);

        textViewMeetingName.setText(meetingName);
        textViewRoomName.setText(meetingRoom);
        textViewTopicDisplay.setText(meetingTopic);
        textViewDateDisplay.setText(meetingDate);
        textViewStartingTime.setText(meetingStartTime);
        textViewEndingTime.setText(meetingEndTime);
        textViewParticipantsDisplay.setText(meetingParticipants);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int getPlaceholderDrawable(Meeting meeting) {
        int drawable;
        switch (meeting.getMeetingRoomName()) {
            case "AMSTERDAM":
                drawable = MeetingRooms.AMSTERDAM.getDrawable();
                break;
            case "BERLIN":
                drawable = MeetingRooms.BERLIN.getDrawable();
                break;
            case "BRUSSELS":
                drawable = MeetingRooms.BRUSSELS.getDrawable();
                break;
            case "BUCHAREST":
                drawable = MeetingRooms.BUCHAREST.getDrawable();
                break;
            case "MADRID":
                drawable = MeetingRooms.MADRID.getDrawable();
                break;
            case "LONDON":
                drawable = MeetingRooms.LONDON.getDrawable();
                break;
            case "MILAN":
                drawable = MeetingRooms.MILAN.getDrawable();
                break;
            case "PARIS":
                drawable = MeetingRooms.PARIS.getDrawable();
                break;
            case "PRAGUE":
                drawable = MeetingRooms.PRAGUE.getDrawable();
                break;
            case "VIENNA":
                drawable = MeetingRooms.VIENNA.getDrawable();
                break;
            default:
                drawable = 0;
        }
        return drawable;
    }
}