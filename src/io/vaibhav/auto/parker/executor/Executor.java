package io.vaibhav.auto.parker.executor;

import io.vaibhav.auto.parker.model.Car;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Executor implements IExecutor {
    private static int MAX_SIZE = 0;
    // Available slots list
    private static ArrayList<Integer> availableSlotList;
    // Map of Slot, Car
    private Map<String, Car> slotCarMap;

    private static boolean isParkingLotCreated = false;

    @Override
    public void createParkingLot(String lotCount) {
        try {
            MAX_SIZE = Integer.parseInt(lotCount);
        } catch (Exception e) {
            System.out.println("Invalid lot count");
            System.out.println();
        }
        if (!isParkingLotCreated) {
            availableSlotList = new ArrayList<>();
            IntStream.range(1, MAX_SIZE + 1).forEach(x -> {
                availableSlotList.add(x);
            });
            slotCarMap = new HashMap<>();
            isParkingLotCreated = true;
            System.out.println("Created parking lot with " + lotCount + " slots");
            System.out.println();
        } else {
            System.out.println("Parking Lot Already Created, Can't Create new One!");
        }

    }

    @Override
    public void park(String regNo, String color) {
        if (parkingLotCreated()) {
            if (slotCarMap.size() == MAX_SIZE) {
                System.out.println("Sorry, parking lot is full");
                System.out.println();
            } else {
                Collections.sort(availableSlotList);
                String slot = availableSlotList.get(0).toString();
                Car car = new Car(regNo, color, slot);
                slotCarMap.put(slot, car);
                System.out.println("Allocated slot number: " + slot);
                System.out.println();
                availableSlotList.remove(0);
            }
        }
    }

    @Override
    public void leave(String slotNo) {
        if (parkingLotCreated()) {
            if (slotCarMap.size() > 0) {
                Car carToLeave = slotCarMap.get(slotNo);
                if (carToLeave != null) {
                    slotCarMap.remove(slotNo);
                    availableSlotList.add(Integer.parseInt(slotNo));
                    System.out.println("Slot number " + slotNo + " is free");
                    System.out.println();
                } else {
                    System.out.println("Slot number " + slotNo + " is already empty");
                    System.out.println();
                }
            } else {
                System.out.println("Parking lot is empty");
                System.out.println();
            }
        }
    }

    @Override
    public void status() {
        if (parkingLotCreated()) {
            if (slotCarMap.size() > 0) {
                // Print the current status.
                System.out.println("Slot No.\t\tRegistration No.\t\tColor");

//                IntStream.range(1, MAX_SIZE + 1).forEach(slotIndex->{
//                    Car car = slotCarMap.get(((int) slotIndex));
//                    if (null != slotCarMap.get(slotIndex)) {
//                        System.out.println(slotIndex + "\t" + car.getRegistrationNo() + "\t" + car.getColor());
//                    }
//                });
                Car car;
                for (int i = 1; i <= MAX_SIZE; i++) {
                    String key = Integer.toString(i);
                    if (slotCarMap.containsKey(key)) {
                        car = slotCarMap.get(key);
                        System.out.println(i + "\t\t" + car.getRegistrationNo() + "\t\t" + car.getColor());
                    }
                }
                System.out.println();
            } else {
                System.out.println("Parking lot is empty");
                System.out.println();
            }
        }
    }

    @Override
    public void getRegistrationNumbersFromColor(String color) {
        if (parkingLotCreated()) {
            List<String> carRegNoList = slotCarMap.values().stream().filter(x -> x.getColor().equals(color)).map(Car::getRegistrationNo).collect(Collectors.toList());
            printList(carRegNoList);
        }
    }

    @Override
    public void getSlotNumbersFromColor(String color) {
        if (parkingLotCreated()) {
            List<String> carSlotNoList = slotCarMap.values().stream().filter(x -> x.getColor().equals(color)).map(Car::getSlotIndex).collect(Collectors.toList());
            printList(carSlotNoList);
        }
    }

    @Override
    public void getSlotNumberFromRegNo(String regNo) {
        if (parkingLotCreated()) {
            String slotNum = slotCarMap.values().stream().filter(x -> x.getRegistrationNo().equals(regNo)).map(Car::getSlotIndex).collect(Collectors.joining());
            if (!slotNum.isEmpty()) {
                System.out.println(slotNum);
            } else {
                System.out.println("Not found");
                System.out.println();
            }
        }
    }

    private boolean parkingLotCreated() {
        if (MAX_SIZE == 0) {
            System.out.println("Sorry, parking lot is not created");
            System.out.println();
            return false;
        }
        return true;
    }

    private void printList(List<String> list) {
        if (!list.isEmpty()) {
            System.out.println(list); //TODO: May have to iterate bcz of ","
            System.out.println();
        } else {
            System.out.println("Not found");
            System.out.println();
        }
    }
}
