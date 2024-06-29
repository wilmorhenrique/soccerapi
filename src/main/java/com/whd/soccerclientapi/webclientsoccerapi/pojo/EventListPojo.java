package com.whd.soccerclientapi.webclientsoccerapi.pojo;

import java.util.ArrayList;
import java.util.List;

public class EventListPojo {
    private List<EventPojo> events;

    public List<EventPojo> getEvents() {
        return events == null ? new ArrayList<>() : events;
    }

    public void setEvents(List<EventPojo> leagues) {
        this.events = leagues;
    }
}
