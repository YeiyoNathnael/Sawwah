package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.sawwah.exceptions.EmptyEventNameException;
import com.example.sawwah.exceptions.EndTimeBeforeStartException;
import com.example.sawwah.exceptions.InvalidCategoryException;
import com.example.sawwah.exceptions.InvalidEventTimeException;
import com.example.sawwah.exceptions.InvalidLocationException;
import com.example.sawwah.exceptions.InvalidPriorityException;

public class Event implements Comparable<Event> {
    String id;
    String name;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String category;
    int priority;

    public Event(String name, LocalDateTime start, LocalDateTime end,
                 String loc, String cat, int pri) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyEventNameException();
        }
        if (start == null || end == null) {
            throw new InvalidEventTimeException("Start and end time must not be null.");
        }
        if (end.isBefore(start)) {
            throw new EndTimeBeforeStartException();
        }
        if (loc == null || loc.trim().isEmpty()) {
            throw new InvalidLocationException(loc);
        }
        if (cat == null || cat.trim().isEmpty() ||
            !(cat.equalsIgnoreCase("Cultural") || cat.equalsIgnoreCase("Educational") || cat.equalsIgnoreCase("Charity"))) {
            throw new InvalidCategoryException(cat);
        }
        if (pri < 1 || pri > 5) {
            throw new InvalidPriorityException(pri);
        }
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.startTime = start;
        this.endTime = end;
        this.location = loc;
        this.category = cat;
        this.priority = pri;
    }

    @Override
    public int compareTo(Event other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        return startTime + ": " + name + " (" + location + ")";
    }
}