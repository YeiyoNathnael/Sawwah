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

That's it. Small data = simple search. Big data = fast search. Pattern handles the switching.
