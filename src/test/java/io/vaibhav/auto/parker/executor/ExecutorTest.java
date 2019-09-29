//package io.vaibhav.auto.parker.executor;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.junit.runner.RunWith;
//
//import static org.junit.Assert.*;
//
//@RunWith(Arquillian.class)
//public class ExecutorTest {
//    @Deployment
//    public static JavaArchive createDeployment() {
//        return ShrinkWrap.create(JavaArchive.class)
//                .addClass(Executor.class)
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    @org.junit.Beforesz
//    public void setUp() throws Exception {
//    }
//
//    @org.junit.After
//    public void tearDown() throws Exception {
//    }
//
//    @org.junit.Test
//    public void createParkingLot() {
//    }
//
//    @org.junit.Test
//    public void park() {
//    }
//
//    @org.junit.Test
//    public void leave() {
//    }
//
//    @org.junit.Test
//    public void status() {
//    }
//
//    @org.junit.Test
//    public void getRegistrationNumbersFromColor() {
//    }
//
//    @org.junit.Test
//    public void getSlotNumbersFromColor() {
//    }
//
//    @org.junit.Test
//    public void getSlotNumberFromRegNo() {
//    }
//}
