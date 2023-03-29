package io.github.bonigarcia.webpages;

//Selenium Imports
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


public class HomePage {
    private WebDriver driver;
    private static String PAGE_URL="https://blazedemo.com/";
    @FindBy(how = How.NAME, using = "fromPort")
    private WebElement fromSelect;
 
    @FindBy(how = How.NAME, using = "toPort")
    private WebElement toSelect;
 
    @FindBy(how = How.TAG_NAME, using = "input")
    private WebElement FindButton;
    public HomePage(WebDriver driver){
       this.driver=driver;
       driver.get(PAGE_URL); 
       PageFactory.initElements(driver, this);
    }
    public void clickOnFindButton(){
        FindButton.click();
    }
    public void setOrigin(String from){
        Select statusDropdown=new Select(fromSelect);
        statusDropdown.selectByVisibleText(from);
    } 
    public void setDestiny(String to){
        Select statusDropdown=new Select(toSelect);
        statusDropdown.selectByVisibleText(to);
    }
}