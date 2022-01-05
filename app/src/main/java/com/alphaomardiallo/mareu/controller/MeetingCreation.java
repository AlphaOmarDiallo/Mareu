package com.alphaomardiallo.mareu.controller;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Objects;

public class MeetingCreation extends AppCompatActivity {
    private static final String TAG = "MeetingCreation";

    private ImageView imageView;
    private Spinner spinnerSelectMeetingRoom;
    private Button buttonSetDate;
    private Button buttonSetStartTime;
    private EditText editTextTopic;
    private TextView textViewParticipants;
    private Button buttonSetEndTime;
    private EditText editTextMeetingName;
    private FloatingActionButton FABMeetingValidation;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListenerStart;
    private TimePickerDialog.OnTimeSetListener timeSetListenerEnd;

    private LocalDate meetingDate;
    private LocalTime startingTime;
    private LocalTime endingTime;

    private final MeetingApiService apiService = DI.getMeetingsApiService();
    private final List<Meeting> meetings = apiService.getMeetings();
    private Boolean textSet = false;
    private Boolean roomSet = false;
    private Boolean dateSet = false;
    private Boolean timeSet = false;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creation);
        //Views
        Toolbar toolbar = findViewById(R.id.toolbarMeetingCreation);
        imageView = findViewById(R.id.imageViewMeetingCreation);
        editTextMeetingName = findViewById(R.id.textfieldInputMeetingNameCreation);
        spinnerSelectMeetingRoom = findViewById(R.id.spinnerMeetingCreation);
        buttonSetDate = findViewById(R.id.buttonSetDateMeetingCreation);
        buttonSetStartTime = findViewById(R.id.buttonStartTimeMeetingCreation);
        editTextTopic = findViewById(R.id.textfieldInputMeetingTopicCreation);
        textViewParticipants = findViewById(R.id.materialTextViewParticipantsMeetingCreation);
        Button buttonAddParticipants = findViewById(R.id.buttonAddParticipantsMeetingCreation);
        FABMeetingValidation = findViewById(R.id.floatingActionButtonValidationMeetingCreation);
        buttonSetEndTime = findViewById(R.id.buttonEndTimeMeetingCreation);


        //Toolbar Settings
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        //ChangeListener
        editTextMeetingName.addTextChangedListener(validationTextWatcher);
        editTextTopic.addTextChangedListener(validationTextWatcher);
        textViewParticipants.addTextChangedListener(validationTextWatcher);

        //Spinner settings
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.meeting_room_spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectMeetingRoom.setAdapter(spinnerAdapter);
        spinnerSelectMeetingRoom.setOnTouchListener((view, motionEvent) -> {
            roomSet = true;
            return false;
        });

        //Calendar instance
        Calendar cal = Calendar.getInstance();

        //Date picker
        buttonSetDate.setOnClickListener(view -> {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    MeetingCreation.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            dateSet = true;
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/MM/yyyy: " + day + "/" + month + "/" + year);

                LocalDate date = LocalDate.of(year, month, day);
                meetingDate = date;
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                buttonSetDate.setText(date.format(dateFormatter));
            }
        };

        //Time picker start
        buttonSetStartTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        MeetingCreation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListenerStart,
                        hours, minutes,
                        DateFormat.is24HourFormat(MeetingCreation.this));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                timeSet = true;
            }
        });
        timeSetListenerStart = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                LocalTime time = LocalTime.of(hours, minutes);
                startingTime = time;
                endingTime = time.plusMinutes(45);
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                buttonSetStartTime.setText(time.format(timeFormatter));
                buttonSetEndTime.setText(endingTime.format(timeFormatter));
            }
        };

        //Time picker end
        buttonSetEndTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        MeetingCreation.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListenerEnd,
                        hours, minutes,
                        DateFormat.is24HourFormat(MeetingCreation.this));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        timeSetListenerEnd = new TimePickerDialog.OnTimeSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                LocalTime time = LocalTime.of(hours, minutes);
                endingTime = time;
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                buttonSetEndTime.setText(time.format(timeFormatter));
            }
        };

        FABMeetingValidation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (timeSet && dateSet & roomSet && textSet) {
                    createNewMeeting(editTextMeetingName.getText().toString(), spinnerSelectMeetingRoom.getSelectedItem().toString(), meetingDate, startingTime, endingTime, editTextTopic.getText().toString(), textViewParticipants.getText().toString());
                    Toast.makeText(MeetingCreation.this, "Meeting created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MeetingCreation.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Add participants
        buttonAddParticipants.setOnClickListener(view -> {
            String[] listCollaborators = {Collaborators.JOHNDOE.getEmail(), Collaborators.JANEDOE.getEmail(), Collaborators.ALPHADIALLO.getEmail(), Collaborators.COLINDAGBA.getEmail(), Collaborators.SAKINAKARCHAOUI.getEmail(), Collaborators.THIAGOSILVA.getEmail(), Collaborators.MARIEANTOINETTEKATOTO.getEmail(), Collaborators.GRACEGEYORO.getEmail(), Collaborators.JORDYNHUITEMA.getEmail(), Collaborators.AMINATADIALLO.getEmail(), Collaborators.MARCOVERRATTI.getEmail(), Collaborators.KYLLIANMBAPPE.getEmail()};
            final boolean[] checkedItems = new boolean[listCollaborators.length];
            final List<String> selectedItems = Arrays.asList(listCollaborators);

            textViewParticipants.setText(null);
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MeetingCreation.this);
            builder.setTitle("Choose participants");
            builder.setMultiChoiceItems(listCollaborators, checkedItems, (dialogInterface, which, isChecked) -> {
                checkedItems[which] = isChecked;
            });
            builder.setCancelable(false);

            builder.setPositiveButton("Done", (dialog, which) -> {
                for (int i = 0; i < checkedItems.length; i++) {
                    if (checkedItems[i]) {
                        textViewParticipants.setText(textViewParticipants.getText() + selectedItems.get(i) + ", ");
                    }
                }
            });

            builder.setNegativeButton("CANCEL", (dialog, which) -> {

            });

            builder.setNeutralButton("CLEAR ALL", (dialog, which) -> Arrays.fill(checkedItems, false));

            builder.create();

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

    }

    private void modifyImageView(String roomUrlImage) {
        Glide.with(imageView).load(roomUrlImage).circleCrop().placeholder(R.drawable.meeting).into(imageView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewMeeting(String meetingName, String meetingRoomName, LocalDate mDate,
                                  LocalTime startingTime, LocalTime endingTime, String topic, String participatingCollaborators) {
        String meetingRoomName1 = meetingRoomName;
        this.meetingDate = mDate;
        this.startingTime = startingTime;
        this.endingTime = endingTime;

        MeetingRooms meetingRoom;

        switch (meetingRoomName1) {
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

        assert meetingRoom != null;
        String meetingRoomUrl = meetingRoom.getUrl();
        meetingRoomName1 = meetingRoom.getCity();
        Meeting newMeeting = new Meeting(meetingName, meetingRoomName1, meetingRoomUrl, this.meetingDate, this.startingTime, this.endingTime, topic, participatingCollaborators);
        meetings.add(newMeeting);
        FABMeetingValidation.setVisibility(View.GONE);
        modifyImageView(meetingRoomUrl);
    }

    private TextWatcher validationTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String meetingNameInput = editTextMeetingName.getText().toString().trim();
            String meetingTopicInput = editTextTopic.getText().toString();
            String meetingParticipantsInput = textViewParticipants.getText().toString();

            if (!meetingNameInput.isEmpty() && !meetingTopicInput.isEmpty() && !meetingParticipantsInput.isEmpty()) {
                textSet = true;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}