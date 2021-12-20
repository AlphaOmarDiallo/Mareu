package com.alphaomardiallo.mareu.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.os.IResultReceiver;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MeetingCreation extends AppCompatActivity {
    private static final String TAG = "MeetingCreation";

    private Toolbar mToolbar;
    private ImageView mImageView;
    private Spinner mSpinnerSelectMeetingRoom;
    private Button mButtonSetDate;
    private Button mButtonSetTime;
    private EditText mEditTextTopic;
    private EditText mParticipants;
    private Button mButtonAddParticipants;
    private EditText mMeetingName;
    private FloatingActionButton mMeetingValidation;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    private String meetingName;
    private String meetingRoomName;
    private int meetingRoomDrawable;
    private String meetingRoomUrl;
    private String mDate;
    private String startingTime;
    private String endingTime;
    private String topic;
    private String participatingCollaborators;

    private MeetingApiService mApiService = DI.getMeetingsApiService();
    private List<Meeting> meetings = mApiService.getMeetings();
    private Boolean textSet = false;
    private Boolean roomSet = false;
    private Boolean dateSet = false;
    private Boolean timeSet = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creation);
        //Vars
        mToolbar = findViewById(R.id.toolbarMeetingCreation);
        mImageView = findViewById(R.id.imageViewMeetingCreationActivity);
        mMeetingName = findViewById(R.id.editiTaextMetingNameCreationActivity);
        mSpinnerSelectMeetingRoom = findViewById(R.id.spinnerMeetingRoomSelectionMeetingCreationActivity);
        mButtonSetDate = findViewById(R.id.buttonSetDateMeetingCreationActivity);
        mButtonSetTime = findViewById(R.id.buttonSetImeMeetingCreationActivity);
        mEditTextTopic = findViewById(R.id.editTextTextMultiLineTopicMeetingCreation);
        mParticipants = findViewById(R.id.editTextParticipantsMeetingCreation);
        mButtonAddParticipants = findViewById(R.id.buttonAddMeetingCreationActivity);
        mMeetingValidation = findViewById(R.id.floatingActionButtonValidateMeetingCreation);


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

        //ChangeListener
        mMeetingName.addTextChangedListener(validationTextwatcher);
        mEditTextTopic.addTextChangedListener(validationTextwatcher);
        mParticipants.addTextChangedListener(validationTextwatcher);

        //Spinner settings
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.meeting_room_spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSelectMeetingRoom.setAdapter(spinnerAdapter);
        mSpinnerSelectMeetingRoom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                roomSet = true;
                return false;
            }
        });

        //Date picker
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MeetingCreation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dateSet = true;
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mButtonSetDate.setText(date);
            }
        };

        //Time picker
        mButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        MeetingCreation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hours, minutes,
                        DateFormat.is24HourFormat(MeetingCreation.this));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                timeSet = true;
            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                String time = hours + " : " + minutes;
                mButtonSetTime.setText(time);
            }
        };

        mMeetingValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timeSet == true && dateSet == true & roomSet == true && textSet == true) {
                    createNewMeeting(mMeetingName.getText().toString(), mSpinnerSelectMeetingRoom.getSelectedItem().toString(), mButtonSetDate.getText().toString(), mButtonSetTime.getText().toString(), mEditTextTopic.getText().toString(), mParticipants.getText().toString());
                } else {
                    Toast.makeText(MeetingCreation.this, "Fill all fields", Toast.LENGTH_SHORT);
                }

            }
        });

    }

    private void createNewMeeting (String meetingName, String meetingRoomName, String mDate,
                                String startingTime, String topic, String participatingCollaborators) {
        this.meetingName = meetingName;
        this.meetingRoomName = meetingRoomName;
        this.mDate = mDate;
        this.startingTime = startingTime;
        this.topic = topic;
        this.participatingCollaborators = participatingCollaborators;

        MeetingRooms meetingRoom;

        switch (this.meetingRoomName) {
            case "AMSTERDAM":
                meetingRoom = MeetingRooms.AMSTERDAM;
                break;
            case "BERLIN":
                meetingRoom = MeetingRooms.BERLIN;
                break;
            case "BRUSSELS":
                meetingRoom = MeetingRooms.BRUSSELS;
                break;
            case "BUCHAREST":
                meetingRoom = MeetingRooms.BUCHAREST;
                break;
            case "MADRID":
                meetingRoom = MeetingRooms.MADRID;
                break;
            case "LONDON":
                meetingRoom = MeetingRooms.LONDON;
                break;
            case "MILAN":
                meetingRoom = MeetingRooms.MILAN;
                break;
            case "PARIS":
                meetingRoom = MeetingRooms.PARIS;
                break;
            case "PRAGUE":
                meetingRoom = MeetingRooms.PRAGUE;
                break;
            case "VIENNA":
                meetingRoom = MeetingRooms.VIENNA;
                break;
            default:
                meetingRoom = null;
        }

        this.meetingRoomDrawable = meetingRoom.getDrawable();
        this.meetingRoomUrl = meetingRoom.getUrl();
        this.meetingRoomName = meetingRoom.getCity();
        Meeting newMeeting = new Meeting(this.meetingName, this.meetingRoomName, this.meetingRoomDrawable, this.meetingRoomUrl, this.mDate, this.startingTime, this.topic, this.participatingCollaborators);
        meetings.add(newMeeting);
        mMeetingValidation.setVisibility(mMeetingValidation.GONE);
    }

    private TextWatcher validationTextwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String meetingNameInput = mMeetingName.getText().toString().trim();
            String meetingTopicInput = mEditTextTopic.getText().toString();
            String meetingParticipantsInput = mParticipants.getText().toString();

            if (!meetingNameInput.isEmpty() && !meetingTopicInput.isEmpty() && !meetingParticipantsInput.isEmpty()) {

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}