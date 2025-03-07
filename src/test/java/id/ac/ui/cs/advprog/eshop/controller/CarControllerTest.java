package id.ac.ui.cs.advprog.eshop.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {
    
    @Mock
    private CarService carService;
    
    @InjectMocks
    private CarController carController;
    
    private Model model;
    private Car car;
    
    @BeforeEach
    void setUp() {
        model = new ConcurrentModel();
        
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Tesla Model S");
        car.setCarColor("Red");
        car.setCarQuantity(5);
    }
    
    @Test
    void testCreateCarPage() {
        String viewName = carController.createCarPage(model);
        
        assertEquals("createCar", viewName);
        assertTrue(model.containsAttribute("car"));
        assertInstanceOf(Car.class, model.getAttribute("car"));
    }
    
    @Test
    void testCreateCarPost() {
        when(carService.create(any(Car.class))).thenReturn(car);
        
        String viewName = carController.createCarPost(car, model);
        
        assertEquals("redirect:listCar", viewName);
        verify(carService, times(1)).create(car);
    }
    
    @Test
    void testCarListPage() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        
        when(carService.findAll()).thenReturn(cars);
        
        String viewName = carController.carListPage(model);
        
        assertEquals("carList", viewName);
        assertTrue(model.containsAttribute("cars"));
        assertEquals(cars, model.getAttribute("cars"));
        verify(carService, times(1)).findAll();
    }
    
    @Test
    void testEditCarPage() {
        when(carService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(car);
        
        String viewName = carController.editCarPage("eb558e9f-1c39-460e-8860-71af6af63bd6", model);
        
        assertEquals("editCar", viewName);
        assertTrue(model.containsAttribute("car"));
        assertEquals(car, model.getAttribute("car"));
        verify(carService, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
    
    @Test
    void testEditCarPost() {
        car.setCarName("Updated Name");
        
        String viewName = carController.editCarPost(car, model);
        
        assertEquals("redirect:listCar", viewName);
        verify(carService, times(1)).update(car.getCarId(), car);
    }
    
    @Test
    void testDeleteCar() {
        String viewName = carController.deleteCar("eb558e9f-1c39-460e-8860-71af6af63bd6");
        
        assertEquals("redirect:listCar", viewName);
        verify(carService, times(1)).deleteCarById("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }
}
