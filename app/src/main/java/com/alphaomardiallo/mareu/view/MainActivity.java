package com.alphaomardiallo.mareu.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.di.DI;
import com.alphaomardiallo.mareu.models.Meeting;
import com.alphaomardiallo.mareu.service.MeetingApiService;
import com.alphaomardiallo.mareu.view.adapters.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    public static final String MEETING = "Meeting";

    private Toolbar mToolbar;
    private ImageButton mFilterButton;
    private FloatingActionButton mFABCreateMeeting;
    private MeetingApiService mApiService;

    private List<Meeting> mMeetings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Is started");
        // Views
        mToolbar = findViewById(R.id.toolbarMainActivity);
        mFilterButton = findViewById(R.id.imageButtonFilterMainActivity);
        mFABCreateMeeting = findViewById(R.id.floatingActionButtonCreateMeetingMainActivity);

        // DI
        mApiService = DI.getMeetingsApiService();
        // Toolbar settings
        setSupportActionBar(mToolbar);

        mMeetings = mApiService.getMeetings();

        initRecyclerView();

        mFABCreateMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MeetingDetails.class);
                startActivity(intent);
            }
        });
    }
    // Initialize RecyclerView
    private void initRecyclerView() {
        Log.d(TAG, "onCreate: initRecyclerViewCalled");
        RecyclerView recyclerView = findViewById(R.id.container);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mMeetings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}