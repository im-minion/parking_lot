package io.vaibhav.auto.parker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Floor {
    private int MAX_SIZE = 0;
    // Available slots list
    private ArrayList<Integer> availableSlotList;
    // Map of Slot, Car
    private Map<String, Car> slotCarMap = new HashMap<>();

    private boolean isFloorFull = false;

    private String level;


    public ArrayList<Integer> getAvailableSlotList() {
        return availableSlotList;
    }

    public void setAvailableSlotList(ArrayList<Integer> availableSlotList) {
        this.availableSlotList = availableSlotList;
    }

    public Map<String, Car> getSlotCarMap() {
        return slotCarMap;
    }

    public void setSlotCarMap(Map<String, Car> slotCarMap) {
        this.slotCarMap = slotCarMap;
    }

    public boolean isFloorFull() {
        return isFloorFull;
    }

    public void setFloorFull(boolean floorFull) {
        isFloorFull = floorFull;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
