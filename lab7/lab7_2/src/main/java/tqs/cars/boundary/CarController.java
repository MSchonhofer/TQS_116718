package tqs.cars.boundary;

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
    public ResponseEntity<Optional<Car>> getCarById(@PathVariable(value = "id") Long carId) throws MissingResourceException {
        Optional<Car> foundCar = carManagerService.getCarDetails(carId);
        return ResponseEntity.ok().body(foundCar);
    }

}
