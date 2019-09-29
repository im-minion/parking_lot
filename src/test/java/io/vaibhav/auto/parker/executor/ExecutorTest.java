package io.vaibhav.auto.parker.executor;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class ExecutorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
    }

    @Test
    public void createParkingLot() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("65");
        assertTrue("createdparkinglotwith65slots".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        instance.doCleanup();
    }


    @Test
    public void testParkingCapacity() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("12");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.park("KA-01-BB-0001", "Black");
        Assert.assertTrue(outContent.toString().contains("Allocated slot number: 3"));
        instance.doCleanup();
    }

    @Test
    public void testParkingCapacityAfterFull() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("6");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.park("KA-01-BB-0001", "Black");
        Assert.assertTrue(outContent.toString().contains("Allocated slot number: 3"));
        instance.park("KA-01-BB-0001", "Black");
        instance.park("KA-03-BB-0001", "Black");
        instance.park("KA-04-BB-0001", "Black");
        instance.park("KA-06-BB-0001", "Black");
        Assert.assertTrue(outContent.toString().contains("Sorry, parking lot is full"));
        instance.doCleanup();
    }

    @Test
    public void testLeave() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("6");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.park("KA-01-BB-0001", "Black");
        instance.leave("3");
        instance.leave("4");
        Assert.assertTrue(outContent.toString().contains("Slot number 3 is free"));
        Assert.assertTrue(outContent.toString().contains("Slot number 4 is already empty"));
        instance.doCleanup();
    }

    @Test
    public void testStatus() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("6");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.status();
        Assert.assertTrue(outContent.toString().contains("Slot No.\tRegistration No \tColour"));
        Assert.assertTrue(outContent.toString().contains("KA-01-HH-1234"));
        Assert.assertTrue(outContent.toString().contains("KA-01-HH-9999"));
        instance.doCleanup();
    }

    @Test
    public void testGetREgNoByColor() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("6");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "Black");
        instance.getRegistrationNumbersFromColor("Cyan");
        Assert.assertTrue(outContent.toString().contains("Not found"));
        instance.getRegistrationNumbersFromColor("Black");
        Assert.assertTrue(outContent.toString().contains("KA-01-HH-9999"));
        Assert.assertTrue(!outContent.toString().contains("KA-01-HH-1234"));
        instance.doCleanup();
    }

    @Test
    public void testGetSlotsByColor() throws Exception {
        IExecutor instance = new Executor();
        instance.createParkingLot("6");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.getSlotNumbersFromColor("Cyan");
        Assert.assertTrue(outContent.toString().contains("Not found"));
        instance.getSlotNumbersFromColor("White");
        Assert.assertTrue(outContent.toString().contains("2"));
        instance.doCleanup();
    }

    @Test
    public void testGetSlotsByRegNo() throws Exception
    {
        IExecutor instance = new Executor();
        instance.createParkingLot("6");
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.status();
        instance.park("KA-01-HH-1234", "White");
        instance.park("KA-01-HH-9999", "White");
        instance.getSlotNumberFromRegNo("KA-01-HH-1234");
        Assert.assertTrue(outContent.toString().contains("1"));
        instance.getSlotNumberFromRegNo("KA-01-HH-789789");
        Assert.assertTrue(outContent.toString().contains("Not found"));
        instance.doCleanup();
    }



}
