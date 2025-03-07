package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.eshop.model.Car;

public class CarRepositoryTest {
    
    private CarRepository carRepository;
    
    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }
    
    @Test
    void testCreateWithNoId() {
        Car car = new Car();
        car.setCarName("Toyota Supra");
        car.setCarColor("Black");
        car.setCarQuantity(10);
        
        Car result = carRepository.create(car);
        
        assertNotNull(result.getCarId());
        assertEquals("Toyota Supra", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }
    
    @Test
    void testCreateWithId() {
        Car car = new Car();
        car.setCarId("existing-id-123");
        car.setCarName("Honda Civic");
        car.setCarColor("White");
        car.setCarQuantity(5);
        
        Car result = carRepository.create(car);
        
        assertEquals("existing-id-123", result.getCarId());
        assertEquals("Honda Civic", result.getCarName());
    }
    
    @Test
    void testFindAllEmpty() {
        Iterator<Car> iterator = carRepository.findAll();
        assertFalse(iterator.hasNext());
    }
    
    @Test
    void testFindAllWithData() {
        // Add some cars
        Car car1 = createCarWithData("Honda Civic", "White", 5);
        Car car2 = createCarWithData("Toyota Supra", "Black", 10);
        
        Iterator<Car> iterator = carRepository.findAll();
        
        assertTrue(iterator.hasNext());
        Car firstCar = iterator.next();
        assertEquals(car1.getCarName(), firstCar.getCarName());
        
        assertTrue(iterator.hasNext());
        Car secondCar = iterator.next();
        assertEquals(car2.getCarName(), secondCar.getCarName());
        
        assertFalse(iterator.hasNext());
    }
    
    @Test
    void testFindByIdFound() {
        Car car = createCarWithData("Honda Civic", "White", 5);
        String carId = car.getCarId();
        
        Car result = carRepository.findById(carId);
        
        assertNotNull(result);
        assertEquals(carId, result.getCarId());
        assertEquals("Honda Civic", result.getCarName());
    }
    
    @Test
    void testFindByIdNotFound() {
        Car result = carRepository.findById("non-existent-id");
        assertNull(result);
    }
    
    @Test
    void testUpdateFound() {
        Car car = createCarWithData("Honda Civic", "White", 5);
        String carId = car.getCarId();
        
        Car updatedCar = new Car();
        updatedCar.setCarName("Honda Accord");
        updatedCar.setCarColor("Black");
        updatedCar.setCarQuantity(10);
        
        Car result = carRepository.update(carId, updatedCar);
        
        assertNotNull(result);
        assertEquals(carId, result.getCarId());
        assertEquals("Honda Accord", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }
    
    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Honda Accord");
        
        Car result = carRepository.update("non-existent-id", updatedCar);
        
        assertNull(result);
    }
    
    @Test
    void testDeleteFound() {
        Car car = createCarWithData("Honda Civic", "White", 5);
        String carId = car.getCarId();
        
        carRepository.delete(carId);
        
        Car result = carRepository.findById(carId);
        assertNull(result);
    }
    
    @Test
    void testDeleteNotFound() {
        // First add a car
        createCarWithData("Honda Civic", "White", 5);
        
        // Try to delete a non-existent car (should not throw exception)
        carRepository.delete("non-existent-id");
        
        // Original car should still be there
        Iterator<Car> iterator = carRepository.findAll();
        assertTrue(iterator.hasNext());
    }
    
    private Car createCarWithData(String name, String color, int quantity) {
        Car car = new Car();
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return carRepository.create(car);
    }
}
