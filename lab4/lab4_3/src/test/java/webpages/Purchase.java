package webpages;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@ExtendWith(SeleniumJupiter.class)
public class Purchase {
    private WebDriver driver;

    @FindBy(xpath = "/html/body/div[2]/h2")
    WebElement heading;

    @FindBy(id = "inputName")
    WebElement name;

    @FindBy(id = "address")
    WebElement address;

    @FindBy(id = "city")
    WebElement city;

    @FindBy(id = "state")
    WebElement state;

    @FindBy(id = "zipCode")
    WebElement zipCode;

    @FindBy(id = "cardType")
    WebElement cardType;

    @FindBy(id = "creditCardNumber")
    WebElement creditCardNumber;

    @FindBy(id = "creditCardMonth")
    WebElement creditCardMonth;

    @FindBy(id = "creditCardYear")
    WebElement creditCardYear;

    @FindBy(id = "nameOnCard")
    WebElement nameCard;

    @FindBy(css = ".checkbox")
    private WebElement rememberMeButton;

    @FindBy(xpath = "/html/body/div[2]/form/div[11]/div/input")
    private WebElement purchaseFlightButton;

    //Constructor
    public Purchase(WebDriver driver) {
        this.driver = driver;

        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void setName(String name_) {
        name.clear();
        name.sendKeys(name_);
    }

    public void setAddress(String address_) {
        address.clear();
        address.sendKeys(address_);
    }

    public void setCity(String city_) {
        city.clear();
        city.sendKeys(city_);
    }

    public void setState(String state_) {
        state.clear();
        state.sendKeys(state_);
    }

    public void setZipCode(String zipCode_) {
        zipCode.clear();
        zipCode.sendKeys(zipCode_);
    }

    public void setCardType(String cardType) {
        this.cardType.findElement(By.xpath("//option[. = '" + cardType + "']"));
    }

    public void setCreditCardNumber(String creditCardNumber_) {
        creditCardNumber.clear();
        creditCardNumber.sendKeys(creditCardNumber_);
    }

    public void setCreditCardMonth(String creditCardMonth_) {
        creditCardMonth.clear();
        creditCardMonth.sendKeys(creditCardMonth_);
    }

    public void setCreditCardYear(String credirCardYear_) {
        creditCardYear.clear();
        creditCardYear.sendKeys(credirCardYear_);
    }

    public void setNameOnCard(String nameOnCard) {
        nameCard.clear();
        nameCard.sendKeys(nameOnCard);
    }

    public void purchaseFlight() {
        purchaseFlightButton.click();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}