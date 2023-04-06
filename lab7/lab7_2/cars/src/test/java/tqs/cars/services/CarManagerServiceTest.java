package tqs.cars.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.cars.data.CarRepository;
import tqs.cars.model.Car;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarManagerServiceTest {
    @Mock( lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        Car car1= new Car("Tesla", "X");
        car1.setCarId(1L);
        Car car2 = new Car("Porsche", "911");
        car2.setCarId(2L);
        Car car3 = new Car("Lamborghini", "Urus");
        car3.setCarId(3L);

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        Mockito.when(carRepository.findByCarId(car1.getCarId())).thenReturn(Optional.of(car1));
        Mockito.when(carRepository.findByCarId(car2.getCarId())).thenReturn(Optional.of(car2));
        Mockito.when(carRepository.findByCarId(car3.getCarId())).thenReturn(Optional.of(car3));
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(Optional.empty());
    }

    @Test
    public void testAllCars() {
        Car car1 = new Car("Tesla", "X");
        Car car2 = new Car("Porsche", "911");
        Car car3 = new Car("Lamborghini", "Urus");

        List<Car> allCars = carManagerService.getAllCars();

        Mockito.verify(carRepository, times(1)).findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(car1.getModel(), car2.getModel(), car3.getModel());
    }

    @Test
    public void testCarInvalidId() {
        Optional<Car> car = carManagerService.getCarDetails(-99L);

        assertThat(car.isEmpty()).isTrue();

        Mockito.verify(carRepository, times(1)).findByCarId(Mockito.anyLong());

    }

    @Test
    public void testCarValidId() {
        Optional<Car> car1 = carManagerService.getCarDetails(1L);
        assertThat(car1.isPresent()).isTrue();
        Car c1 = car1.get();
        assertThat(c1.getMaker()).isEqualTo("Tesla");

        Optional<Car> car2 = carManagerService.getCarDetails(2L);
        assertThat(car2.isPresent()).isTrue();
        Car c2 = car2.get();
        assertThat(c2.getModel()).isEqualTo("911");

        Mockito.verify(carRepository, times(2)).findByCarId(Mockito.anyLong());

    }
}