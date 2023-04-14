package tqs.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.cars.Car;
import tqs.cars.CarManagerService;

import java.util.List;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    public CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping(path = "/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) {
        return new ResponseEntity<>(carManagerService.save(newCar), HttpStatus.CREATED);
    }

    @GetMapping(path = "/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) {
        return ResponseEntity.ok().body(carManagerService.getCarDetails(carId).get());
    }
}
