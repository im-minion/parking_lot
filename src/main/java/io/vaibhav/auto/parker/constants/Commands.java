package io.vaibhav.auto.parker.constants;

import io.vaibhav.auto.parker.executor.IExecutor;
import io.vaibhav.auto.parker.model.VehicleSize;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Commands {
    public Map<String, Method> commandsMap;

    public Commands() {
        commandsMap = new HashMap<>();
        try {
            populateCommandsMap();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void populateCommandsMap() throws NoSuchMethodException {
        commandsMap.put("create_parking_lot", IExecutor.class.getMethod("createParkingLot", String.class, String.class, String.class));
        commandsMap.put("park", IExecutor.class.getMethod("park", String.class, String.class, String.class));
        commandsMap.put("leave", IExecutor.class.getMethod("leave", String.class));
        commandsMap.put("status", IExecutor.class.getMethod("status"));
        commandsMap.put("registration_numbers_for_cars_with_colour", IExecutor.class.getMethod("getRegistrationNumbersFromColor", String.class));
        commandsMap.put("slot_numbers_for_cars_with_colour", IExecutor.class.getMethod("getSlotNumbersFromColor", String.class));
        commandsMap.put("slot_number_for_registration_number", IExecutor.class.getMethod("getSlotNumberFromRegNo", String.class));
    }
}
