package id.ac.ui.cs.advprog.eshop.service;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {
    
    @Mock
    private CarRepository carRepository;
    
    @InjectMocks
    private CarServiceImpl carService;
    
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
    void testCreate() {
        when(carRepository.create(any(Car.class))).thenReturn(car);
        
        Car result = carService.create(car);
        
        assertNotNull(result);
        assertEquals(car.getCarId(), result.getCarId());
        assertEquals(car.getCarName(), result.getCarName());
        verify(carRepository, times(1)).create(car);
    }
    
    @Test
    void testFindAll() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        
        Car car2 = new Car();
        car2.setCarId("another-id");
        car2.setCarName("Toyota Supra");
        cars.add(car2);
        
        when(carRepository.findAll()).thenReturn(cars.iterator());
        
        List<Car> result = carService.findAll();
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(car.getCarId(), result.get(0).getCarId());
        assertEquals(car2.getCarId(), result.get(1).getCarId());
        verify(carRepository, times(1)).findAll();
    }
    
    @Test
    void testFindById() {
        when(carRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(car);
        
        Car result = carService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        
        assertNotNull(result);
        assertEquals(car.getCarId(), result.getCarId());
        assertEquals(car.getCarName(), result.getCarName());
        verify(carRepository, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
    
    @Test
    void testUpdate() {
        Car updatedCar = new Car();
        updatedCar.setCarName("Updated Name");
        
        carService.update("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedCar);
        
        verify(carRepository, times(1)).update("eb558e9f-1c39-460e-8860-71af6af63bd6", updatedCar);
    }
    
    @Test
    void testDeleteCarById() {
        carService.deleteCarById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        
        verify(carRepository, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
}
