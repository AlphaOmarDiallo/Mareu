package com.alphaomardiallo.mareu.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.alphaomardiallo.mareu.controller.adapters.RecyclerViewAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FilterDialog.FilterDialogListener {

    private static final String TAG = "Main Activity";
    public static final String MEETING = "Meeting";

    private Toolbar mToolbar;
    private ImageButton mFilterButton;
    private FloatingActionButton mFABCreateMeeting;
    private MeetingApiService mApiService;
    private Button mButtonResetFilters;

    private List<Meeting> mMeetings = new ArrayList<>();
    private List<Meeting> mDisplayedMeetings = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    private int itemPosition;

    private String filterDialogDate;
    private String filterDialogRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Is started");
        // Views
        mToolbar = findViewById(R.id.toolbarMainActivity);
        mFilterButton = findViewById(R.id.imageButtonFilterMainActivity);
        mFABCreateMeeting = findViewById(R.id.floatingActionButtonCreateMeetingMainActivity);
        mFilterButton = findViewById(R.id.imageButtonFilterMainActivity);
        mButtonResetFilters = findViewById(R.id.buttonResetFilters);

        // Toolbar settings
        setSupportActionBar(mToolbar);

        //Filter button settings
        filterDialogDate = null;
        filterDialogRoom = null;
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFilterDialog();
            }
        });
        if(filterDialogRoom != null|| filterDialogDate !=null) {

        }

        //Reset button settings
        mButtonResetFilters.setVisibility(mButtonResetFilters.GONE);

        // DI
        mApiService = DI.getMeetingsApiService();
        mMeetings = mApiService.getMeetings();
        mDisplayedMeetings = mMeetings;
        initRecyclerView();

        mFABCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MeetingCreation.class);
                startActivity(intent);
            }
        });
    }

    // Initialize RecyclerView
    private void initRecyclerView() {
        Log.d(TAG, "onCreate: initRecyclerViewCalled");
        RecyclerView recyclerView = findViewById(R.id.container);
        adapter = new RecyclerViewAdapter(this, mMeetings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Subscribe
    public void onOpenMeetingEvent(OpenMeetingEvent event) {
        Meeting meeting = event.meeting;
        Intent intent = new Intent(this, MeetingDetails.class);
        intent.putExtra(MEETING, meeting);
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
        MaterialAlertDialogBuilder builder =  new MaterialAlertDialogBuilder(this);
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

    public void setButtonResetFilters(){

    }

    public void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "FilterDialog");
    }

    @Override
    public void applyTexts(String date, String room) {
        filterDialogDate = date;
        filterDialogRoom = room;
        Log.d(TAG, "applyTexts: " + date);
        Log.d(TAG, "applyTexts: " + room);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
        Log.d(TAG, "onResume: called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}