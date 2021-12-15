package com.alphaomardiallo.mareu.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.alphaomardiallo.mareu.R;
import com.google.android.material.chip.ChipGroup;

import butterknife.BindView;

public class MeetingCreation extends AppCompatActivity {

    @BindView(R.id.toolbarMeetingCreation)
    public Toolbar mToolbar;
    @BindView(R.id.imageViewMeetingCreationActivity)
    public ImageView mImageView;
    @BindView(R.id.spinnerMeetingRoomSelectionMeetingCreationActivity)
    public Spinner mSpinnerSelectMeetingRoom;
    @BindView(R.id.buttonSetDateMeetingCreationActivity)
    public Button mButtonSetDate;
    @BindView(R.id.textViewDateDisplayMeetingCreationActivity)
    public TextView mTextViewDisplayDate;
    @BindView(R.id.buttonSetImeMeetingCreationActivity)
    public Button mButtonSetTime;
    @BindView(R.id.textViewTimeDisplayMeetingCreationActivity)
    public TextView mTextViewDisplayTime;
    @BindView(R.id.editTextTextMultiLineTopicMeetingCreation)
    public EditText mEditTextTopic;
    @BindView(R.id.chipGroupParticipantsMeetingCreation)
    public ChipGroup mChipGroupParticipants;
    @BindView(R.id.buttonAddMeetingCreationActivity)
    public Button mButtonAddParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creation);
    }
}