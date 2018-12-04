package com.project.WorldFirst.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.WorldFirst.model.OrderType.OrderFXType;

@RunWith(SpringRunner.class)
public class OrderTest {
	
	private Order order;
	private Currency currency;

    @Before
    public void setupScenario() {
    	
    	currency = new Currency();
    	currency.setChangeType("GBP/USD");
    	currency.setPrice(1.2100f);
    	
    	order = new Order();
    	order.setOrderId(1);
    	order.setCurrency(currency);
    	order.setOrderType(OrderFXType.ASK);
    	order.setUserId(1);
    	order.setAmount(1200);
    	order.setMatched(false);
    	order.setMatchedOrderID(0);
    }
    
    @Test
    public void shouldGetState() {
    	assertNotNull(order);
        assertEquals(order.getOrderId(), 1);
        assertEquals(order.getCurrency().getChangeType(), "GBP/USD");
        assertEquals(order.getCurrency().getPrice(), 1.2100f, 0.0001);
        assertEquals(order.getOrderType(), OrderFXType.ASK);
        assertEquals(order.getAmount(), 1200, 0.0);
        assertEquals(order.isMatched(), false);
        assertEquals(order.getMatchedOrderID(), 0);
        assertEquals(order.getUserId(), 1);
    }

}
