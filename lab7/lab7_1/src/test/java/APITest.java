import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
public class APITest {
    private final String API = "https://jsonplaceholder.typicode.com/todos";

    @Test
    void testAPIEndpoint_StatusCode200() {
        given()
                .when()
                .get(API)
                .then()
                .statusCode(200);
    }

    @Test
    void queryToDo4_ReturnObject(){
        given()
                .when()
                .get(API + "/4")
                .then()
                .statusCode(200)
                .body("id", equalTo(4))
                .body("title", equalTo("et porro tempora"));

    }

    @Test
    void whenListingAllToDos_getIDs(){
        given()
                .when()
                .get(API)
                .then()
                .statusCode(200)
                .body("id", hasItems(198,199));
    }

    @Test
    void getResultsInLessThan2secs() {
        given()
                .when()
                .get(API)
                .then()
                .statusCode(200)
                .body("", hasSize(200))
                .time(lessThan(2000L));
    }
}
