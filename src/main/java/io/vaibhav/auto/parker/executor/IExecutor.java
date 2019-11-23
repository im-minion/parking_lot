package io.vaibhav.auto.parker.executor;

import io.vaibhav.auto.parker.exception.ExecutorException;

import java.util.List;


public interface IExecutor {
    void createParkingLot(String sLotCount, String mLotCount, String lLotCount);

    void park(String regNo, String color, String vehicleSize) throws ExecutorException;

    void leave(String slotNo) throws ExecutorException;

    void status() throws ExecutorException;

    void getRegistrationNumbersFromColor(String color) throws ExecutorException;

    void getSlotNumbersFromColor(String color) throws ExecutorException;

    void getSlotNumberFromRegNo(String regNo) throws ExecutorException;

    void doCleanup();

    static void printList(List<String> list) {
        if (!list.isEmpty()) {
            String joined = String.join(", ", list);
            System.out.println(joined);
        } else {
            System.out.println("Not found");
        }
    }
}
