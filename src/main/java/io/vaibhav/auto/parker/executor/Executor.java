package io.vaibhav.auto.parker.executor;

import io.vaibhav.auto.parker.exception.ErrorCode;
import io.vaibhav.auto.parker.exception.ExecutorException;
import io.vaibhav.auto.parker.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Executor implements IExecutor {
    private static int MAX_SIZE = 0;
    // Available slots list
    private static ArrayList<Integer> availableSlotList;
    // Map of Slot, Car
    private Map<String, Car> slotCarMap;

    private static boolean isParkingLotCreated = false;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(String lotCount) {
        try {
            MAX_SIZE = Integer.parseInt(lotCount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid lot count");
        }
        if (!isParkingLotCreated) {
            availableSlotList = new ArrayList<>();
            IntStream.range(1, MAX_SIZE + 1).forEach(x -> {
                availableSlotList.add(x);
            });
            slotCarMap = new HashMap<>();
            isParkingLotCreated = true;
            System.out.println("Created parking lot with " + lotCount + " slots");
        } else {
            System.out.println("Parking Lot Already Created, Can't Create new One!");
        }

    }

    @Override
    public void park(String regNo, String color) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.writeLock().lock();
                if (slotCarMap.size() == MAX_SIZE) {
                    System.out.println("Sorry, parking lot is full");
                } else {
                    Collections.sort(availableSlotList);
                    String slot = availableSlotList.get(0).toString();
                    Car car = new Car(regNo, color, slot);
                    slotCarMap.put(slot, car);
                    System.out.println("Allocated slot number: " + slot);
                    availableSlotList.remove(0);
                }
            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void leave(String slotNo) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.writeLock().lock();
                if (slotCarMap.size() > 0) {
                    Car carToLeave = slotCarMap.get(slotNo);
                    if (carToLeave != null) {
                        slotCarMap.remove(slotNo);
                        availableSlotList.add(Integer.parseInt(slotNo));
                        System.out.println("Slot number " + slotNo + " is free");
                    } else {
                        System.out.println("Slot number " + slotNo + " is already empty");
                    }
                } else {
                    System.out.println("Parking lot is empty");
                }

            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"), e);
        } finally {
            lock.writeLock().lock();
        }
    }

    @Override
    public void status() throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.readLock().lock();
                if (slotCarMap.size() > 0) {
                    // Print the current status.
                    System.out.println("Slot No.\tRegistration No \tColour");

                    Car car;
                    for (int i = 1; i <= MAX_SIZE; i++) {
                        String key = Integer.toString(i);
                        if (slotCarMap.containsKey(key)) {
                            car = slotCarMap.get(key);
                            System.out.println(i + "\t\t" + car.getRegistrationNo() + "\t\t" + car.getColor());
                        }
                    }
                } else {
                    System.out.println("Parking lot is empty");
                }
            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getRegistrationNumbersFromColor(String color) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.readLock().lock();
                IExecutor.printList(getColorFilter(color).map(Car::getRegistrationNo).collect(Collectors.toList()));

            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getSlotNumbersFromColor(String color) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.readLock().lock();
                IExecutor.printList(getColorFilter(color).map(Car::getSlotIndex).collect(Collectors.toList()));
            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getSlotNumberFromRegNo(String regNo) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.readLock().lock();
                String slotNum = slotCarMap.values().stream().filter(x -> x.getRegistrationNo().equals(regNo)).map(Car::getSlotIndex).collect(Collectors.joining());
                if (!slotNum.isEmpty()) {
                    System.out.println(slotNum);
                } else {
                    System.out.println("Not found");
                }
            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void doCleanup() {
        this.slotCarMap = null;
        MAX_SIZE = 0;
        availableSlotList = null;
        isParkingLotCreated = false;
        lock = null;
    }

    private <T> Stream<Car> getColorFilter(String color) {
        // will return the Stream<Car> having color -> 'color'
        return slotCarMap.values().stream().filter(x -> x.getColor().equals(color));
    }

    private boolean parkingLotCreated() {
        if (MAX_SIZE == 0) {
            System.out.println("Sorry, parking lot is not created");
            return false;
        }
        return true;
    }
}
