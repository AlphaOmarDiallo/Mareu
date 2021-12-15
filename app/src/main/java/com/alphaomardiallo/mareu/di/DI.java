package com.alphaomardiallo.mareu.di;

import com.alphaomardiallo.mareu.service.DummyMeetingsApiService;

public class DI {

    private static DummyMeetingsApiService service = new DummyMeetingsApiService();

        /**
         * Get an instance on @{@link com.alphaomardiallo.mareu.service.MeetingApiService}
         * @return
         */
        public static DummyMeetingsApiService getMeetingsApiService() {
            return service;
        }

}
