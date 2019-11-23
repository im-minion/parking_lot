package io.vaibhav.auto.parker.model;

public class Vehicle {
    private String registrationNo;
    private String color;
    private String slotIndex;
    private VehicleSize vehicleSize;

    public Vehicle(String registrationNo, String color, String slotIndex, VehicleSize vehicleSize) {
        this.registrationNo = registrationNo;
        this.color = color;
        this.slotIndex = slotIndex;
        this.vehicleSize = vehicleSize;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNo='" + registrationNo + '\'' +
                ", color='" + color + '\'' +
                ", slotIndex='" + slotIndex + '\'' +
                '}';
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSlotIndex() {
        return slotIndex;
    }

    public void setSlotIndex(String slotIndex) {
        this.slotIndex = slotIndex;
    }

    public VehicleSize getVehicleSize() {
        return vehicleSize;
    }

    public void setVehicleSize(VehicleSize vehicleSize) {
        this.vehicleSize = vehicleSize;
    }
}
