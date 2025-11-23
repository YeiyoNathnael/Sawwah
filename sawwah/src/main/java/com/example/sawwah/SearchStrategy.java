package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public interface SearchStrategy {
    Event find(String name, TreeMap<LocalDateTime, List<Event>> tree, Map<String, Event> nameIndex);
}
