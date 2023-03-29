package io.github.bonigarcia.webpages;

//Java Imports
import java.util.List;

//Selenium Imports
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class FormPage {
   private WebDriver driver;
    @FindBy(how = How.ID, using = "inputName")
    private WebElement inputName; 

    @FindBy(how = How.ID, using = "address")
    private WebElement address;

    @FindBy(how = How.ID, using = "state")
    private WebElement state;

    @FindBy(how = How.ID, using = "city")
    private WebElement city;

    @FindBy(how = How.ID, using = "zipCode")
    private WebElement zipCode;

    @FindBy(how = How.NAME, using = "cardType")
    private WebElement cardType;

    @FindBy(how = How.NAME, using = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(how = How.NAME, using = "creditCardMonth")
    private WebElement creditCardMonth;

    @FindBy(how = How.NAME, using = "creditCardYear")
    private WebElement creditCardYear;

    @FindBy(how = How.NAME, using = "nameOnCard")
    private WebElement nameOnCard;  
 
    public FormPage(WebDriver driver){
        this.driver=driver;
 
        PageFactory.initElements(driver, this);
    } 

    public void setName(String fullname){
        inputName.clear();
        inputName.sendKeys(fullname);
    }

    public void setAddress(String addressString){
        address.clear();
        address.sendKeys(addressString);
    }

    public void setCity(String CityString){
        city.clear();
        city.sendKeys(CityString);
    }

    public void setState(String StateString){
        state.clear();
        state.sendKeys(StateString);
    }

    public void setZip(String zipCodeString){
        zipCode.clear();
        zipCode.sendKeys(zipCodeString);
    }

    public void setCardType(String tyupeString){
        Select statusDropdown=new Select(cardType);
        statusDropdown.selectByVisibleText(tyupeString);
    } 
    
    public void setCardNumber(String creditCardNumberString){
        creditCardNumber.clear();
        creditCardNumber.sendKeys(creditCardNumberString);
    } 

    public void setCardMonth(String cardMonthString){
        creditCardMonth.clear();
        creditCardMonth.sendKeys(cardMonthString);
    }

    public void setcreditCardYear(String cardYearString){
        creditCardYear.clear();
        creditCardYear.sendKeys(cardYearString);
    }

    public void setNameOnCard(String nameOnCardString){
        nameOnCard.clear();
        nameOnCard.sendKeys(nameOnCardString);
    }

    public void clickButton(){
        List<WebElement> elements = driver.findElements(By.tagName("form"));   
        WebElement form = elements.get(0);
        form.submit();
    }
}