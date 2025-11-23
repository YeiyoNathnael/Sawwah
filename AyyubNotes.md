# Search Strategy Pattern - Simple Explanation

## What It Does
Automatically picks the best search method based on how many events you have.

## Two Search Methods

### Linear Search (O(n))
- Goes through every event one by one
- Slow for big datasets
- No extra memory needed
- Used when: **Less than 200 events**

### HashMap Search (O(1))
- Looks up event instantly by name
- Super fast
- Uses extra memory for index
- Used when: **200 or more events**

## How It Works

```
Start → Linear Search (fast enough for small data)
         ↓
Add events...
         ↓
Reach 200 events → Switch to HashMap (now needs speed)
         ↓
Remove events...
         ↓
Drop below 200 → Switch back to Linear (save memory)
```

## Code Structure

```
SearchStrategy (interface)
    ├── LinearStrategy    → loops through all events
    └── HashMapStrategy   → uses nameIndex map
```

## In SawwahTree

```java
private int totalEvents = 0;              // Counter
private SearchStrategy strategy = Linear; // Start simple

addEvent() {
    // add event
    totalEvents++;
    if (totalEvents >= 200) → switch to HashMap
}

removeEvent() {
    // remove event  
    totalEvents--;
    if (totalEvents < 200) → switch to Linear
}

searchEvent(name) {
    return strategy.find(name); // Use current strategy
}
```

## Why It's Good

- **Smart**: Picks the right tool for the job
- **Automatic**: No manual switching needed
- **Efficient**: Fast for big data, memory-friendly for small data
- **Clean**: Easy to add new search methods later

## Example

```
Load 50 events   → Uses Linear (good enough)
Load 425k events → Switches to HashMap at 200
Search E599999   → Uses HashMap (instant!)
```

#
# New Features & Changes (Nov 2025)

## 1. Custom Exception Hierarchy
- Introduced 15+ custom exceptions (e.g., `InvalidEventException`, `EventNotFoundException`, `CSVFormatException`, etc.)
- All validation, file I/O, and business logic now use these exceptions for robust error handling
- Cleaner error messages and easier debugging

## 2. Event ID Uniqueness
- Every event now has a unique `id` (UUID)
- Prevents accidental collisions when adding events
- Duplicate IDs are ignored, ensuring data integrity

## 3. Duplicate Name Handling in UI
- When removing by name, if multiple events share the same name, the UI lists all and prompts for which one to remove
- Prevents accidental deletion of the wrong event
- User selects by event number or ID

## 4. Comprehensive Automated Testing
- Added `Test.java` covering all main features:
    - Data loading from CSV
    - Adding events (including duplicate and unique cases)
    - Searching (including duplicates and non-existent)
    - Removing events (with/without duplicates)
    - Statistics and analytics
    - Backup and restore
    - Tree visualization
- Ensures all features work as expected and exceptions are thrown correctly

## 5. Codebase Refactoring
- All main classes (`Event.java`, `DataManager.java`, `SawwahTree.java`, `Sawwah.java`) updated to use new exceptions and ID logic
- Improved validation and error handling throughout
- Consistent interface for event operations

## 6. Summary
- The system is now more robust, user-friendly, and maintainable
- Data integrity is enforced at every step
- All main features are covered by automated tests

---
**This update brings production-level reliability and clarity to the Sawwah event management system.**
