package io.vaibhav.auto.parker.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingSpace {

    private ArrayList<Floor> floors;

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }
}
