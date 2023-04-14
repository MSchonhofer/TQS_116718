package tqs.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.cars.JsonUtils;
import tqs.cars.model.Car;
import tqs.cars.service.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerMvcTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void testPostCar() throws Exception {
        Car car1 = new Car("Tesla", "X");

        when( service.save(Mockito.any())).thenReturn(car1);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Tesla")))
                .andExpect(jsonPath("$.model", is("X")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void getAllCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car("Porsche", "911");
        Car car2 = new Car("Lamborghini", "Urus");
        Car car3 = new Car("Audi", "A7");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(service.getAllCars()).thenReturn(allCars);

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].model", is(car1.getModel())))
                .andExpect(jsonPath("$[1].model", is(car2.getModel())))
                .andExpect(jsonPath("$[2].model", is(car3.getModel())))
                .andExpect(jsonPath("$[0].maker", is(car1.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(car2.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(car3.getMaker())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    public void givenId_thenCheckIfValid() throws Exception {
        Car car1 = new Car("Tesla", "X");
        car1.setCarId(1L);

        when(service.getCarDetails(car1.getCarId())).thenReturn(Optional.of(car1));

        mvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("maker").value("Tesla"))
                .andExpect(jsonPath("model").value("X"));

        verify(service, times(1)).getCarDetails(car1.getCarId());
    }

    @Test
    public void givenId_thenCheckIfInvalid() throws Exception {

        mvc.perform(get("/api/cars/1"))
                .andExpect(status().isNotFound());

        verify(service, times(1)).getCarDetails(1L);
    }
}