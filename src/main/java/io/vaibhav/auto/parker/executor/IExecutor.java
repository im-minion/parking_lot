package io.vaibhav.auto.parker.executor;

import java.util.List;


public interface IExecutor {
    void createParkingLot(String lotCount);

    void park(String regNo, String color);

    void leave(String slotNo);

    void status();

    void getRegistrationNumbersFromColor(String color);

    void getSlotNumbersFromColor(String color);

    void getSlotNumberFromRegNo(String regNo);

    static void printList(List<String> list) {
        if (!list.isEmpty()) {
            String joined = String.join(", ", list);
            System.out.println(joined);
        } else {
            System.out.println("Not found");
        }
    }
}
