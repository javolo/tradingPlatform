package com.project.WorldFirst.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.project.WorldFirst.model.Currency;
import com.project.WorldFirst.model.Order;
import com.project.WorldFirst.model.OrderType.OrderFXType;
import com.project.WorldFirst.service.TradeService;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DummyFxControllerTest {
	
	@Mock
	private TradeService tradeService;

	private DummyFXController controller;
	
	private Currency currency;
	private Order order;
	private List<Order> unmatchedOrderList;
	private List<Order> matchedOrderList;

	@Before
	public void setupScenario() {
		controller = new DummyFXController(tradeService);
		
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
    	
    	unmatchedOrderList = new ArrayList<>();
    	unmatchedOrderList.add(order);
    	
    	matchedOrderList = new ArrayList<>();
	}

	@Test
	public void makeAnOrderTest() {

		ResponseEntity<String> response = controller.makeOrder("BIDD", 1200);
		assertEquals(response, ResponseEntity.badRequest().body("One of the parameters (order type or amount) is incorrect"));
		response = controller.makeOrder(null, 1200);
		assertEquals(response, ResponseEntity.badRequest().body("One of the parameters (order type or amount) is incorrect"));
		response = controller.makeOrder("", 1200);
		assertEquals(response, ResponseEntity.badRequest().body("One of the parameters (order type or amount) is incorrect"));
		response = controller.makeOrder("BID", -1200);
		assertEquals(response, ResponseEntity.badRequest().body("One of the parameters (order type or amount) is incorrect"));
		
		response = controller.makeOrder("ASK", 1200);
		assertEquals(response, ResponseEntity.ok().body("Order made successfully"));
	}
	
	@Test
	public void cancelAnOrderTest() {
		
		ResponseEntity<String> response = controller.makeOrder("ASK", 1200);
		assertEquals(response, ResponseEntity.ok().body("Order made successfully"));
		
		when(tradeService.cancelOrder(5)).thenReturn(false);
		response = controller.cancelOrder(5);
		assertEquals(response, ResponseEntity.badRequest().body("The order is already matched or doesn´t exist in the system and can´t be cancel"));
		
		when(tradeService.cancelOrder(1)).thenReturn(true);
		response = controller.cancelOrder(1);
		assertEquals(response, ResponseEntity.ok().body("Order cancelled successfully"));
	}
	
	@Test
	public void getUnmatchedOdersTest() {
		
		ResponseEntity<String> response = controller.makeOrder("ASK", 1200);
		assertEquals(response, ResponseEntity.ok().body("Order made successfully"));

		when(tradeService.obtainUnMatchedOrders()).thenReturn(unmatchedOrderList);		
		assertEquals(controller.getUnmatchedOrders().size(), 1);
	}
	
	@Test
	public void getMatchedOdersTest() {
		
		ResponseEntity<String> response = controller.makeOrder("ASK", 1200);
		assertEquals(response, ResponseEntity.ok().body("Order made successfully"));
		response = controller.makeOrder("BID", 1200);
		assertEquals(response, ResponseEntity.ok().body("Order made successfully"));
		
		matchedOrderList.add(order);
		unmatchedOrderList.clear();
		order.setOrderType(OrderFXType.BID);
		matchedOrderList.add(order);
		
		when(tradeService.obtainMatchedOrders()).thenReturn(matchedOrderList);		
		assertEquals(controller.getMatchedOrders().size(), 2);
	}


}
