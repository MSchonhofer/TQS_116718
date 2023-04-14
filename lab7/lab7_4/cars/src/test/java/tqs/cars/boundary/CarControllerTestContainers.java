package tqs.cars.boundary;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import tqs.cars.GsCarsContainersApplication;
import tqs.cars.JsonUtils;
import tqs.cars.model.Car;
import tqs.cars.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@AutoConfigureMockMvc
public class CarControllerTestContainers {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:latest")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("cars");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setUp () {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void testPostCar() throws Exception {
        Car car1 = new Car("Tesla", "X");

        when(service.save(Mockito.any())).thenReturn(car1);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .body(JsonUtils.toJson(car1))
                .when()
                .post("/api/cars")
                .then()
                .statusCode(201)
                .body("maker", equalTo("Tesla"))
                .body("model", equalTo("X"));

        verify(service, times(1)).save(Mockito.any());

    }

    @Test
    void getAllCars_thenReturnJsonArray() throws Exception {
        Car car1 = new Car("Porsche", "911");
        Car car2 = new Car("Lamborghini", "Urus");
        Car car3 = new Car("Audi", "A7");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
                .given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200)
                .body("", hasSize(3))
                .body("[0].model", equalTo(car1.getModel()))
                .body("[1].model", equalTo(car2.getModel()))
                .body("[2].model", equalTo(car3.getModel()))
                .body("[0].maker", equalTo(car1.getMaker()))
                .body("[1].maker", equalTo(car2.getMaker()))
                .body("[2].maker", equalTo(car3.getMaker()));

        verify(service, times(1)).getAllCars();
    }

    @Test
    public void givenId_thenCheckIfValid() throws Exception {
        Car car1 = new Car("Tesla", "X");
        car1.setCarId(1L);

        when(service.getCarDetails(car1.getCarId())).thenReturn(Optional.of(car1));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars/1")
                .then()
                .statusCode(200)
                .body("maker", equalTo("Tesla"))
                .body("model", equalTo("X"));

        verify(service, times(1)).getCarDetails(car1.getCarId());
    }

    @Test
    public void givenId_thenCheckIfInvalid() throws Exception {

        when(service.getCarDetails(Mockito.anyLong())).thenReturn(Optional.empty());

        RestAssuredMockMvc
                .given()
                .when()
                .get("/api/cars/1")
                .then()
                .statusCode(404);

        verify(service, times(1)).getCarDetails(1L);

    }
}