package com.alphaomardiallo.mareu.controller;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.events.DeleteMeetingEvent;
import com.alphaomardiallo.mareu.events.OpenMeetingEvent;
import com.alphaomardiallo.mareu.events.SendPositionEvent;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.models.MeetingRooms;
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.alphaomardiallo.mareu.controller.adapters.RecyclerViewAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity implements FilterDialog.FilterDialogListener {

    private static final String TAG = "Main Activity";
    public static final String MEETING = "Meeting";
    private static final String MEETINGDATE = "MeetingDate";
    private static final String MEETINGTIME = "MeetingTime";

    private Toolbar mToolbar;
    private ImageButton mFilterButton;
    private FloatingActionButton mFABCreateMeeting;
    private MeetingApiService mApiService;
    private Button mButtonApplyFilters;
    private Button mButtonResetFilters;

    private List<Meeting> mMeetings = new ArrayList<>();
    private List<Meeting> mDisplayedMeetings = new ArrayList<>();
    private List<Meeting> mFilteredMeetings = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private int itemPosition;

    private LocalDate filterDialogDate = null;
    private String filterDialogRoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        mToolbar = findViewById(R.id.toolbarMainActivity);
        mFilterButton = findViewById(R.id.imageButtonFilterMainActivity);
        mFABCreateMeeting = findViewById(R.id.floatingActionButtonCreateMeetingMainActivity);
        mFilterButton = findViewById(R.id.imageButtonFilterMainActivity);
        mButtonApplyFilters = findViewById(R.id.buttonApplyFilters);
        mButtonResetFilters = findViewById(R.id.buttonResetFilters);

        // Toolbar settings
        setSupportActionBar(mToolbar);

        // DI
        mApiService = DI.getMeetingsApiService();
        mMeetings = mApiService.getMeetings();
        mDisplayedMeetings = mMeetings;
        initRecyclerView();

        // Meeting creation button
        mFABCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MeetingCreation.class);
                startActivity(intent);
            }
        });

        //Filter button settings
        setButtonResetApplyFiltersInvisible();
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                openFilterDialog();
                mButtonApplyFilters.setVisibility(mButtonApplyFilters.VISIBLE);
            }
        });

        //Apply filter button settings
        mButtonApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList();
                initRecyclerView();
                mButtonResetFilters.setVisibility(mButtonResetFilters.VISIBLE);
            }
        });

        //Apply filter button settings
        mButtonResetFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDisplayedMeetings = mApiService.getMeetings();
                initRecyclerView();
                setButtonResetApplyFiltersInvisible();
            }
        });

        if (savedInstanceState != null){
            List <Meeting> list = savedInstanceState.getParcelableArrayList("mDisplayMeetings");
            String room = savedInstanceState.getString("filterRoomDialog");
            String date = savedInstanceState.getString("filterDialogDate");
            Boolean values = savedInstanceState.getBoolean("values");
            if (values == true) {
                if (room != null){
                    filterDialogRoom = room;
                }
                if (date != null) {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
                    filterDialogDate = LocalDate.parse(String.format(date, dateFormatter));
                }
                mDisplayedMeetings = list;
                mButtonResetFilters.setVisibility(mButtonResetFilters.VISIBLE);
                mButtonApplyFilters.setVisibility(mButtonApplyFilters.VISIBLE);
            }
            initRecyclerView();
        }
    }

    // EventBus related ==========================================================
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe
    public void onOpenMeetingEvent(OpenMeetingEvent event) {
        Meeting meeting = event.meeting;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        String meetingDate = dateFormatter.format(event.meeting.getMeetingDate());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String meetingTime = timeFormatter.format(event.meeting.getMeetingTime());

        Bundle extras = new Bundle();
        extras.putParcelable(MEETING, meeting);
        extras.putString(MEETINGDATE, meetingDate);
        extras.putString(MEETINGTIME, meetingTime);

        Intent intent = new Intent(this, MeetingDetails.class);
        intent.putExtras(extras);

        Log.d(TAG, "onOpenMeetingEvent: opening with intent");
        startActivity(intent);
    }

    @Subscribe
    public void onSendItemPosition(SendPositionEvent event) {
        itemPosition = event.position;
    }

    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event) {
        Meeting meeting = event.meeting;
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setMessage(R.string.delete_alert_dialog_message).setTitle(R.string.delete_alert_dialog_title);
        builder.setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            mMeetings.remove(meeting);
                            //adapter.notifyDataSetChanged();
                            adapter.notifyItemRemoved(itemPosition);
                            Log.d(TAG, "onClick: item removed position " + itemPosition);
                            Toast.makeText(MainActivity.this, "Meeting deleted", Toast.LENGTH_SHORT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Application state ===================================================================
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
        Log.d(TAG, "onResume: called");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Boolean values = false;
        outState.putParcelableArrayList("mDisplayMeetings", (ArrayList<? extends Parcelable>) mDisplayedMeetings);
        if (filterDialogRoom != null) {
            outState.putString("filterDialogRoom", filterDialogRoom);
            values = true;
        } else if (filterDialogDate != null) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            outState.putString("filterDialogDate", filterDialogDate.format(dateFormatter));
            values = true;
        }
        outState.putBoolean("values", values);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // Methods ===================================================================================
    // Initialize RecyclerView
    private void initRecyclerView() {
        Log.d(TAG, "onCreate: initRecyclerViewCalled");
        RecyclerView recyclerView = findViewById(R.id.container);
        adapter = new RecyclerViewAdapter(this, mDisplayedMeetings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setButtonResetApplyFiltersInvisible() {
        mButtonResetFilters.setVisibility(mButtonResetFilters.GONE);
        mButtonApplyFilters.setVisibility(mButtonApplyFilters.GONE);
    }

    public void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "FilterDialog");
    }

    @Override
    public void applyTexts(LocalDate date, String room) {
        filterDialogDate = date;
        filterDialogRoom = room;
        Log.d(TAG, "applyTexts: " + date);
        Log.d(TAG, "applyTexts: " + room);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void filterList() {
        if (filterDialogDate != null){
            Log.d(TAG, "filterList: date " + filterDialogDate.toEpochDay());
            mFilteredMeetings = mMeetings.stream()
                    .filter(meeting -> meeting.getMeetingDate().toEpochDay() == filterDialogDate.toEpochDay())
                    .collect(Collectors.toList());
            Log.d(TAG, "filterList: Filtered list by date " + mFilteredMeetings);
            mDisplayedMeetings = mFilteredMeetings;
            initRecyclerView();
        } else if (filterDialogRoom != null) {
            Log.d(TAG, "filterList: room " + filterDialogRoom);
            mFilteredMeetings = mMeetings.stream()
                    .filter(meeting -> meeting.getMeetingRoomName().equalsIgnoreCase(filterDialogRoom))
                    .collect(Collectors.toList());
            Log.d(TAG, "filterList: Filtered list by room " + mFilteredMeetings);
            mDisplayedMeetings = mFilteredMeetings;
            initRecyclerView();
        }
        Log.d(TAG, "filterList: result " + mFilteredMeetings.toString());
    }

    public String meetingRoom(String room) {
        switch (room) {
            case "AMSTERDAM":
                room = MeetingRooms.AMSTERDAM.getCity();
                break;
            case "BERLIN":
                room = MeetingRooms.BERLIN.getCity();
                break;
            case "BRUSSELS":
                room = MeetingRooms.BRUSSELS.getCity();
                break;
            case "BUCHAREST":
                room = MeetingRooms.BUCHAREST.getCity();
                break;
            case "MADRID":
                room = MeetingRooms.MADRID.getCity();
                break;
            case "LONDON":
                room = MeetingRooms.LONDON.getCity();
                break;
            case "MILAN":
                room = MeetingRooms.MILAN.getCity();
                break;
            case "PARIS":
                room = MeetingRooms.PARIS.getCity();
                break;
            case "PRAGUE":
                room = MeetingRooms.PRAGUE.getCity();
                break;
            case "VIENNA":
                room = MeetingRooms.VIENNA.getCity();
                break;
            default:
                room = null;
        }
        return room;
    }
}

