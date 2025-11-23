# Sawwah - UAE Event Management System

A sophisticated event management system for organizing and managing events across the seven emirates of the UAE, built with advanced data structures and design patterns.

## Features

### Core Functionality
- **Event Management**: Add, remove, update, and search events with comprehensive details (name, location, category, priority, time)
- **Chronological Organization**: Events automatically sorted by start time using Red-Black Tree (TreeMap)
- **Multi-Event Support**: Multiple events can share the same start time
- **CSV Import/Export**: Load events from CSV files and backup to CSV format
- **Event Visualization**: ASCII tree visualization showing hierarchical event structure

### Data Structures
- **Red-Black Tree**: Self-balancing binary search tree for O(log n) insertions and sorted traversal
- **HashMap Index**: O(1) event lookup by name with case-insensitive matching
- **Strategy Pattern**: Adaptive search algorithm selection based on dataset size

### Search Algorithms
- **Linear Search**: O(n) - Efficient for small datasets (<200 events), zero memory overhead
- **HashMap Search**: O(1) - Optimal for large datasets (≥200 events), uses name index
- **Adaptive Strategy**: Automatically switches between Linear and HashMap based on event count threshold
- **Performance Tracking**: Nanosecond-precision timing for search operations

### Event Categories
- **Cultural**: Heritage festivals, art exhibitions, poetry evenings
- **Educational**: Book fairs, workshops, technology summits, career fairs
- **Charity**: Beach cleanups, blood donations, community service events

### UAE Emirates Support
- Dubai
- Abu Dhabi
- Sharjah
- Ras Al Khaimah
- Ajman
- Umm Al Quwain
- Fujairah

### Statistics & Analytics
- **Events by Emirate**: Count and distribution across all seven emirates
- **Events by Category**: Cultural, Educational, Charity breakdown
- **Trending Events**: Top events ranked by priority and duration
- **Search Performance**: Detailed timing metrics for algorithm comparison

### Design Patterns
- **Strategy Pattern**: Pluggable search algorithms with automatic selection
- **Template Method**: Consistent event processing workflow
- **Interface Segregation**: Clean EventManager contract

## Project Structure

```
Sawwah/
├── sawwah/
│   ├── src/main/java/com/example/sawwah/
│   │   ├── Event.java                 # Event data model
│   │   ├── EventManager.java          # Core interface
│   │   ├── SawwahTree.java            # Red-Black Tree implementation
│   │   ├── DataManager.java           # CSV I/O and analytics
│   │   ├── Sawwah.java                # Main UI
│   │   ├── SearchStrategy.java        # Strategy interface
│   │   ├── LinearStrategy.java        # O(n) search
│   │   ├── HashMapStrategy.java       # O(1) search
│   │   └── FakeManager.java           # Stub for testing
│   └── pom.xml
├── TestData.csv                       # Sample event data
├── RandomEvent.java                   # Event generator utility
└── test.java                          # Search benchmarking

```

## Technical Highlights

### Adaptive Search Strategy
- Threshold: 200 events
- Below 200: Uses Linear search (no memory overhead)
- At/Above 200: Switches to HashMap search (O(1) lookup)
- Bulk loading: Single strategy update after loading all events

### Performance Optimizations
- **Batch Event Loading**: `batchAddEvents()` method prevents 425k+ strategy recalculations
- **Case-Insensitive Search**: Lowercase normalization for consistent matching
- **Automatic Rebalancing**: TreeMap handles Red-Black Tree rotations internally
- **Lazy Cleanup**: Empty time buckets removed after event deletion

### Event Model
```java
Event {
    String id;              // Auto-generated UUID
    String name;            // Event name
    LocalDateTime startTime;// Start timestamp
    LocalDateTime endTime;  // End timestamp
    String location;        // UAE Emirate
    String category;        // Cultural/Educational/Charity
    int priority;           // 1-5 scale
}
```

## Data Format (CSV)

```
Event Name,Start Time,End Time,Location,Category,Priority
Dubai Food Festival,2025-02-10T17:00,2025-02-10T22:00,Dubai,Cultural,4
Abu Dhabi Book Fair,2025-05-01T09:00,2025-05-01T18:00,Abu Dhabi,Educational,5
```

## Object-Oriented Design

- **Encapsulation**: Private fields with controlled access through EventManager interface
- **Polymorphism**: SearchStrategy implementations with different algorithms
- **Abstraction**: EventManager interface hides implementation details
- **Single Responsibility**: Separate classes for data model, storage, I/O, and UI
- **Open/Closed**: Easy to add new search strategies without modifying SawwahTree

## Future Enhancements

- Binary search optimization for sorted event lists
- Observer pattern for event notifications
- Decorator pattern for enhanced functionality (caching, logging, timing)
- Additional tree implementations (AVL, B-Tree) for comparison
- Event filtering by date range, location, or category
- Graphical UI using JavaFX