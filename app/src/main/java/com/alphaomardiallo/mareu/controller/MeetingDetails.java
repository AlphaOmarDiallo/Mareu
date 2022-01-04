package com.alphaomardiallo.mareu.controller;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MeetingDetails extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView mTextViewMeetingName;
    private TextView mTextViewRoomName;
    private TextView mTextViewDateDisplay;
    private TextView mTextViewTopicDisplay;
    private TextView mTextViewStartingTime;
    private TextView mTextViewParticipantsDisplay;

    private Meeting meeting;
    private String meetingName;

    private MeetingApiService mApiService = DI.getMeetingsApiService();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        //Vars
        mToolbar = findViewById(R.id.toolbarDetailActivity);
        mImageView = findViewById(R.id.imageViewDetailActivity);
        mTextViewRoomName = findViewById(R.id.textViewMeetingRoomNameDetailActivity);
        mTextViewMeetingName = findViewById(R.id.textViewMeetingNameDetailActivity);
        mTextViewParticipantsDisplay = findViewById(R.id.textViewMeetingRoomNameDetailActivity);
        mTextViewTopicDisplay = findViewById(R.id.textViewTopicDisplayDetailActivity);
        mTextViewDateDisplay = findViewById(R.id.textViewDateDisplayDetailActivity);
        mTextViewStartingTime = findViewById(R.id.textViewStartingTimeDisplayDetailActivity);
        mTextViewParticipantsDisplay = findViewById(R.id.textViewParticipantsDisplayDetailActivity);

        //Toolbar Settings
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Retrieving data from intent
        Bundle extras = getIntent().getExtras();
        meeting = extras.getParcelable("Meeting");
        meetingName = meeting.getMeetingName();
        String meetingRoom = meeting.getMeetingRoomName();
        String meetingDate = extras.getString("MeetingDate");
        String meetingTopic = meeting.getTopic();
        String meetingStartTime = extras.getString("MeetingTime");
        String meetingParticipants = meeting.getParticipatingCollaborators();
        String meetingUrl = meeting.getMeetingRoomUrl(); 

        //Setting the data
        Glide.with(this)
                .load(meetingUrl)
                .placeholder(getPlaceholderDrawable(meeting))
                .into(mImageView);

        mTextViewMeetingName.setText(meetingName);
        mTextViewRoomName.setText(meetingRoom);
        mTextViewTopicDisplay.setText(meetingTopic);
        mTextViewDateDisplay.setText(meetingDate);
        mTextViewStartingTime.setText(meetingStartTime);
        mTextViewParticipantsDisplay.setText(meetingParticipants);
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
};