package tqs.cars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.cars.CarRepository;
import tqs.cars.Car;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    @Autowired
    public CarRepository carRepository;

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        Car car = carRepository.findByCarId(carId);

        if(car != null) {
            return Optional.of(car);
        } else {

            return Optional.empty();
        }
    }

}
