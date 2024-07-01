package com.pst.logistics.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pst.logistics.model.Car;
import com.pst.logistics.repository.CarRepository;

import java.util.List;
import java.util.Arrays;

public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    public CarServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchCars() {
        Car car = new Car();
        car.setLength(4.5);
        car.setWeight(1200);
        car.setVelocity(200);
        car.setColor("Red");

        when(carRepository.findByLengthAndWeightAndVelocityAndColor(4.5, 1200, 200, "Red"))
                .thenReturn(Arrays.asList(car));

        List<Car> cars = carService.getCars(4.5, 1200, 200, "Red");
        assertNotNull(cars);
        assertEquals(1, cars.size());
        assertEquals("Red", cars.get(0).getColor());
    }
}
