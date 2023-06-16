package webpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Flights {
    private final WebDriver driver;

    public Flights(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void bookFlight(int flightIdx) {
        By flightButton = By.cssSelector("tr:nth-child(" + flightIdx + ") .btn");
        driver.findElement(flightButton).click();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
