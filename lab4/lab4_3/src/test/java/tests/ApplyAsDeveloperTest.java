package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import webpages.Confirmation;
import webpages.Flights;
import webpages.Purchase;
import webpages.HomePage;

import static org.assertj.core.api.Assertions.assertThat;


public class ApplyAsDeveloperTest {
    WebDriver driver;

    @BeforeEach
    public void setup(){
        driver = new FirefoxDriver();
    }

    @Test
    public void applyAsDeveloper() {
        //Create object of HomePage Class
        HomePage home = new HomePage(driver);
        assertThat(home.getTitle()).isEqualTo("BlazeDemo");

        home.fromPort("Philadelphia");
        home.toPort("New York");
        home.clickOnFindFlightsButton();

        Flights flights = new Flights(driver);
        assertThat(flights.getTitle()).isEqualTo("BlazeDemo - reserve");
        flights.bookFlight(2);


        //Create object of DeveloperApplyPage
        Purchase applyPage = new Purchase(driver);
        assertThat(flights.getTitle()).isEqualTo("BlazeDemo Purchase");

        //Fill up data
        applyPage.setName("Pepe");
        applyPage.setAddress("Main St 123");
        applyPage.setCity("Miami");
        applyPage.setState("USA");
        applyPage.setZipCode("12345");
        applyPage.setCardType("American Express");
        applyPage.setCreditCardNumber("55555");
        applyPage.setCreditCardMonth("12");
        applyPage.setCreditCardYear("2022");
        applyPage.setNameOnCard("Pepe Popo");
        applyPage.purchaseFlight();


        Confirmation confirmation = new Confirmation(driver);
        assertThat(confirmation.getTitle()).isEqualTo("BlazeDemo Confirmation");
    }

    @AfterEach
    public void close(){
        driver.close();
    }
}
