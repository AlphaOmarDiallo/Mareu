package com.alphaomardiallo.mareu.controller;

import static com.alphaomardiallo.mareu.models.Collaborators.collaboratorsLength;
import static com.alphaomardiallo.mareu.models.Collaborators.collaboratorsNumber;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Collaborators;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class MeetingCreation extends AppCompatActivity {
    private static final String TAG = "MeetingCreation";

    private Toolbar mToolbar;
    private ImageView mImageView;
    private Spinner mSpinnerSelectMeetingRoom;
    private Button mButtonSetDate;
    private Button mButtonSetStartTime;
    private EditText mEditTextTopic;
    private TextView mParticipants;
    private Button mButtonAddParticipants;
    private Button mButtonSetEndTime;
    private EditText mMeetingName;
    private FloatingActionButton mMeetingValidation;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerStart;
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerEnd;

    private String meetingName;
    private String meetingRoomName;
    private int meetingRoomDrawable;
    private String meetingRoomUrl;
    private LocalDate mDate;
    private LocalTime startingTime;
    private LocalTime endingTime;
    private String topic;
    private String participatingCollaborators;

    private MeetingApiService mApiService = DI.getMeetingsApiService();
    private List<Meeting> meetings = mApiService.getMeetings();
    private Boolean textSet = false;
    private Boolean roomSet = false;
    private Boolean dateSet = false;
    private Boolean timeSet = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creation);
        //Vars
        mToolbar = findViewById(R.id.toolbarMeetingCreation);
        mImageView = findViewById(R.id.imageViewMeetingCreation);
        mMeetingName = findViewById(R.id.textfieldInputMeetingNameCreation);
        mSpinnerSelectMeetingRoom = findViewById(R.id.spinnerMeetingCreation);
        mButtonSetDate = findViewById(R.id.buttonSetDateMeetingCreation);
        mButtonSetStartTime = findViewById(R.id.buttonStartTimeMeetingCreation);
        mEditTextTopic = findViewById(R.id.textfieldInputMeetingTopicCreation);
        mParticipants = findViewById(R.id.materialTextViewParticipantsMeetingCreation);
        mButtonAddParticipants = findViewById(R.id.buttonAddParticipantsMeetingCreation);
        mMeetingValidation = findViewById(R.id.floatingActionButtonValidationMeetingCreation);
        mButtonSetEndTime = findViewById(R.id.buttonEndTimeMeetingCreation);


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
        mMeetingName.addTextChangedListener(validationTextWatcher);
        mEditTextTopic.addTextChangedListener(validationTextWatcher);
        mParticipants.addTextChangedListener(validationTextWatcher);

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

        //Calendar instance
        Calendar cal = Calendar.getInstance();

        //Date picker
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/MM/yyyy: " + day + "/" + month + "/" + year);

                LocalDate date = LocalDate.of(year, month, day);
                mDate = date;
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                mButtonSetDate.setText(date.format(dateFormatter));
            }
        };

        //Time picker start
        mButtonSetStartTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        MeetingCreation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListenerStart,
                        hours, minutes,
                        DateFormat.is24HourFormat(MeetingCreation.this));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                timeSet = true;
            }
        });
        mTimeSetListenerStart = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                LocalTime time = LocalTime.of(hours, minutes);
                startingTime = time;
                endingTime = time.plusMinutes(45);
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                mButtonSetStartTime.setText(time.format(timeFormatter));
                mButtonSetEndTime.setText(endingTime.format(timeFormatter));
            }
        };

        //Time picker end
        mButtonSetEndTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        MeetingCreation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListenerEnd,
                        hours, minutes,
                        DateFormat.is24HourFormat(MeetingCreation.this));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mTimeSetListenerEnd = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                LocalTime time = LocalTime.of(hours, minutes);
                endingTime = time;
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                mButtonSetEndTime.setText(time.format(timeFormatter));
            }
        };

        mMeetingValidation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (timeSet == true && dateSet == true & roomSet == true && textSet == true) {
                    createNewMeeting(mMeetingName.getText().toString(), mSpinnerSelectMeetingRoom.getSelectedItem().toString(), mDate, startingTime, mEditTextTopic.getText().toString(), mParticipants.getText().toString());
                    Toast.makeText(MeetingCreation.this, "Meeting created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MeetingCreation.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Add participants
        mButtonAddParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] listCollaborators = {Collaborators.JOHNDOE.getEmail(), Collaborators.JANEDOE.getEmail(), Collaborators.ALPHADIALLO.getEmail(), Collaborators.COLINDAGBA.getEmail(), Collaborators.SAKINAKARCHAOUI.getEmail(), Collaborators.THIAGOSILVA.getEmail(), Collaborators.MARIEANTOINETTEKATOTO.getEmail(), Collaborators.GRACEGEYORO.getEmail(), Collaborators.JORDYNHUITEMA.getEmail(), Collaborators.AMINATADIALLO.getEmail(), Collaborators.MARCOVERRATTI.getEmail(), Collaborators.KYLLIANMBAPPE.getEmail()};
                final boolean[] checkedItems = new boolean[listCollaborators.length];
                final List<String> selectedItems = Arrays.asList(listCollaborators);

                mParticipants.setText(null);
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MeetingCreation.this);
                builder.setTitle("Choose participants");
                builder.setMultiChoiceItems(listCollaborators, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        checkedItems[which] = isChecked;
                        String currentItem = selectedItems.get(which);
                    }
                });
                builder.setCancelable(false);

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            if (checkedItems[i]) {
                                mParticipants.setText(mParticipants.getText() + selectedItems.get(i) + ", ");
                            }
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                        }
                    }
                });

                builder.create();

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void modifyImageView(String roomUrlImage) {
        String urlImage = roomUrlImage;
        Glide.with(mImageView).load(urlImage).circleCrop().placeholder(R.drawable.meeting).into(mImageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewMeeting (String meetingName, String meetingRoomName, LocalDate mDate,
                                   LocalTime startingTime, String topic, String participatingCollaborators) {
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

        this.meetingRoomUrl = meetingRoom.getUrl();
        this.meetingRoomName = meetingRoom.getCity();
        Meeting newMeeting = new Meeting(this.meetingName, this.meetingRoomName, this.meetingRoomUrl, this.mDate, this.startingTime, this.topic, this.participatingCollaborators);
        meetings.add(newMeeting);
        mMeetingValidation.setVisibility(mMeetingValidation.GONE);
        modifyImageView(meetingRoomUrl);
    }

    private TextWatcher validationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String meetingNameInput = mMeetingName.getText().toString().trim();
            String meetingTopicInput = mEditTextTopic.getText().toString();
            String meetingParticipantsInput = mParticipants.getText().toString();

            if (!meetingNameInput.isEmpty() && !meetingTopicInput.isEmpty() && !meetingParticipantsInput.isEmpty()) {
                textSet = true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}