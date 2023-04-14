package tqs.cars.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tqs.cars.GsCarsContainersApplication;
import tqs.cars.JsonUtils;
import tqs.cars.data.CarRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import tqs.cars.model.Car;

import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = GsCarsContainersApplication.class)
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CarRestControllerIT {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }
    @Test
    void whenValidInput_thenCreateCar() throws Exception {
        Car car1 = new Car("Tesla", "X");
        mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car1)));

        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).extracting(Car::getModel).containsOnly("X");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() throws Exception {
        Car car1 = new Car("Tesla", "X");
        car1.setCarId(1L);
        Car car2 = new Car("Porsche", "911");
        car2.setCarId(2L);

        carRepository.saveAndFlush(car1);
        carRepository.saveAndFlush(car2);

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].maker", is("Tesla")))
                .andExpect(jsonPath("$[0].model", is("X")))
                .andExpect(jsonPath("$[1].maker", is("Porsche")))
                .andExpect(jsonPath("$[1].model", is("911")));
    }

}
