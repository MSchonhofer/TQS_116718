package org.example;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StocksPortfolioTest {

    // 1. Prepare a mock to substitute the remote service (@Mock annotation)
    @Mock
    private IStockmarketService stockMarket = mock(IStockmarketService.class);
    
    // 2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
    @InjectMocks
    private StocksPortfolio stocksPortfolio = new StocksPortfolio(stockMarket);

    @Test
    void getTotalValue() {

        // 3. Load the mock with the proper expectations (when...thenReturn)

        when(stockMarket.lookUpPrice("EBAY")).thenReturn(4.50);
        when(stockMarket.lookUpPrice("TESLA")).thenReturn(2.50);

        // 4. Execute the test (use the service in the SuT)

        stocksPortfolio.addStock(new Stock("EBAY", 2));
        stocksPortfolio.addStock(new Stock("TESLA",3));

        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(stocksPortfolio.getTotalValue(), 7);
        verify(stockMarket, times(2)).lookUpPrice(anyString());

    }
}