package webpages;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@ExtendWith(SeleniumJupiter.class)
public class HomePage {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL="https://blazedemo.com/";

    @FindBy(name = "fromPort")
    private WebElement departure;

    @FindBy(name = "toPort")
    private WebElement destination;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsButton;


    //Constructor
    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void fromPort(String city) {
        String xpath_str = String.format("//option[. = '%s']", city);
        departure.findElement(By.xpath(xpath_str)).click();
    }

    public void toPort(String city) {
        String xpath_str = String.format("//option[. = '%s']", city);
        destination.findElement(By.xpath(xpath_str)).click();
    }

    public void searchFlight() {
        findFlightsButton.click();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void clickOnFindFlightsButton(){
        findFlightsButton.click();
    }
}
