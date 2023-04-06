package tqs.cars.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private final CarManagerService carManagerService;

    public CarController(CarManagerService injectedCarManagerService) {
        this.carManagerService = injectedCarManagerService;
    }

    @PostMapping(path = "/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) {
        return new ResponseEntity<Car>(carManagerService.save(newCar), HttpStatus.CREATED);
    }

    @GetMapping(path = "/cars",  produces = "application/json")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping(path = "/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) throws ResourceNotFoundException {
        Car foundCar = carManagerService.getCarDetails(carId).orElseThrow(() -> new ResourceNotFoundException("Car with id " + carId + "not found!"));
        return ResponseEntity.ok().body(foundCar);
    }
}
