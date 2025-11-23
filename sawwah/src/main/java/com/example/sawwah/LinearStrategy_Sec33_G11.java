package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LinearStrategy_Sec33_G11 extends  SearchStrategy_Sec33_G11 {
    
    @Override
    public Event_Sec33_G11 find(String name, TreeMap<LocalDateTime, List<Event_Sec33_G11>> tree, Map<String, Event_Sec33_G11> nameIndex) {
        if (name == null) return null;
        
        for (List<Event_Sec33_G11> events : tree.values()) {
            for (Event_Sec33_G11 e : events) {
                if (e.name.equalsIgnoreCase(name)) {
                    return e;
                }
            }
        }
        return null;
    }
}
