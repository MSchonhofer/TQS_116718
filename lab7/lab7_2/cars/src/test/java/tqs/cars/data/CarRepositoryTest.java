package tqs.cars.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.cars.model.Car;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarByID_thenReturnCar() {
        Car car1 = new Car("Tesla", "X"); //insert new car into the database
        entityManager.persistAndFlush(car1); // immediately persist an entity

        Car fromDb = carRepository.findByCarId(car1.getCarId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getModel()).isEqualTo(car1.getModel());
        assertThat(fromDb.getMaker()).isEqualTo(car1.getMaker());
    }

    @Test
    void whenCarIsInvalid_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-911L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void testFindAll_thenReturnAll() {
        Car car1 = new Car("Porsche", "911");
        Car car2 = new Car("Lamborghini", "Urus");
        Car car3 = new Car("Audi", "A7");

        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(car1.getMaker(), car2.getMaker(), car3.getMaker());

    }
}