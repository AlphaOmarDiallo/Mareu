package com.alphaomardiallo.mareu.controller;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.controller.adapters.RecyclerViewAdapter;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.events.DeleteMeetingEvent;
import com.alphaomardiallo.mareu.events.OpenMeetingEvent;
import com.alphaomardiallo.mareu.events.SendPositionEvent;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.service.MeetingApiService;
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

    private MeetingApiService apiService;
    private Button buttonApplyFilters;
    private Button buttonResetFilters;

    private List<Meeting> listMeetings = new ArrayList<>();
    private List<Meeting> listDisplayedMeetings = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private int itemPosition;

    private LocalDate filterDialogDateSelected = null;
    private String filterDialogRoomSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        Toolbar toolbar = findViewById(R.id.toolbarMainActivity);
        ImageButton filterButton = findViewById(R.id.imageButtonFilterMainActivity);
        FloatingActionButton FABCreateMeeting = findViewById(R.id.FABCreateMeetingMainActivity);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMainActivity);
        buttonApplyFilters = findViewById(R.id.buttonApplyFiltersMainActivity);
        buttonResetFilters = findViewById(R.id.buttonResetFiltersMainActivity);

        // Toolbar settings
        setSupportActionBar(toolbar);

        // RecyclerView settings
        ItemAnimator animator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(animator);

        // DI
        apiService = DI.getMeetingsApiService();
        listMeetings = apiService.getMeetings();
        listDisplayedMeetings = listMeetings;
        initializeRecyclerView();

        // Meeting creation button
        FABCreateMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MeetingCreation.class);
            startActivity(intent);
        });

        //Filter button settings
        setButtonResetApplyFiltersInvisible();
        filterButton.setOnClickListener(view -> {
            openFilterDialog();
            buttonApplyFilters.setVisibility(View.VISIBLE);
        });

        //Apply filter button settings
        buttonApplyFilters.setOnClickListener(view -> {
            filterList();
            buttonResetFilters.setVisibility(View.VISIBLE);
        });

        //Apply filter button settings
        buttonResetFilters.setOnClickListener(view -> {
            listDisplayedMeetings = apiService.getMeetings();
            initializeRecyclerView();
            setButtonResetApplyFiltersInvisible();
        });

        if (savedInstanceState != null) {
            List<Meeting> list = savedInstanceState.getParcelableArrayList("mDisplayMeetings");
            String room = savedInstanceState.getString("filterRoomDialog");
            String date = savedInstanceState.getString("filterDialogDate");
            if (room != null) {
                filterDialogRoomSelected = room;
            }
            if (date != null) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
                filterDialogDateSelected = LocalDate.parse(String.format(date, dateFormatter));
            }
            listDisplayedMeetings = list;
            buttonResetFilters.setVisibility(View.VISIBLE);
            buttonApplyFilters.setVisibility(View.VISIBLE);
            initializeRecyclerView();
        }
    }

    // EventBus related ==========================================================
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe
    public void onOpenMeetingEvent(OpenMeetingEvent event) {
        Meeting meeting = event.meeting;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String meetingDate = dateFormatter.format(event.meeting.getMeetingDate());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String meetingTime = timeFormatter.format(event.meeting.getMeetingTime());

        Bundle extras = new Bundle();
        extras.putParcelable(MEETING, meeting);
        extras.putString(MEETINGDATE, meetingDate);
        extras.putString(MEETINGTIME, meetingTime);

        Intent intent = new Intent(this, MeetingDetails.class);
        intent.putExtras(extras);

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
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    try {
                        listMeetings.remove(meeting);
                        adapter.notifyItemRemoved(itemPosition);
                        filterList();
                        Toast.makeText(MainActivity.this, "Meeting deleted", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Application lifecycle state ===================================================================
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeRecyclerView();
        Log.d(TAG, "onResume: called");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mDisplayMeetings", (ArrayList<? extends Parcelable>) listDisplayedMeetings);
        if (filterDialogRoomSelected != null) {
            outState.putString("filterDialogRoom", filterDialogRoomSelected);
        }
        if (filterDialogDateSelected != null) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            outState.putString("filterDialogDate", filterDialogDateSelected.format(dateFormatter));
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // Methods ===================================================================================
    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMainActivity);
        adapter = new RecyclerViewAdapter(this, listDisplayedMeetings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setButtonResetApplyFiltersInvisible() {
        buttonResetFilters.setVisibility(View.GONE);
        buttonApplyFilters.setVisibility(View.GONE);
    }

    public void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "FilterDialog");
    }

    @Override
    public void applyTexts(LocalDate date, String room) {
        filterDialogDateSelected = date;
        filterDialogRoomSelected = room;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void filterList() {
        List<Meeting> listFilteredMeetings;
        if (filterDialogDateSelected != null) {
            listFilteredMeetings = listMeetings.stream()
                    .filter(meeting -> meeting.getMeetingDate().toEpochDay() == filterDialogDateSelected.toEpochDay())
                    .collect(Collectors.toList());
            listDisplayedMeetings = listFilteredMeetings;
            initializeRecyclerView();
        } else if (filterDialogRoomSelected != null) {
            listFilteredMeetings = listMeetings.stream()
                    .filter(meeting -> meeting.getMeetingRoomName().equalsIgnoreCase(filterDialogRoomSelected))
                    .collect(Collectors.toList());
            listDisplayedMeetings = listFilteredMeetings;
            initializeRecyclerView();
        }
    }
}

