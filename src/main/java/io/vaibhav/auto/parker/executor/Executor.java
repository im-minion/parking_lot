package io.vaibhav.auto.parker.executor;

import io.vaibhav.auto.parker.exception.ErrorCode;
import io.vaibhav.auto.parker.exception.ExecutorException;
import io.vaibhav.auto.parker.model.Car;
import io.vaibhav.auto.parker.model.Floor;
import io.vaibhav.auto.parker.model.ParkingSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Executor implements IExecutor {
    private static int TOTAL_LOTS_SIZE = 0;

    private static boolean isParkingLotCreated = false;

    private static int numberOfFloors;

    private static int lotsPrtFloor;

    private ParkingSpace parkingSpace = new ParkingSpace();

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(String lotCount, String noOfFloors) {
        try {
            lotsPrtFloor = Integer.parseInt(lotCount);
            numberOfFloors = Integer.parseInt(noOfFloors);
            TOTAL_LOTS_SIZE = lotsPrtFloor * numberOfFloors;
        } catch (NumberFormatException e) {
            System.out.println("Invalid lot count");
        }
        if (!isParkingLotCreated) {
            ArrayList<Floor> floorsList = new ArrayList<>();
            ArrayList<Integer> availList = new ArrayList<>();
            for (int j = 0; j < lotsPrtFloor; j++) {
                availList.add(j);
            }
            for (int i = 0; i < numberOfFloors; i++) {
                Floor floor = new Floor();
                floor.setLevel(String.valueOf(i));
                floor.setAvailableSlotList(new ArrayList<>(availList));
                floorsList.add(floor);
            }
            parkingSpace.setFloors(floorsList);
            System.out.println("Created parking with " + noOfFloors + " floors and each floor has " + lotCount + " slots");
        } else {
            System.out.println("Parking Lot Already Created, Can't Create new One!");
        }
    }

    @Override
    public void park(String regNo, String color) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.writeLock().lock();
                String floorNo = getFloorNumber();
                String slot = getNearestAvailableSlot();
                if (null != floorNo && null != slot) {
                    Floor f = parkingSpace.getFloors().get(Integer.parseInt(floorNo));
                    Car car = new Car(regNo, color, slot);
                    f.getSlotCarMap().put(slot, car);
                    System.out.println("Allocated slot number: " + slot + " on floor : " + floorNo);
                } else {
                    System.out.println("Sorry, parking lot is full");
                }
            }
        } catch (Exception e) {
            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private String getNearestAvailableSlot() {
        for (Floor f : parkingSpace.getFloors()) {
            if (!f.isFloorFull()) {
                Collections.sort(f.getAvailableSlotList());
                String r = f.getAvailableSlotList().get(0).toString();
                f.getAvailableSlotList().remove(0);
                if (f.getAvailableSlotList().isEmpty()) {
                    f.setFloorFull(true);
                }
                return r;
            }
        }
        return null;
    }

    private String getFloorNumber() {
        for (Floor f : parkingSpace.getFloors()) {
            if (!f.isFloorFull()) {
                return f.getLevel();
            }
        }
        return null;
    }

    @Override
    public void leave(String slotNo, String floor) throws ExecutorException {
        try {
            if (parkingLotCreated()) {
                lock.writeLock().lock();
                int floorLevel = Integer.parseInt(floor);
                Floor floorToOperate = parkingSpace.getFloors().get(floorLevel);
                if (null != floorToOperate && floorToOperate.getSlotCarMap().size() > 0) {
                    Car carToLeave = floorToOperate.getSlotCarMap().get(slotNo);
                    if (carToLeave != null) {
                        floorToOperate.getSlotCarMap().remove(slotNo);
                        floorToOperate.getAvailableSlotList().add(Integer.parseInt(slotNo));
                        Collections.sort(floorToOperate.getAvailableSlotList());
                        floorToOperate.setFloorFull(false);
                        System.out.println("Slot number " + slotNo + " is free from floor " + floor);
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
//        try {
//            if (parkingLotCreated()) {
//                lock.readLock().lock();
//                if (slotCarMap.size() > 0) {
//                    // Print the current status.
//                    System.out.println("Slot No.\tRegistration No \tColour");
//
//                    Car car;
//                    for (int i = 1; i <= MAX_SIZE; i++) {
//                        String key = Integer.toString(i);
//                        if (slotCarMap.containsKey(key)) {
//                            car = slotCarMap.get(key);
//                            System.out.println(i + "\t\t" + car.getRegistrationNo() + "\t\t" + car.getColor());
//                        }
//                    }
//                } else {
//                    System.out.println("Parking lot is empty");
//                }
//            }
//        } catch (Exception e) {
//            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
//        } finally {
//            lock.readLock().unlock();
//        }
    }

    @Override
    public void getRegistrationNumbersFromColor(String color) throws ExecutorException {
//        try {
//            if (parkingLotCreated()) {
//                lock.readLock().lock();
//                IExecutor.printList(getColorFilter(color).map(Car::getRegistrationNo).collect(Collectors.toList()));
//
//            }
//        } catch (Exception e) {
//            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
//        } finally {
//            lock.readLock().unlock();
//        }
    }

    @Override
    public void getSlotNumbersFromColor(String color) throws ExecutorException {
//        try {
//            if (parkingLotCreated()) {
//                lock.readLock().lock();
//                IExecutor.printList(getColorFilter(color).map(Car::getSlotIndex).collect(Collectors.toList()));
//            }
//        } catch (Exception e) {
//            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
//        } finally {
//            lock.readLock().unlock();
//        }
    }

    @Override
    public void getSlotNumberFromRegNo(String regNo) throws ExecutorException {
//        try {
//            if (parkingLotCreated()) {
//                lock.readLock().lock();
//                String slotNum = slotCarMap.values().stream().filter(x -> x.getRegistrationNo().equals(regNo)).map(Car::getSlotIndex).collect(Collectors.joining());
//                if (!slotNum.isEmpty()) {
//                    System.out.println(slotNum);
//                } else {
//                    System.out.println("Not found");
//                }
//            }
//        } catch (Exception e) {
//            throw new ExecutorException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
//        } finally {
//            lock.readLock().unlock();
//        }
    }

    @Override
    public void doCleanup() {
//        this.slotCarMap = null;
//        MAX_SIZE = 0;
//        availableSlotList = null;
//        isParkingLotCreated = false;
//        lock = null;
    }

//    private <T> Stream<Car> getColorFilter(String color) {
//        // will return the Stream<Car> having color -> 'color'
//        return slotCarMap.values().stream().filter(x -> x.getColor().equals(color));
//    }

    private boolean parkingLotCreated() {
        if (TOTAL_LOTS_SIZE == 0) {
            System.out.println("Sorry, parking lot is not created");
            return false;
        }
        return true;
    }
}
