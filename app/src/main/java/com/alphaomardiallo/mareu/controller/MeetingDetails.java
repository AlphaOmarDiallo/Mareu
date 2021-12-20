package com.alphaomardiallo.mareu.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.*;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MeetingDetails extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView mTextViewMeetingName;
    private TextView mTextViewRoomName;
    private FloatingActionButton mFloatingActionButtonDeleteMeeting;
    private TextView mTextViewDateDisplay;
    private TextView mTextViewTopicDisplay;
    private TextView mTextViewStartingTime;
    private TextView mTextViewEndingTime;
    private TextView mTextViewParticipantsDisplay;

    private Meeting meeting;
    private String meetingName;
    private int meetingID;

    private MeetingApiService mApiService = DI.getMeetingsApiService();

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

        //Retrieving intent
        meeting = getIntent().getParcelableExtra("Meeting");

        //Retrieving data from intent
        meetingName = meeting.getMeetingName();
        String meetingRoom = meeting.getMeetingRoomName();
        String meetingDate = meeting.getDate();
        String meetingTopic = meeting.getTopic();
        String meetingStartTime = meeting.getStartingTime();
        String meetingParticipants = meeting.getParticipatingCollaborators();
        int meetingDrawable = meeting.getMeetingRoomDrawable();
        String meetingUrl = meeting.getMeetingRoomUrl();

        //Setting the data
        Glide.with(this)
                .load(meetingUrl)
                .placeholder(meeting.getMeetingRoomDrawable())
                .into(mImageView);

        mTextViewMeetingName.setText(meetingName);
        mTextViewRoomName.setText(meetingRoom);
        mTextViewTopicDisplay.setText(meetingTopic);
        mTextViewDateDisplay.setText(meetingDate);
        mTextViewStartingTime.setText(meetingStartTime);
        mTextViewParticipantsDisplay.setText(meetingParticipants);

    }
}