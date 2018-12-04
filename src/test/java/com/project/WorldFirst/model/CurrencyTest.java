package com.project.WorldFirst.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CurrencyTest {
	
	private Currency currency;
	
	@Before
    public void setupScenario() {
		currency = new Currency();
    	currency.setChangeType("GBP/USD");
    	currency.setPrice(1.2100f);
    }
    
    @Test
    public void shouldGetState() {
    	assertNotNull(currency);
        assertEquals(currency.getChangeType(), "GBP/USD");
        assertEquals(currency.getPrice(), 1.2100f, 0.0002);
    }

}
