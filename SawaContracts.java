import java.time.LocalDateTime;
import java.util.*;

// 1. The Data Object (Everyone uses this)
class Event implements Comparable<Event> {
    String id; // Unique ID to handle duplicates
    String name;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String location;
    String category; // "Cultural", "Educational", "Charity"
    int priority;

    // Constructor
    public Event(String name, LocalDateTime start, LocalDateTime end, String loc, String cat, int pri) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.startTime = start;
        this.endTime = end;
        this.location = loc;
        this.category = cat;
        this.priority = pri;
    }

    // Comparable for Tree Sorting (Chronological)
    @Override
    public int compareTo(Event other) {
        return this.startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        return startTime + ": " + name + " (" + location + ")";
    }
}

// 2. The Logic Interface (Member 1 implements this, Member 3 calls this)
interface EventManager {
    void addEvent(Event e);
    boolean removeEvent(String eventName);
    Event searchEvent(String eventName);
    void updateEvent(String oldName, Event newEvent);
    void displayEvents();            // In-order
    void displayEventsReverse();     // Reverse-order

    Map<LocalDateTime, List<Event>> getRawMap(); // For Member 2's backup feature
}
