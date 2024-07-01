package com.pst.logistics.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.pst.logistics.model.Car;
import com.pst.logistics.service.CarService;

public class CarControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    public CarControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testSearchCars() throws Exception {
        Car car = new Car();
        car.setLength(4.5);
        car.setWeight(1200);
        car.setVelocity(200);
        car.setColor("Red");

        List<Car> cars = Arrays.asList(car);

        when(carService.getCars(4.5, 1200, 200, "Red")).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/search")
                .param("length", "4.5")
                .param("weight", "1200")
                .param("velocity", "200")
                .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    public void testDownloadSearchResults() throws Exception {
        Car car = new Car();
        car.setLength(4.5);
        car.setWeight(1200);
        car.setVelocity(200);
        car.setColor("Red");

        List<Car> cars = Arrays.asList(car);

        when(carService.getCars(4.5, 1200, 200, "Red")).thenReturn(cars);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/search/download")
                .param("length", "4.5")
                .param("weight", "1200")
                .param("velocity", "200")
                .param("color", "Red"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml"))
                .andExpect(content().contentType("application/xml"));
    }
}

