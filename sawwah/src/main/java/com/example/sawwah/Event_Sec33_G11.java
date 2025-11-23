package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.sawwah.exceptions.EmptyEventNameException_Sec33_G11;
import com.example.sawwah.exceptions.EndTimeBeforeStartException_Sec33_G11;
import com.example.sawwah.exceptions.InvalidCategoryException_Sec33_G11;
import com.example.sawwah.exceptions.InvalidEventTimeException_Sec33_G11;
import com.example.sawwah.exceptions.InvalidLocationException_Sec33_G11;
import com.example.sawwah.exceptions.InvalidPriorityException_Sec33_G11;

public class Event_Sec33_G11 implements Comparable<Event_Sec33_G11> {
    String id;
    String name;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String category;
    int priority;

    public Event_Sec33_G11(String name, LocalDateTime start, LocalDateTime end,
                 String loc, String cat, int pri) {
        if (name == null || name.trim().isEmpty()) {
            throw new EmptyEventNameException_Sec33_G11();
        }
        if (start == null || end == null) {
            throw new InvalidEventTimeException_Sec33_G11("Start and end time must not be null.");
        }
        if (end.isBefore(start)) {
            throw new EndTimeBeforeStartException_Sec33_G11();
        }
        if (loc == null || loc.trim().isEmpty()) {
            throw new InvalidLocationException_Sec33_G11(loc);
        }
        if (cat == null || cat.trim().isEmpty() ||
            !(cat.equalsIgnoreCase("Cultural") || cat.equalsIgnoreCase("Educational") || cat.equalsIgnoreCase("Charity"))) {
            throw new InvalidCategoryException_Sec33_G11(cat);
        }
        if (pri < 1 || pri > 5) {
            throw new InvalidPriorityException_Sec33_G11(pri);
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
    public int compareTo(Event_Sec33_G11 other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        return startTime + ": " + name + " (" + location + ")";
    }
}