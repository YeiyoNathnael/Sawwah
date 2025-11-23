package com.example.sawwah;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventManager {
    void addEvent(Event e);
    boolean removeEvent(String eventName);
    Event searchEvent(String eventName);
    void updateEvent(String oldName, Event newEvent);
    void displayEvents();
    void displayEventsReverse();
    Map<LocalDateTime, List<Event>> getRawMap();
}