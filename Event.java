import java.time.LocalDateTime;
import java.util.UUID;

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