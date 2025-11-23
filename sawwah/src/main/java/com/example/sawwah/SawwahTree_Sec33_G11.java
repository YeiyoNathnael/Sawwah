
package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.sawwah.exceptions.EventNotFoundException_Sec33_G11;



public class SawwahTree_Sec33_G11 implements EventManager_Sec33_G11 {

    // Index by event id for uniqueness
    private Map<String, Event_Sec33_G11> idIndex = new HashMap<>();
    // Keep nameIndex for name-based search (returns first match)
    private Map<String, Event_Sec33_G11> nameIndex = new HashMap<>();
    private TreeMap<LocalDateTime, List<Event_Sec33_G11>> tree = new TreeMap<>();
    private static int totalEvents = 0;
    private SearchStrategy_Sec33_G11 strategy = new LinearStrategy_Sec33_G11();
    private static final int THRESHOLD = 200;

    public static void settotalEvents(int number){
        totalEvents += number;
    }
    @Override
    public void addEvent(Event_Sec33_G11 e) {
        if (e == null) return;
        // Prevent duplicate by id
        if (idIndex.containsKey(e.id)) return;

        List<Event_Sec33_G11> list = tree.get(e.startTime);
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
        if (name == null) throw new EventNotFoundException_Sec33_G11("null");

        Event_Sec33_G11 target = searchEvent(name);
        if (target == null) {
            throw new EventNotFoundException_Sec33_G11(name);
        }

        List<Event_Sec33_G11> list = tree.get(target.startTime);
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
    public Event_Sec33_G11 searchEvent(String name) {
        return strategy.find(name, tree, nameIndex);
    }

     @Override
    public void updateEvent(String oldName, Event_Sec33_G11 newEvent) {
        removeEvent(oldName);
        addEvent(newEvent);
    }
    // Get all events with the same name
    public List<Event_Sec33_G11> getEventsByName(String name) {
        if (name == null) return new ArrayList<>();
        String nameKey = name.toLowerCase();
        List<Event_Sec33_G11> events = new ArrayList<>();
        for (Event_Sec33_G11 e : nameIndex.values()) {
            if (e.name.equalsIgnoreCase(name)) {
                events.add(e);
            }
        }
        return events;
    }

    // Remove by ID
    public boolean removeEventById(String id) {
        if (id == null) throw new EventNotFoundException_Sec33_G11("null");
        Event_Sec33_G11 target = idIndex.get(id);
        if (target == null) throw new EventNotFoundException_Sec33_G11(id);
        List<Event_Sec33_G11> list = tree.get(target.startTime);
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
            for (Event_Sec33_G11 e : tree.get(t)) {
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
            for (Event_Sec33_G11 e : tree.get(t)) {
                System.out.println(e);
            }
        }
    }

    @Override
    public Map<LocalDateTime, List<Event_Sec33_G11>> getRawMap() {
        return tree;
    }
    
    
    private void updateStrategy() {
        if (totalEvents >= THRESHOLD && !(strategy instanceof HashMapStrategy_Sec33_G11)) {
            strategy = new HashMapStrategy_Sec33_G11();
        } else if (totalEvents < THRESHOLD && !(strategy instanceof LinearStrategy_Sec33_G11)) {
            strategy = new LinearStrategy_Sec33_G11();
        }
    }
}