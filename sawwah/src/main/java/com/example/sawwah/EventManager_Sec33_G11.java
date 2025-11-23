package com.example.sawwah;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventManager_Sec33_G11 {
    void addEvent(Event_Sec33_G11 e);
    boolean removeEvent(String eventName);
    Event_Sec33_G11 searchEvent(String eventName);
    void updateEvent(String oldName, Event_Sec33_G11 newEvent);
    void displayEvents();
    void displayEventsReverse();
    Map<LocalDateTime, List<Event_Sec33_G11>> getRawMap();
}