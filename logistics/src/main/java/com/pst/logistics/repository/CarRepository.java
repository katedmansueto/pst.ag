package com.pst.logistics.repository;

import com.pst.logistics.model.Car;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByLengthAndWeightAndVelocityAndColor(double length, double weight, double velocity, String color);
}
