package com.alphaomardiallo.mareu.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphaomardiallo.mareu.R;
import com.alphaomardiallo.mareu.models.Meeting;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecylclerView";
    private List<Meeting> mMeetings = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Meeting> meetings) {
        mMeetings = meetings;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_meeting, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Meeting meeting = mMeetings.get(position);
        holder.mHolderCircleImageView.setImageResource(meeting.getMeetingRoomDrawable());
        holder.mTextViewInfo.setText(meeting.getMeetingName()+ " - " + meeting.getStartingTime() + " - " + meeting.getMeetingRoomName());
        holder.mTextViewParticipants.setText(meeting.getParticipatingCollaborators());

        holder.mImageButtonDeleteMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: was used to delete an item.");
            }
        });

        holder.mHolderCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: was used to open an item information.");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mMeetingCard;
        public CircleImageView mHolderCircleImageView;
        public TextView mTextViewInfo;
        public TextView mTextViewParticipants;
        public ImageButton mImageButtonDeleteMeeting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //mMeetingCard = (FrameLayout) itemView.findViewById(R.id.List_Item_Meeting);
            mHolderCircleImageView = itemView.findViewById(R.id.imageViewMeetingCard);
            mTextViewInfo = itemView.findViewById(R.id.textViewInfoMeetingCard);
            mTextViewParticipants = itemView.findViewById(R.id.textViewParticipantsMeetingCard);
            mImageButtonDeleteMeeting = itemView.findViewById(R.id.imageButtonDeleteMeetingCard);

        }
    }
}


