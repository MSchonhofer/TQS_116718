Feature: BlazeDemo Usage

  Scenario: Booking a flight from Paris to New York
    When I navigate to "https://blazedemo.com/"
    And I choose the origin:"Paris" and the destiny:"New York"
    And I select one of the flights
    And I write my name: "Annie Position"
    And I write my address:"Dust II"
    And I write my city:"New York"
    And I write my state:"Far"
    And I write my zip:"2117"
    And I select my card type:"Visa"
    And I write my card number:"123123"
    And I write my credit card month:"11"
    And I write my credit card year:"2023"
    And I write the name on the card:"Annie Position"
    And I click to confirm
    Then I should be in the "BlazeDemo Confirmation" page

  
 