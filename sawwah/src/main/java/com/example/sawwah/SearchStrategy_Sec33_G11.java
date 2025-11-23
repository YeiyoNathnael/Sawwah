package com.example.sawwah;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class SearchStrategy_Sec33_G11 {
    public abstract Event_Sec33_G11 find(String name, TreeMap<LocalDateTime, List<Event_Sec33_G11>> tree, Map<String, Event_Sec33_G11> nameIndex);
}
