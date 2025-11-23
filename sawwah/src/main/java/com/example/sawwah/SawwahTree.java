
package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.sawwah.exceptions.EventNotFoundException;



public class SawwahTree implements EventManager {

    // Index by event id for uniqueness
    private Map<String, Event> idIndex = new HashMap<>();
    // Keep nameIndex for name-based search (returns first match)
    private Map<String, Event> nameIndex = new HashMap<>();
    private TreeMap<LocalDateTime, List<Event>> tree = new TreeMap<>();
    private static int totalEvents = 0;
    private SearchStrategy strategy = new LinearStrategy();
    private static final int THRESHOLD = 200;

    public static void settotalEvents(int number){
        totalEvents += number;
    }
    @Override
    public void addEvent(Event e) {
        if (e == null) return;
        // Prevent duplicate by id
        if (idIndex.containsKey(e.id)) return;

        List<Event> list = tree.get(e.startTime);
        if (list == null) {
            list = new ArrayList<>();
            tree.put(e.startTime, list);
        }
        list.add(e);
        idIndex.put(e.id, e);
        nameIndex.put(e.name.toLowerCase(), e);
        totalEvents++;
        updateStrategy();
    }

    @Override
    public boolean removeEvent(String name) {
        if (name == null) throw new EventNotFoundException("null");

        Event target = searchEvent(name);
        if (target == null) {
            throw new EventNotFoundException(name);
        }

        List<Event> list = tree.get(target.startTime);
        if (list != null) {
            list.remove(target);
            if (list.isEmpty()) {
                tree.remove(target.startTime);
            }
        }
        nameIndex.remove(name.toLowerCase());
        idIndex.remove(target.id);
        
        totalEvents--;
        updateStrategy();
        return true;
    }


    @Override
    public Event searchEvent(String name) {
        return strategy.find(name, tree, nameIndex);
    }

     @Override
    public void updateEvent(String oldName, Event newEvent) {
        removeEvent(oldName);
        addEvent(newEvent);
    }
    // Get all events with the same name
    public List<Event> getEventsByName(String name) {
        if (name == null) return new ArrayList<>();
        String nameKey = name.toLowerCase();
        List<Event> events = new ArrayList<>();
        for (Event e : nameIndex.values()) {
            if (e.name.equalsIgnoreCase(name)) {
                events.add(e);
            }
        }
        return events;
    }

    // Remove by ID
    public boolean removeEventById(String id) {
        if (id == null) throw new EventNotFoundException("null");
        Event target = idIndex.get(id);
        if (target == null) throw new EventNotFoundException(id);
        List<Event> list = tree.get(target.startTime);
        if (list != null) {
            list.remove(target);
            if (list.isEmpty()) {
                tree.remove(target.startTime);
            }
        }
        nameIndex.remove(target.name.toLowerCase());
        idIndex.remove(id);
        totalEvents--;
        updateStrategy();
        return true;
    }

    @Override
    public void displayEvents() {
        if (tree.isEmpty()) {
            System.out.println("No events");
            return;
        }

        for (LocalDateTime t : tree.keySet()) {
            for (Event e : tree.get(t)) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void displayEventsReverse() {
        if (tree.isEmpty()) {
            System.out.println("No events");
            return;
        }

        for (LocalDateTime t : tree.descendingKeySet()) {
            for (Event e : tree.get(t)) {
                System.out.println(e);
            }
        }
    }

    @Override
    public Map<LocalDateTime, List<Event>> getRawMap() {
        return tree;
    }
    
    
    private void updateStrategy() {
        if (totalEvents >= THRESHOLD && !(strategy instanceof HashMapStrategy)) {
            strategy = new HashMapStrategy();
        } else if (totalEvents < THRESHOLD && !(strategy instanceof LinearStrategy)) {
            strategy = new LinearStrategy();
        }
    }
}