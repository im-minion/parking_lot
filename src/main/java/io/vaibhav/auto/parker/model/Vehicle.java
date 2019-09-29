package io.vaibhav.auto.parker.model;

abstract class Vehicle {
    private String registrationNo;
    private String color;
    private String slotIndex;

    Vehicle(String registrationNo, String color, String slotIndex) {
        this.registrationNo = registrationNo;
        this.color = color;
        this.slotIndex = slotIndex;
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
}
