package com.pst.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pst.logistics.model.Car;
import com.pst.logistics.repository.CarRepository;


import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getCars(double length, double weight, double velocity, String color) {
        return carRepository.findByLengthAndWeightAndVelocityAndColor(length, weight, velocity, color);
    }
}
