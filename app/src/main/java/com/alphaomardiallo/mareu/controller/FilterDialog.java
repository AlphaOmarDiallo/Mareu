package com.alphaomardiallo.mareu.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alphaomardiallo.mareu.R;

import java.util.Calendar;

public class FilterDialog extends AppCompatDialogFragment {

    private DatePicker datePicker;
    private Spinner spinner;
    private FilterDialogListener listener;
    private RadioButton radioButtonDate;
    private RadioButton radioButtonMeetingRoom;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_filter, null);

        builder.setView(view)
                .setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date;
                        String room;
                        if (radioButtonDate.isChecked()) {
                            date =  getDateFromDatePicker(datePicker);
                        } else {
                            date = null;
                        }
                        if (radioButtonMeetingRoom.isChecked()) {
                            room = spinner.getSelectedItem().toString();
                        } else {
                            room = null;
                        }
                        listener.applyTexts(date, room);
                    }
                });
        ScrollView scrollView = view.findViewById(R.id.scrollView);
        datePicker = view.findViewById(R.id.datePickerFilterDialog);
        spinner = view.findViewById(R.id.spinnerFilterDialog);
        radioButtonDate = view.findViewById(R.id.radioButtonDate);
        radioButtonMeetingRoom = view.findViewById(R.id.radioButtonMeetingRoom);

        scrollView.setScrollbarFadingEnabled(false);
        radioButtonDate.setChecked(true);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.meeting_room_spinner_filter_dialog, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement FilterDialogListener");
        }
    }

    public static String getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(day, month, year);

        return String.format("%02d/%02d/%d", day, month, year);
    }

    public interface FilterDialogListener {
        void applyTexts(String date, String room);
    }
}
