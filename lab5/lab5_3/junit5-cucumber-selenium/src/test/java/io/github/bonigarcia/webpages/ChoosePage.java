package io.github.bonigarcia.webpages;

//Selenium Imports
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class ChoosePage {
   private WebDriver driver; 
   public ChoosePage(WebDriver driver){
    this.driver = driver;
 
    PageFactory.initElements(driver, this);
   }
   public void clickOnFindButton(String value){ 
 
      driver.findElement(By.name(value)).submit(); 
   }
}