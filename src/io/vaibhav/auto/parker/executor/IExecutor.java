package io.vaibhav.auto.parker.executor;

public interface IExecutor {
    void createParkingLot(String lotCount);

    void park(String regNo, String color);

    void leave(String slotNo);

    void status();

    void getRegistrationNumbersFromColor(String color);

    void getSlotNumbersFromColor(String color);

    void getSlotNumberFromRegNo(String regNo);
}
