package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.*;



public class SawwahTree implements EventManager {

    private Map<String, Event> nameIndex = new HashMap<>();
    private TreeMap<LocalDateTime, List<Event>> tree = new TreeMap<>();
    private int totalEvents = 0;
    private SearchStrategy strategy = new LinearStrategy();
    private static final int THRESHOLD = 200;
    @Override
    public void addEvent(Event e) {
        if (e == null) return;

        List<Event> list = tree.get(e.startTime);
        if (list == null) {
            list = new ArrayList<>();
            tree.put(e.startTime, list);
        }
        list.add(e);
        nameIndex.put(e.name.toLowerCase(), e);
        totalEvents++;
        updateStrategy();
    }

    @Override
    public boolean removeEvent(String name) {
        if (name == null) return false;

        for (LocalDateTime time : tree.keySet()) {
            List<Event> list = tree.get(time);

            Event target = null;
            for (Event e : list) {
                if (e.name.equalsIgnoreCase(name)) {
                    target = e;
                    break;
                }
            }

            if (target != null) {
                list.remove(target);
                if (list.isEmpty()) {
                    tree.remove(time);
                }
                nameIndex.remove(name.toLowerCase());
                totalEvents--;
                updateStrategy();
                return true;
            }
        }
        return false;
    }



    @Override
    public Event searchEvent(String name) {
        return strategy.find(name, tree, nameIndex);
    }

    // @Override
    // public Event searchEvent(String name) {
    //     if (name == null) return null;

    //     for (List<Event> list : tree.values()) {
    //         for (Event e : list) {
    //             if (e.name.equalsIgnoreCase(name)) {
    //                 return e;
    //             }
    //         }
    //     }
    //     return null;
    // }

    @Override
    public void updateEvent(String oldName, Event newEvent) {
        removeEvent(oldName);
        addEvent(newEvent);
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
    
    public void batchAddEvents(List<Event> events) {
        for (Event e : events) {
            if (e == null) continue;
            
            List<Event> list = tree.get(e.startTime);
            if (list == null) {
                list = new ArrayList<>();
                tree.put(e.startTime, list);
            }
            list.add(e);
            nameIndex.put(e.name.toLowerCase(), e);
            totalEvents++;
        }
        updateStrategy();
    }
    
    private void updateStrategy() {
        if (totalEvents >= THRESHOLD && !(strategy instanceof HashMapStrategy)) {
            strategy = new HashMapStrategy();
        } else if (totalEvents < THRESHOLD && !(strategy instanceof LinearStrategy)) {
            strategy = new LinearStrategy();
        }
    }
}