package io.vaibhav.auto.parker.executor;

import io.vaibhav.auto.parker.exception.ErrorCode;
import io.vaibhav.auto.parker.exception.ExecutorException;
import io.vaibhav.auto.parker.model.Vehicle;
import io.vaibhav.auto.parker.model.VehicleSize;

import java.util.ArrayList;
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
    private Map<String, Vehicle> slotCarMap;

    private static boolean isParkingLotCreated = false;

    private int sStartIndex;
    private int mStartIndex;
    private int lStartIndex;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(String sLotCount, String mLotCount, String lLotCount) {
        try {
            MAX_SIZE = Integer.parseInt(sLotCount) + Integer.parseInt(mLotCount) + Integer.parseInt(lLotCount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid lot count");
        }

        sStartIndex = 0;
        mStartIndex = Integer.parseInt(sLotCount);
        lStartIndex = Integer.parseInt(sLotCount) + Integer.parseInt(mLotCount);

        if (!isParkingLotCreated) {
            availableSlotList = new ArrayList<>();
            IntStream.range(0, MAX_SIZE).forEach(x -> {
                availableSlotList.add(x);
            });
            slotCarMap = new HashMap<>();
            isParkingLotCreated = true;
            System.out.println("Created parking lot with " + sLotCount + " slots");
        } else {
            System.out.println("Parking Lot Already Created, Can't Create new One!");
        }

    }

    @Override
    public void park(String regNo, String color, String vehicleS) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.writeLock().lock();
                boolean isParked = false;
                VehicleSize vehicleSize = VehicleSize.valueOf(vehicleS);
                int startIndexToPark = getStartingIndexFromSize(vehicleSize);
                if (slotCarMap.size() == MAX_SIZE) {
                    System.out.println("Sorry, parking lot is full");
                } else {
                    for (int i = startIndexToPark; i < MAX_SIZE; i++) {
                        if (!availableSlotList.get(i).equals(Integer.MAX_VALUE)) {
                            //park
                            String slot = availableSlotList.get(i).toString();
                            Vehicle vehicle = new Vehicle(regNo, color, slot, vehicleSize);
                            slotCarMap.put(slot, vehicle);
                            availableSlotList.set(i, Integer.MAX_VALUE);
                            isParked = true;
                            System.out.println("Allocated slot number: " + slot);
                            break;
                        }
                    }
                    if (!isParked) {
                        System.out.println("Sorry, parking lot is full for SIZE");
                    }
                }
            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private int getStartingIndexFromSize(VehicleSize vehicleSize) {
        return (vehicleSize.equals(VehicleSize.SMALL) ? sStartIndex : vehicleSize.equals(VehicleSize.MEDIUM) ? mStartIndex : lStartIndex);
    }

    @Override
    public void leave(String slotNo) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.writeLock().lock();
                if (slotCarMap.size() > 0) {
                    Vehicle vehicleToLeave = slotCarMap.get(slotNo);
                    if (vehicleToLeave != null) {
                        slotCarMap.remove(slotNo);
                        int slot = Integer.parseInt(slotNo);
                        availableSlotList.set(slot,slot);
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

                    Vehicle vehicle;
                    for (int i = 1; i <= MAX_SIZE; i++) {
                        String key = Integer.toString(i);
                        if (slotCarMap.containsKey(key)) {
                            vehicle = slotCarMap.get(key);
                            System.out.println(i + "\t\t" + vehicle.getRegistrationNo() + "\t\t" + vehicle.getColor());
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
                IExecutor.printList(getColorFilter(color).map(Vehicle::getRegistrationNo).collect(Collectors.toList()));

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
                IExecutor.printList(getColorFilter(color).map(Vehicle::getSlotIndex).collect(Collectors.toList()));
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
                String slotNum = slotCarMap.values().stream().filter(x -> x.getRegistrationNo().equals(regNo)).map(Vehicle::getSlotIndex).collect(Collectors.joining());
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

    private <T> Stream<Vehicle> getColorFilter(String color) {
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
