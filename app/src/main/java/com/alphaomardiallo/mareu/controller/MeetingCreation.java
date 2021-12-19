package com.alphaomardiallo.mareu.controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.alphaomardiallo.mareu.R;
import com.google.android.material.chip.ChipGroup;

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

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creation);
        //Vars
        mToolbar = findViewById(R.id.toolbarMeetingCreation);
        mImageView = findViewById(R.id.imageViewMeetingCreationActivity);
        mSpinnerSelectMeetingRoom = findViewById(R.id.spinnerMeetingRoomSelectionMeetingCreationActivity);
        mButtonSetDate = findViewById(R.id.buttonSetDateMeetingCreationActivity);
        mButtonSetTime = findViewById(R.id.buttonSetImeMeetingCreationActivity);
        mEditTextTopic = findViewById(R.id.editTextTextMultiLineTopicMeetingCreation);
        mParticipants = findViewById(R.id.editTextParticipantsMeetingCreation);
        mButtonAddParticipants = findViewById(R.id.buttonAddMeetingCreationActivity);

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
        //Spinner settings
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.meeting_room_spinner,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSelectMeetingRoom.setAdapter(spinnerAdapter);

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
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
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
            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                String time = hours + " : " + minutes;
                mButtonSetTime.setText(time);
            }
        };
    }


}