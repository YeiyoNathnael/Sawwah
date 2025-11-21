
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataManager {

private static List<String> eventBackUpFiles = new ArrayList<>();



    /*
     * this method is assuming that the csv is written in a way that a single row
     * contain the properties of a single event event. event properties are separeted by
     * comma.
     */
    public static List<Event> loadDataFromFiles(String fileDirectory) {
        List<Event> eventList = new ArrayList<>();
        File file = new File(fileDirectory);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                // 1. Get the raw string for one event (e.g., "National Day,Dubai,2025-11-20")
                // This reads until the next newline character.
                String rawRecord = scanner.nextLine().trim();

                if (rawRecord.isEmpty())
                    continue; // Skip empty records

                String[] properties = rawRecord.split(","); // 2. Split properties by comma

                // 3. Safety check to ensure we have enough columns
                if (properties.length >= 6) {
                    String name = properties[0].trim();
                    LocalDateTime eventStartDate = LocalDateTime.parse(properties[1].trim());
                    LocalDateTime eventEndDate = LocalDateTime.parse(properties[2].trim());
                    String eventLocation = properties[3].trim();
                    String eventCategory = properties[4].trim();
                    int eventPriority = Integer.parseInt(properties[5].trim());

                    // Create and add the object
                    eventList.add(
                            new Event(name, eventStartDate, eventEndDate, eventLocation, eventCategory, eventPriority));
                } else {
                    System.err.println("Incorrect File Format " + rawRecord);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileDirectory); // Using the correct variable name
            e.printStackTrace();
        }

        return eventList;

    }

    

    // Backup Feaure putting data into a CSV file. using a time-stamp of the event
    // as key
    public static boolean saveBackup(Map<LocalDateTime, List<Event>> data, String fileDirectory) {
        File backupFile = new File(fileDirectory);
        try (BufferedWriter saveEvent = new BufferedWriter(new FileWriter(backupFile))) {

            // Register the backup file
             String path = backupFile.getAbsolutePath();
            if (!eventBackUpFiles.contains(path)) {
                eventBackUpFiles.add(path);
            }
           

            // Loop through the time-event map
            for (Map.Entry<LocalDateTime, List<Event>> entry : data.entrySet()) {
                for (Event e : entry.getValue()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(e.name).append(",")
                            .append(e.startTime).append(",")
                            .append(e.endTime).append(",")
                            .append(e.location).append(",")
                            .append(e.category).append(",")
                            .append(e.priority).append("\n");
                    saveEvent.write(sb.toString());
                   
                }
                
            }

            return true;

        } catch (IOException e) {
            System.out.print("Error:"+ e.getMessage()+ " Unable to save backup to " + fileDirectory);
            return false;
        }
    }

    public List<String> getBackUpFilesList() {
        return eventBackUpFiles;
    }

    // donwload back up files when needed
    public List<Event> loadBackup(String fileDirectory) {
        File backupFile = new File(fileDirectory);
        String path = backupFile.getAbsolutePath();
        if (!eventBackUpFiles.contains(path)) {
            System.out.println("Backup file " + fileDirectory + "not found ");
            return new ArrayList<Event>();
        }
        List<Event> loadedEvents = loadDataFromFiles(fileDirectory);
        if(loadedEvents.isEmpty()){
            System.out.println("No events found in backup file: " + fileDirectory);
        }
        return loadedEvents;


        
    }

    // return List of Events By category
    public static List<Event> getEventsByCategory(List<Event> events, String category) {
        List<Event> filteredEvents = new ArrayList<>();
        for (Event e : events) {
            if (e.category.equalsIgnoreCase(category)) {
                filteredEvents.add(e);
            }
        }
        return filteredEvents;
    }





    // status of each emirate by the number of events

    public static Map<String, Integer> getEmirateStatus(List<Event> events) {
        Map<String, Integer> emirateStatus = new HashMap<String, Integer>();

        for (Event e : events) {
            if(emirateStatus.containsKey(e.location)){
                emirateStatus.put(e.location, emirateStatus.get(e.location)+1);
                
            }
            else {
                emirateStatus.put(e.location, 1);
            }
        }

        return emirateStatus;
    }




    // trending events implemented by comparing priority and length of event

    public List<Event> getTrendingEventsEvents(List<Event> events, int n) {
        if (events == null || events.isEmpty() || n <= 0) {
            return new ArrayList<Event>();
        }
        List<Event> sortedEvents = new ArrayList<Event>(events);
        Collections.sort(sortedEvents, new EventPriorityComparator());
        return sortedEvents.subList(0, Math.min(n, sortedEvents.size()));

    }

    // a private comparator class for comparing and sorting events by priority
    private static class EventPriorityComparator implements java.util.Comparator<Event> {
        @Override
        public int compare(Event e1, Event e2) {
            if (e1.priority != e2.priority) {
                return e2.priority - e1.priority;
            }
            // if they have the same priority , compare by length of event(long Period more
            // trending)
            return Duration.between(e2.startTime, e2.endTime)
                    .compareTo(Duration.between(e1.startTime, e1.endTime));
        }
    }
}
