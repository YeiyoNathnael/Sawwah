package com.example.sawwah;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Sawwah {
    
    private static final EventManager system = new SawwahTree(); // Integrated: Real tree implementation
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║     SAWWAH Event Management System        ║");
        System.out.println("║     UAE Events Organizer                  ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    loadData();
                    break;
                case 2:
                    addEvent();
                    break;
                case 3:
                    removeEvent();
                    break;
                case 4:
                    searchEvent();
                    break;
                case 5:
                    showStats();
                    break;
                case 6:
                    backup();
                    break;
                case 7:
                    displayEvents();
                    break;
                case 8:
                    printEventTree();
                    break;
                case 0:
                    System.out.println("\n✓ Exiting Sawwah. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n✗ Invalid option. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(45));
        System.out.println("              MAIN MENU");
        System.out.println("=".repeat(45));
        System.out.println("1. Load Data from CSV");
        System.out.println("2. Add Event");
        System.out.println("3. Remove Event");
        System.out.println("4. Search Event");
        System.out.println("5. Show Statistics");
        System.out.println("6. Backup Events");
        System.out.println("7. Display All Events");
        System.out.println("8. View Event Tree (Visualization)");
        System.out.println("0. Exit");
        System.out.println("=".repeat(45));
        System.out.print("Enter your choice: ");
    }
    
    private static int getUserChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    // ========== OPTION 1: LOAD DATA ==========
    private static void loadData() {
        System.out.println("\n--- Load Data from CSV ---");
        System.out.print("Enter CSV file path (or press Enter for 'TestData.csv'): ");
        String filePath = scanner.nextLine().trim();
        
        if (filePath.isEmpty()) {
            filePath = "/home/nyeiyo/Sawwah/TestData.csv";
        }
        
        System.out.println("Loading events from: " + filePath);
        List<Event> events = DataManager.loadDataFromFiles(filePath);
        
        if (events.isEmpty()) {
            System.out.println("✗ No events loaded. Check file format or path.");
            return;
        }
        
        for (Event event : events) {
            system.addEvent(event);
        }
        
        System.out.println("✓ Successfully loaded " + events.size() + " events!");
    }
    
    // ========== OPTION 2: ADD EVENT ==========
    private static void addEvent() {
        System.out.println("\n--- Add New Event ---");
        
        try {
            System.out.print("Event Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("✗ Event name cannot be empty.");
                return;
            }
            
            System.out.print("Start Time (YYYY-MM-DDTHH:MM, e.g., 2025-12-01T14:30): ");
            String startStr = scanner.nextLine().trim();
            LocalDateTime startTime = LocalDateTime.parse(startStr, formatter);
            
            System.out.print("End Time (YYYY-MM-DDTHH:MM): ");
            String endStr = scanner.nextLine().trim();
            LocalDateTime endTime = LocalDateTime.parse(endStr, formatter);
            
            if (endTime.isBefore(startTime)) {
                System.out.println("✗ End time cannot be before start time.");
                return;
            }
            
            System.out.print("Location (Emirate): ");
            String location = scanner.nextLine().trim();
            
            System.out.print("Category (Cultural/Educational/Charity): ");
            String category = scanner.nextLine().trim();
            
            System.out.print("Priority (1-5): ");
            int priority = Integer.parseInt(scanner.nextLine().trim());
            if (priority < 1 || priority > 5) {
                System.out.println("✗ Priority must be between 1 and 5.");
                return;
            }
            
            Event newEvent = new Event(name, startTime, endTime, location, category, priority);
            system.addEvent(newEvent);
            System.out.println("✓ Event added successfully!");
            
        } catch (DateTimeParseException e) {
            System.out.println("✗ Invalid date format. Use YYYY-MM-DDTHH:MM (e.g., 2025-12-01T14:30)");
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid number format for priority.");
        } catch (Exception e) {
            System.out.println("✗ Error adding event: " + e.getMessage());
        }
    }
    
    // ========== OPTION 3: REMOVE EVENT ==========
    private static void removeEvent() {
        System.out.println("\n--- Remove Event ---");
        System.out.print("Enter event name to remove: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("✗ Event name cannot be empty.");
            return;
        }
        
        boolean removed = system.removeEvent(name);
        if (removed) {
            System.out.println("✓ Event '" + name + "' removed successfully!");
        } else {
            System.out.println("✗ Event '" + name + "' not found.");
        }
    }
    
    // ========== OPTION 4: SEARCH EVENT ==========
    private static void searchEvent() {
        System.out.println("\n--- Search Event ---");
        System.out.print("Enter event name to search: ");
        String name = scanner.nextLine().trim();
        
        if (name.isEmpty()) {
            System.out.println("✗ Event name cannot be empty.");
            return;
        }
        
        // Start timing
        long startTime = System.nanoTime();
        
        Event found = system.searchEvent(name);
        
        // End timing
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        
        if (found != null) {
            System.out.println("\n✓ Event Found:");
            System.out.println("  Name:     " + found.name);
            System.out.println("  Start:    " + found.startTime);
            System.out.println("  End:      " + found.endTime);
            System.out.println("  Location: " + found.location);
            System.out.println("  Category: " + found.category);
            System.out.println("  Priority: " + found.priority);
            System.out.println("  ID:       " + found.id);
            System.out.println("\n⏱  Search Time: " + duration + " nanoseconds (" + String.format("%.6f", duration / 1_000_000.0) + " ms)");
        } else {
            System.out.println("✗ Event '" + name + "' not found.");
            System.out.println("\n⏱  Search Time: " + duration + " nanoseconds (" + String.format("%.6f", duration / 1_000_000.0) + " ms)");
        }
    }
    
    // ========== OPTION 5: SHOW STATISTICS ==========
    private static void showStats() {
        System.out.println("\n--- Event Statistics ---");
        
        // Get all events from the system
        Map<LocalDateTime, List<Event>> rawMap = system.getRawMap();
        List<Event> allEvents = new ArrayList<>();
        for (List<Event> eventList : rawMap.values()) {
            allEvents.addAll(eventList);
        }
        
        if (allEvents.isEmpty()) {
            System.out.println("✗ No events in the system.");
            return;
        }
        
        System.out.println("\nTotal Events: " + allEvents.size());
        
        // Emirate Statistics
        System.out.println("\n--- Events by Emirate ---");
        Map<String, Integer> emirateStats = DataManager.getEmirateStatus(allEvents);
        for (Map.Entry<String, Integer> entry : emirateStats.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " events");
        }
        
        // Category Statistics
        System.out.println("\n--- Events by Category ---");
        Map<String, Integer> categoryStats = new HashMap<>();
        for (Event e : allEvents) {
            categoryStats.put(e.category, categoryStats.getOrDefault(e.category, 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : categoryStats.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " events");
        }
        
        // Trending Events
        System.out.println("\n--- Top 5 Trending Events ---");
        DataManager dm = new DataManager();
        List<Event> trending = dm.getTrendingEventsEvents(allEvents, 5);
        int rank = 1;
        for (Event e : trending) {
            System.out.println("  " + rank + ". " + e.name + " (Priority: " + e.priority + ", Location: " + e.location + ")");
            rank++;
        }
    }
    
    // ========== OPTION 6: BACKUP ==========
    private static void backup() {
        System.out.println("\n--- Backup Events ---");
        System.out.print("Enter backup file path (e.g., backup_events.csv): ");
        String filePath = scanner.nextLine().trim();
        
        if (filePath.isEmpty()) {
            System.out.println("✗ File path cannot be empty.");
            return;
        }
        
        Map<LocalDateTime, List<Event>> data = system.getRawMap();
        boolean success = DataManager.saveBackup(data, filePath);
        
        if (success) {
            System.out.println("✓ Backup saved successfully to: " + filePath);
        } else {
            System.out.println("✗ Failed to save backup.");
        }
    }
    
    // ========== OPTION 7: DISPLAY EVENTS ==========
    private static void displayEvents() {
        System.out.println("\n--- All Events (Chronological) ---");
        system.displayEvents();
    }
    
    // ========== OPTION 8: TREE VISUALIZATION ==========
    private static void printEventTree() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║           EVENT TREE VISUALIZATION                        ║");
        System.out.println("║           (Chronological Red-Black Tree)                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        Map<LocalDateTime, List<Event>> rawMap = system.getRawMap();
        
        if (rawMap.isEmpty()) {
            System.out.println("  [Empty Tree - No Events]");
            return;
        }
        
        System.out.println("Root (TreeMap - Red-Black Tree)");
        System.out.println("│");
        
        List<LocalDateTime> times = new ArrayList<>(rawMap.keySet());
        for (int i = 0; i < times.size(); i++) {
            LocalDateTime time = times.get(i);
            List<Event> events = rawMap.get(time);
            boolean isLast = (i == times.size() - 1);
            
            String connector = isLast ? "└── " : "├── ";
            String childPrefix = isLast ? "    " : "│   ";
            
            System.out.println(connector + " " + time);
            
            for (int j = 0; j < events.size(); j++) {
                Event e = events.get(j);
                boolean isLastEvent = (j == events.size() - 1);
                String eventConnector = isLastEvent ? "└── " : "├── ";
                
                System.out.println(childPrefix + eventConnector + " " + e.name + " (" + e.location + ", " + e.category + ", Priority: " + e.priority + ")");
            }
        }
        
        System.out.println("\n✓ Total Timestamps: " + rawMap.size());
        int totalEvents = 0;
        for (List<Event> list : rawMap.values()) {
            totalEvents += list.size();
        }
        System.out.println("✓ Total Events: " + totalEvents);
    }
}
