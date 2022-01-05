package com.alphaomardiallo.mareu.controller.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.events.DeleteMeetingEvent;
import com.alphaomardiallo.mareu.events.OpenMeetingEvent;
import com.alphaomardiallo.mareu.events.SendPositionEvent;
import com.alphaomardiallo.mareu.models.Meeting;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerView";
    private final List<Meeting> meetings;
    private final Context context;

    public RecyclerViewAdapter(Context context, List<Meeting> meetings) {
        this.meetings = meetings;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Meeting meeting = meetings.get(position);
        Glide.with(holder.mHolderCircleImageView.getContext())
                .load(meeting.getMeetingRoomUrl())
                .into(holder.mHolderCircleImageView);
        holder.mTextViewInfo.setText(meeting.getMeetingName() + " - " + meeting.getMeetingStart() + " - " + meeting.getMeetingRoomName());
        holder.mTextViewParticipants.setText(meeting.getParticipatingCollaborators());

        holder.mImageButtonDeleteMeeting.setOnClickListener(view -> {
            int itemPosition = holder.getAdapterPosition();
            EventBus.getDefault().post(new SendPositionEvent(itemPosition));
            EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
        });

        holder.mHolderCircleImageView.setOnClickListener(view -> {
            EventBus.getDefault().post(new OpenMeetingEvent(meeting));
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView mHolderCircleImageView;
        public TextView mTextViewInfo;
        public TextView mTextViewParticipants;
        public ImageButton mImageButtonDeleteMeeting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mHolderCircleImageView = itemView.findViewById(R.id.imageViewMeetingCard);
            mTextViewInfo = itemView.findViewById(R.id.textViewInfoMeetingCard);
            mTextViewParticipants = itemView.findViewById(R.id.textViewParticipantsMeetingCard);
            mImageButtonDeleteMeeting = itemView.findViewById(R.id.imageButtonDeleteMeetingCard);
        }
    }
}


