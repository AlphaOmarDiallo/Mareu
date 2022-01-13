package com.alphaomardiallo.mareu.controller;

import static androidx.recyclerview.widget.RecyclerView.ItemAnimator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FilterDialog.FilterDialogListener {

    private static final String TAG = "Main Activity";
    public static final String MEETING = "Meeting";

    private MeetingApiService apiService;
    private Chip chipApplyFilters;
    private Chip chipResetFilters;

    private List<Meeting> listMeetings = new ArrayList<>();
    private List<Meeting> listDisplayedMeetings = new ArrayList<>();
    private List<Meeting> listFilteredMeetings = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private int itemPosition;

    private String filterDialogDateSelected = null;
    private String filterDialogRoomSelected = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Views
        Toolbar toolbar = findViewById(R.id.toolbarMainActivity);
        ImageButton filterButton = findViewById(R.id.imageButtonFilterMainActivity);
        FloatingActionButton FABCreateMeeting = findViewById(R.id.FABCreateMeetingMainActivity);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewMainActivity);
        chipApplyFilters = findViewById(R.id.buttonApplyFiltersMainActivity);
        chipResetFilters = findViewById(R.id.buttonResetFiltersMainActivity);

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
            chipApplyFilters.setVisibility(View.VISIBLE);
        });

        //Apply filter button settings
        chipApplyFilters.setOnClickListener(view -> {
            filterList();
            int meetingSize = listMeetings.size();
            int meetingDisplaySize = listDisplayedMeetings.size();
            if (meetingSize == meetingDisplaySize) {
                setButtonResetApplyFiltersInvisible();
            } else {
                chipResetFilters.setVisibility(View.VISIBLE);
            }
        });

        //Apply filter button settings
        chipResetFilters.setOnClickListener(view -> {
            listDisplayedMeetings = apiService.getMeetings();
            initializeRecyclerView();
            setButtonResetApplyFiltersInvisible();
        });

        if (savedInstanceState != null) {
            List<Meeting> list = savedInstanceState.getParcelableArrayList("mDisplayMeetings");
            String room = savedInstanceState.getString("filterRoomDialog");
            Log.d(TAG, "onCreate: saved" + room);
            String date = savedInstanceState.getString("filterDialogDate");
            if (room != null) {
                filterDialogRoomSelected = room;
            }
            if (date != null) {
                filterDialogDateSelected = date;
            }
            if (list.size() != listMeetings.size()) {
                chipResetFilters.setVisibility(View.VISIBLE);
                chipApplyFilters.setVisibility(View.VISIBLE);
            }
            listDisplayedMeetings = list;
            initializeRecyclerView();
        }
    }

    // EventBus related ==========================================================
    @Subscribe
    public void onOpenMeetingEvent(OpenMeetingEvent event) {
        Meeting meeting = event.meeting;
        Bundle extras = new Bundle();
        extras.putParcelable(MEETING, meeting);
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
        listMeetings.remove(meeting);
        adapter.notifyItemRemoved(itemPosition);
        filterList();
        Toast.makeText(MainActivity.this, "Meeting deleted", Toast.LENGTH_SHORT).show();
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
            outState.putString("filterDialogDate", filterDialogDateSelected);
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
        chipResetFilters.setVisibility(View.GONE);
        chipApplyFilters.setVisibility(View.GONE);
    }

    public void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "FilterDialog");
    }

    @Override
    public void applyTexts(String date, String room) {
        filterDialogDateSelected = date;
        filterDialogRoomSelected = room;
    }

    private void filterList() {
        List<Meeting> listFilteredMeetings2 = new ArrayList<>();
        if (filterDialogDateSelected != null) {
            for (Meeting meeting : listMeetings) {
                if (meeting.getMeetingDate().equalsIgnoreCase(filterDialogDateSelected)) {
                    listFilteredMeetings2.add(meeting);
                }
            }
            listDisplayedMeetings = listFilteredMeetings2;
            initializeRecyclerView();
        } else if (filterDialogRoomSelected != null) {
            for (Meeting meeting : listMeetings) {
                if (meeting.getMeetingRoomName().equalsIgnoreCase(filterDialogRoomSelected)) {
                    listFilteredMeetings2.add(meeting);
                }
            }
            listDisplayedMeetings = listFilteredMeetings2;
            listFilteredMeetings = listFilteredMeetings2;
            initializeRecyclerView();
        }
    }
}