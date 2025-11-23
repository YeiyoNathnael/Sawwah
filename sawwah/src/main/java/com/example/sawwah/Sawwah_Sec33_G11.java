package com.example.sawwah;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.sawwah.exceptions.BackupException_Sec33_G11;
import com.example.sawwah.exceptions.BackupSaveException_Sec33_G11;
import com.example.sawwah.exceptions.CSVFormatException_Sec33_G11;
import com.example.sawwah.exceptions.DataLoadException_Sec33_G11;
import com.example.sawwah.exceptions.EmptyDataSetException_Sec33_G11;
import com.example.sawwah.exceptions.EventNotFoundException_Sec33_G11;
import com.example.sawwah.exceptions.InvalidEventException_Sec33_G11;

public class Sawwah_Sec33_G11 {
    
    private static final EventManager_Sec33_G11 system = new SawwahTree_Sec33_G11(); // Integrated: Real tree implementation
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
        try {
            List<Event_Sec33_G11> events = DataManager_Sec33_G11.loadDataFromFiles(filePath);
            if (events.isEmpty()) {
                System.out.println("✗ No events loaded. Check file format or path.");
                return;
            }
            for (Event_Sec33_G11 event : events) {
                system.addEvent(event);
            }
            System.out.println("✓ Successfully loaded " + events.size() + " events!");
        } catch (CSVFormatException_Sec33_G11 | DataLoadException_Sec33_G11 | InvalidEventException_Sec33_G11 e) {
            System.out.println("✗ Error loading data: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
        }
    }
    
    // ========== OPTION 2: ADD EVENT ==========
    private static void addEvent() {
        System.out.println("\n--- Add New Event ---");
        try {
            System.out.print("Event Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Start Time (YYYY-MM-DDTHH:MM, e.g., 2025-12-01T14:30): ");
            String startStr = scanner.nextLine().trim();
            System.out.print("End Time (YYYY-MM-DDTHH:MM): ");
            String endStr = scanner.nextLine().trim();
            System.out.print("Location (Emirate): ");
            String location = scanner.nextLine().trim();
            System.out.print("Category (Cultural/Educational/Charity): ");
            String category = scanner.nextLine().trim();
            System.out.print("Priority (1-5): ");
            int priority = Integer.parseInt(scanner.nextLine().trim());
            LocalDateTime startTime = LocalDateTime.parse(startStr, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endStr, formatter);
            Event_Sec33_G11 newEvent = new Event_Sec33_G11(name, startTime, endTime, location, category, priority);
            system.addEvent(newEvent);
            System.out.println("✓ Event added successfully!");
        } catch (DateTimeParseException e) {
            System.out.println("✗ Invalid date format. Use YYYY-MM-DDTHH:MM (e.g., 2025-12-01T14:30)");
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid number format for priority.");
        } catch (InvalidEventException_Sec33_G11 e) {
            System.out.println("✗ Invalid event: " + e.getMessage());
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
        try {
            // Get all events with this name
            List<Event_Sec33_G11> events = ((SawwahTree_Sec33_G11) system).getEventsByName(name);
            if (events.isEmpty()) {
                throw new EventNotFoundException_Sec33_G11(name);
            }
            
            if (events.size() == 1) {
                // Only one event, remove it directly
                boolean removed = system.removeEvent(name);
                if (removed) {
                    System.out.println("✓ Event '" + name + "' removed successfully!");
                }
            } else {
                // Multiple events, let user choose
                System.out.println("\nMultiple events found with name '" + name + "':");
                for (int i = 0; i < events.size(); i++) {
                    Event_Sec33_G11 e = events.get(i);
                    System.out.println((i + 1) + ". " + e.name + " (ID: " + e.id + ", Start: " + e.startTime + ", Location: " + e.location + ")");
                }
                System.out.print("Enter the number of the event to remove (1-" + events.size() + "): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice < 1 || choice > events.size()) {
                    System.out.println("✗ Invalid choice.");
                    return;
                }
                Event_Sec33_G11 selected = events.get(choice - 1);
                boolean removed = ((SawwahTree_Sec33_G11) system).removeEventById(selected.id);
                if (removed) {
                    System.out.println("✓ Event '" + selected.name + "' (ID: " + selected.id + ") removed successfully!");
                }
            }
        } catch (EventNotFoundException_Sec33_G11 e) {
            System.out.println("✗ " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("✗ Invalid number format for choice.");
        } catch (Exception e) {
            System.out.println("✗ Error removing event: " + e.getMessage());
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
        long startTime = System.nanoTime();
        try {
            Event_Sec33_G11 found = system.searchEvent(name);
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
                throw new EventNotFoundException_Sec33_G11(name);
            }
        } catch (EventNotFoundException_Sec33_G11 e) {
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("✗ " + e.getMessage());
            System.out.println("\n⏱  Search Time: " + duration + " nanoseconds (" + String.format("%.6f", duration / 1_000_000.0) + " ms)");
        } catch (Exception e) {
            System.out.println("✗ Error searching event: " + e.getMessage());
        }
    }
    
    // ========== OPTION 5: SHOW STATISTICS ==========
    private static void showStats() {
        System.out.println("\n--- Event Statistics ---");
        try {
            Map<LocalDateTime, List<Event_Sec33_G11>> rawMap = system.getRawMap();
            List<Event_Sec33_G11> allEvents = new ArrayList<>();
            for (List<Event_Sec33_G11> eventList : rawMap.values()) {
                allEvents.addAll(eventList);
            }
            if (allEvents.isEmpty()) {
                throw new EmptyDataSetException_Sec33_G11("No events in the system.");
            }
            System.out.println("\nTotal Events: " + allEvents.size());
            System.out.println("\n--- Events by Emirate ---");
            Map<String, Integer> emirateStats = DataManager_Sec33_G11.getEmirateStatus(allEvents);
            for (Map.Entry<String, Integer> entry : emirateStats.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " events");
            }
            System.out.println("\n--- Events by Category ---");
            Map<String, Integer> categoryStats = new HashMap<>();
            for (Event_Sec33_G11 e : allEvents) {
                categoryStats.put(e.category, categoryStats.getOrDefault(e.category, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : categoryStats.entrySet()) {
                System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " events");
            }
            System.out.println("\n--- Top 5 Trending Events ---");
            DataManager_Sec33_G11 dm = new DataManager_Sec33_G11();
            List<Event_Sec33_G11> trending = dm.getTrendingEventsEvents(allEvents, 5);
            int rank = 1;
            for (Event_Sec33_G11 e : trending) {
                System.out.println("  " + rank + ". " + e.name + " (Priority: " + e.priority + ", Location: " + e.location + ")");
                rank++;
            }
        } catch (EmptyDataSetException_Sec33_G11 e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error showing statistics: " + e.getMessage());
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
        Map<LocalDateTime, List<Event_Sec33_G11>> data = system.getRawMap();
        try {
            boolean success = DataManager_Sec33_G11.saveBackup(data, filePath);
            if (success) {
                System.out.println("✓ Backup saved successfully to: " + filePath);
            } else {
                throw new BackupSaveException_Sec33_G11(filePath);
            }
        } catch (BackupException_Sec33_G11 e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error during backup: " + e.getMessage());
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
        
        Map<LocalDateTime, List<Event_Sec33_G11>> rawMap = system.getRawMap();
        
        if (rawMap.isEmpty()) {
            System.out.println("  [Empty Tree - No Events]");
            return;
        }
        
        System.out.println("Root (TreeMap - Red-Black Tree)");
        System.out.println("│");
        
        List<LocalDateTime> times = new ArrayList<>(rawMap.keySet());
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
    }
}


// batch