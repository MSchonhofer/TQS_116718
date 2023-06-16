package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Confirmation {

    private WebDriver driver;

    public Confirmation(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getTitle() {
        return driver.getTitle();
    }
}
