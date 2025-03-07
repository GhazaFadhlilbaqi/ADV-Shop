package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Tesla Model S");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }

    @Test
    void testGetCarId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", car.getCarId());
    }

    @Test
    void testSetCarId() {
        car.setCarId("new-id-123");
        assertEquals("new-id-123", car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Tesla Model S", car.getCarName());
    }

    @Test
    void testSetCarName() {
        car.setCarName("Toyota Supra");
        assertEquals("Toyota Supra", car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Red", car.getCarColor());
    }

    @Test
    void testSetCarColor() {
        car.setCarColor("Blue");
        assertEquals("Blue", car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(5, car.getCarQuantity());
    }

    @Test
    void testSetCarQuantity() {
        car.setCarQuantity(10);
        assertEquals(10, car.getCarQuantity());
    }

    @Test
    void testUpdate() {
        Car newCar = new Car();
        newCar.setCarName("Toyota Supra");
        newCar.setCarColor("Blue");
        newCar.setCarQuantity(10);
        
        Car updatedCar = car.update(newCar);
        
        assertEquals("Toyota Supra", updatedCar.getCarName());
        assertEquals("Blue", updatedCar.getCarColor());
        assertEquals(10, updatedCar.getCarQuantity());
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedCar.getCarId());
        assertSame(car, updatedCar); // Verify it returns the same instance
    }
}
