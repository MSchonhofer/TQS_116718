package io.github.bonigarcia.webpages;

//Selenium Imports
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
   private WebDriver driver;
   public ConfirmationPage(WebDriver driver){
      this.driver = driver;
 
      PageFactory.initElements(driver, this);
   }
   public String getTitle(){
      String currentTitle = driver.getTitle(); 
      return currentTitle; 
   }

}