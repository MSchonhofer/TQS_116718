package tqs.cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "application-integrationtest.properties")
public class CarRestControllerIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car car1 = new Car("Tesla", "X");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", car1, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Tesla");
        assertThat(found).extracting(Car::getModel).containsOnly("X");

    }

    @Test
    void givenCars_whenGetCars_thenStatus200()  {
        Car car1 = new Car("Tesla", "X");
        Car car2 = new Car("Porsche", "911");
        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);


        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("Tesla", "Porsche");
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("X", "911");

    }

}
