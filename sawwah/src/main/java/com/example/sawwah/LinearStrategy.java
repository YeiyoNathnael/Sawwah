package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LinearStrategy implements SearchStrategy {
    
    @Override
    public Event find(String name, TreeMap<LocalDateTime, List<Event>> tree, Map<String, Event> nameIndex) {
        if (name == null) return null;
        
        for (List<Event> events : tree.values()) {
            for (Event e : events) {
                if (e.name.equalsIgnoreCase(name)) {
                    return e;
                }
            }
        }
        return null;
    }
}
