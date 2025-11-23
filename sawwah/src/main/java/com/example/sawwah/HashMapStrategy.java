package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HashMapStrategy extends SearchStrategy {
    
    @Override
    public Event find(String name, TreeMap<LocalDateTime, List<Event>> tree, Map<String, Event> nameIndex) {
        if (name == null) return null;
        return nameIndex.get(name.toLowerCase());
    }
}
