package com.alphaomardiallo.mareu.di;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alphaomardiallo.mareu.service.DummyMeetingsApiService;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DI {

    private static final DummyMeetingsApiService service = new DummyMeetingsApiService();

    /**
     * Get an instance on @{@link com.alphaomardiallo.mareu.service.MeetingApiService}
     *
     * @return service-
     */
    public static DummyMeetingsApiService getMeetingsApiService() {
        return service;
    }

}
