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
            System.out.println(list.get(0)); //TODO: May have to iterate bcz of ","
            list.forEach(sl -> {
                System.out.println(", " + sl);
            });
            System.out.println();
        } else {
            System.out.println("Not found");
            System.out.println();
        }
    }
}
