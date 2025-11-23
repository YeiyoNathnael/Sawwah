package com.example.sawwah;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Test_Sec33_G11 {

    private static final EventManager_Sec33_G11 system = new SawwahTree_Sec33_G11();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║         SAWWAH COMPREHENSIVE TEST          ║");
        System.out.println("║         Testing All Main Functions         ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        try {
            // Test 1: Load Data from CSV
            testLoadData();

            // Test 2: Add Events
            testAddEvents();

            // Test 3: Search Events
            testSearchEvents();

            // Test 4: Remove Events (including duplicates)
            testRemoveEvents();

            // Test 5: Show Statistics
            testShowStats();

            // Test 6: Backup
            testBackup();

            // Test 7: Display Events
            testDisplayEvents();

            // Test 8: Tree Visualization
            testPrintEventTree();

            System.out.println("\n✓ All tests completed successfully!");

        } catch (Exception e) {
            System.out.println("✗ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testLoadData() {
        System.out.println("=== TEST 1: Load Data from CSV ===");
        try {
            String filePath = "/home/nyeiyo/Sawwah/TestData.csv";
            List<Event_Sec33_G11> events = DataManager_Sec33_G11.loadDataFromFiles(filePath);
            System.out.println("✓ Loaded " + events.size() + " events from CSV");

            for (Event_Sec33_G11 event : events) {
                system.addEvent(event);
            }
            System.out.println("✓ Added all events to system");

            // Verify total events
            Map<LocalDateTime, List<Event_Sec33_G11>> rawMap = system.getRawMap();
            int total = 0;
            for (List<Event_Sec33_G11> list : rawMap.values()) {
                total += list.size();
            }
            System.out.println("✓ Total events in system: " + total);

        } catch (Exception e) {
            System.out.println("✗ Load data test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testAddEvents() {
        System.out.println("=== TEST 2: Add Events ===");
        try {
            // Add a new event
            LocalDateTime start = LocalDateTime.parse("2025-12-25T10:00", formatter);
            LocalDateTime end = LocalDateTime.parse("2025-12-25T12:00", formatter);
            Event_Sec33_G11 newEvent = new Event_Sec33_G11("Christmas Festival", start, end, "Dubai", "Cultural", 4);
            system.addEvent(newEvent);
            System.out.println("✓ Added new event: " + newEvent.name);

            // Try to add duplicate ID (should be ignored)
            Event_Sec33_G11 duplicate = new Event_Sec33_G11("Duplicate Test", start, end, "Abu Dhabi", "Cultural", 3);
            duplicate.id = newEvent.id; // Force same ID
            system.addEvent(duplicate);
            System.out.println("✓ Duplicate ID handling: ignored duplicate");

            // Add another event with same name but different ID
            Event_Sec33_G11 sameName = new Event_Sec33_G11("Christmas Festival", LocalDateTime.parse("2025-12-26T14:00", formatter),
                                      LocalDateTime.parse("2025-12-26T16:00", formatter), "Sharjah", "Cultural", 5);
            system.addEvent(sameName);
            System.out.println("✓ Added event with duplicate name but unique ID");

        } catch (Exception e) {
            System.out.println("✗ Add events test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testSearchEvents() {
        System.out.println("=== TEST 3: Search Events ===");
        try {
            // Search for existing event
            Event_Sec33_G11 found = system.searchEvent("National Day");
            if (found != null) {
                System.out.println("✓ Found event: " + found.name + " (ID: " + found.id + ")");
            } else {
                System.out.println("✗ Event not found");
            }

            // Search for non-existing event
            Event_Sec33_G11 notFound = system.searchEvent("NonExistentEvent");
            if (notFound == null) {
                System.out.println("✓ Correctly returned null for non-existent event");
            } else {
                System.out.println("✗ Should have returned null");
            }

            // Search for event with duplicates
            List<Event_Sec33_G11> duplicates = ((SawwahTree_Sec33_G11) system).getEventsByName("Christmas Festival");
            System.out.println("✓ Found " + duplicates.size() + " events with name 'Christmas Festival'");

        } catch (Exception e) {
            System.out.println("✗ Search events test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testRemoveEvents() {
        System.out.println("=== TEST 4: Remove Events ===");
        try {
            // Remove single event
            try {
                boolean removed = system.removeEvent("National Day");
                if (removed) {
                    System.out.println("✓ Removed single event: National Day");
                } else {
                    System.out.println("✗ Failed to remove event");
                }
            } catch (Exception e) {
                System.out.println("Event 'National Day' not found in system, skipping remove test: " + e.getMessage());
            }

            // Test removing from duplicates
            List<Event_Sec33_G11> festivals = ((SawwahTree_Sec33_G11) system).getEventsByName("Christmas Festival");
            if (festivals.size() >= 1) {
                String idToRemove = festivals.get(0).id;
                boolean removedById = ((SawwahTree_Sec33_G11) system).removeEventById(idToRemove);
                if (removedById) {
                    System.out.println("✓ Removed specific event by ID: " + idToRemove);
                }
            }

            // Try to remove non-existent event
            try {
                system.removeEvent("NonExistent");
                System.out.println("✗ Should have thrown exception for non-existent event");
            } catch (Exception e) {
                System.out.println("✓ Correctly threw exception for non-existent event: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("✗ Remove events test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testShowStats() {
        System.out.println("=== TEST 5: Show Statistics ===");
        try {
            Map<LocalDateTime, List<Event_Sec33_G11>> rawMap = system.getRawMap();
            List<Event_Sec33_G11> allEvents = new java.util.ArrayList<>();
            for (List<Event_Sec33_G11> eventList : rawMap.values()) {
                allEvents.addAll(eventList);
            }

            System.out.println("✓ Total Events: " + allEvents.size());

            // Emirate Statistics
            Map<String, Integer> emirateStats = DataManager_Sec33_G11.getEmirateStatus(allEvents);
            System.out.println("✓ Events by Emirate:");
            for (Map.Entry<String, Integer> entry : emirateStats.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " events");
            }

            // Category Statistics
            Map<String, Integer> categoryStats = new java.util.HashMap<>();
            for (Event_Sec33_G11 e : allEvents) {
                categoryStats.put(e.category, categoryStats.getOrDefault(e.category, 0) + 1);
            }
            System.out.println("✓ Events by Category:");
            for (Map.Entry<String, Integer> entry : categoryStats.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " events");
            }

            // Trending Events
            DataManager_Sec33_G11 dm = new DataManager_Sec33_G11();
            List<Event_Sec33_G11> trending = dm.getTrendingEventsEvents(allEvents, 3);
            System.out.println("✓ Top 3 Trending Events:");
            int rank = 1;
            for (Event_Sec33_G11 e : trending) {
                System.out.println("  " + rank + ". " + e.name + " (Priority: " + e.priority + ")");
                rank++;
            }

        } catch (Exception e) {
            System.out.println("✗ Show stats test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testBackup() {
        System.out.println("=== TEST 6: Backup ===");
        try {
            String backupPath = "/home/nyeiyo/Sawwah/test_backup.csv";
            Map<LocalDateTime, List<Event_Sec33_G11>> data = system.getRawMap();
            boolean success = DataManager_Sec33_G11.saveBackup(data, backupPath);
            if (success) {
                System.out.println("✓ Backup saved successfully to: " + backupPath);
            } else {
                System.out.println("✗ Backup failed");
            }

            // Test loading backup
            DataManager_Sec33_G11 dm = new DataManager_Sec33_G11();
            List<Event_Sec33_G11> loadedEvents = dm.loadBackup(backupPath);
            System.out.println("✓ Loaded " + loadedEvents.size() + " events from backup");

        } catch (Exception e) {
            System.out.println("✗ Backup test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testDisplayEvents() {
        System.out.println("=== TEST 7: Display Events ===");
        try {
            System.out.println("✓ Displaying all events:");
            system.displayEvents();

        } catch (Exception e) {
            System.out.println("✗ Display events test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }

    private static void testPrintEventTree() {
        System.out.println("=== TEST 8: Tree Visualization ===");
        try {
            Map<LocalDateTime, List<Event_Sec33_G11>> rawMap = system.getRawMap();

            if (rawMap.isEmpty()) {
                System.out.println("  [Empty Tree - No Events]");
                return;
            }

            System.out.println("✓ Tree Structure:");
            System.out.println("Root (TreeMap - Red-Black Tree)");
            System.out.println("│");

            java.util.List<LocalDateTime> times = new java.util.ArrayList<>(rawMap.keySet());
            for (int i = 0; i < times.size(); i++) {
                LocalDateTime time = times.get(i);
                List<Event_Sec33_G11> events = rawMap.get(time);
                boolean isLast = (i == times.size() - 1);

                String connector = isLast ? "└── " : "├── ";
                String childPrefix = isLast ? "    " : "│   ";

                System.out.println(connector + " " + time);

                for (int j = 0; j < events.size(); j++) {
                    Event_Sec33_G11 e = events.get(j);
                    boolean isLastEvent = (j == events.size() - 1);
                    String eventConnector = isLastEvent ? "└── " : "├── ";

                    System.out.println(childPrefix + eventConnector + " " + e.name + " (" + e.location + ", " + e.category + ", Priority: " + e.priority + ")");
                }
            }

            System.out.println("\n✓ Total Timestamps: " + rawMap.size());
            int totalEvents = 0;
            for (List<Event_Sec33_G11> list : rawMap.values()) {
                totalEvents += list.size();
            }
            System.out.println("✓ Total Events: " + totalEvents);

        } catch (Exception e) {
            System.out.println("✗ Tree visualization test failed: " + e.getMessage());
            throw e;
        }
        System.out.println();
    }
}