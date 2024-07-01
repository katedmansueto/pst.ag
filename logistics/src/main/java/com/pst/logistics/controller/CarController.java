package com.pst.logistics.controller;

import java.io.StringWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.pst.logistics.model.Car;
import com.pst.logistics.service.CarService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }
    		
    @GetMapping("/search")
    public List<Car> searchCars(
            @RequestParam double length,
            @RequestParam double weight,
            @RequestParam double velocity,
            @RequestParam String color) {
        return carService.getCars(length, weight, velocity, color);
    }

    @GetMapping("/search/download")
    public ResponseEntity<String> downloadSearchResults(
            @RequestParam double length,
            @RequestParam double weight,
            @RequestParam double velocity,
            @RequestParam String color) throws Exception {
        CarList carList = new CarList();
        carList.setCars(carService.getCars(length, weight, velocity, color));
        JAXBContext context = JAXBContext.newInstance(CarList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        StringWriter sw = new StringWriter();
        marshaller.marshal(carList, sw);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cars.xml");
        headers.setContentType(MediaType.APPLICATION_XML);
        return new ResponseEntity<>(sw.toString(), headers, HttpStatus.OK);
    }
    
	  @XmlRootElement(name = "cars")
	  public static class CarList {
		private List<Car> cars;
	
		  @XmlElement(name = "car")
		public List<Car> getCars() {
			return cars;
		}
	
		public void setCars(List<Car> cars) {
			this.cars = cars;
		}
	  }
  
}


