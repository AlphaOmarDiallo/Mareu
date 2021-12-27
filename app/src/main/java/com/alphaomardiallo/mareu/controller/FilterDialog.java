package com.alphaomardiallo.mareu.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alphaomardiallo.mareu.R;

import java.util.Calendar;

public class FilterDialog extends AppCompatDialogFragment {

    private DatePicker mDatePicker;
    private Spinner mSpinner;
    private FilterDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //Spinner setting

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_filter, null);

        builder.setView(view)
                .setTitle("Meeting filter")
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton(R.string.validate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date = getDateFromDatePicker(mDatePicker);
                        String room = mSpinner.getSelectedItem().toString();
                        listener.applyTexts(date, room);
                    }
                });
        mDatePicker = view.findViewById(R.id.datePickerFilterDialog);
        mSpinner = view.findViewById(R.id.spinnerFilterDialog);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.meeting_room_spinner, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);

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

    public static String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(day, month, year);

        return day + "/" + month + "/" + year;
    }

    public interface FilterDialogListener {
        void applyTexts(String date, String room);
    }
}
