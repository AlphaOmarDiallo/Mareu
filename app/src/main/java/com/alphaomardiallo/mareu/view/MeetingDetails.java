package com.alphaomardiallo.mareu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alphaomardiallo.mareu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;

public class MeetingDetails extends AppCompatActivity {

    @BindView(R.id.toolbarDetailActivity)
    public Toolbar mToolbar;
    @BindView(R.id.imageViewDetailActivity)
    public ImageView mImageView;
    @BindView(R.id.textViewMeetingRoomNameDetailActivity)
    public TextView mTextViewRoomName;
    @BindView(R.id.floatingActionButtonDeleteRMeetingDetailActivity)
    public FloatingActionButton mFloatingActionButtonDeleteMeeting;
    @BindView(R.id.textViewDateDisplayDetailActivity)
    public TextView mTextViewDateDisplay;
    @BindView(R.id.textViewTopicDisplayDetailActivity)
    public TextView mTextViewTopicDisplay;
    @BindView(R.id.textViewStartingTimeDisplayDetailActivity)
    public TextView mTextViewStartingTime;
    @BindView(R.id.textViewEndingTimeDisplayDetailActivity)
    public TextView mTextViewEndingTime;
    @BindView(R.id.textViewParticipantsDisplayDetailActivity)
    public TextView mTextViewParticipantsDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
    }
}