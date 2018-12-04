package com.project.WorldFirst.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.WorldFirst.model.Order;
import com.project.WorldFirst.model.OrderType;
import com.project.WorldFirst.model.OrderType.OrderFXType;
import com.project.WorldFirst.service.TradeService;

@Controller
public class DummyFXController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DummyFXController.class);

	private final TradeService tradeService;

	@Autowired
	public DummyFXController(TradeService tradeService) {
		this.tradeService = tradeService;
	}


	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public ResponseEntity<String> makeOrder(@RequestParam String orderType, 
			@RequestParam float tradeAmount) {
		
		LOGGER.info("ORDER TYPE: " + orderType);
		LOGGER.info("AMOUNT: " + tradeAmount);

		if (validateOrderType(orderType) && tradeAmount >0) {
			// We get the order by the String received
			OrderType.OrderFXType orderTypeConversion = getOrderTypeFromString(orderType);
			tradeService.makeAnOrder(orderTypeConversion, tradeAmount);
		} else {
			return ResponseEntity.badRequest().body("One of the parameters (order type or amount) is incorrect");
		}

		return ResponseEntity.ok("Order made successfully");
	}

	private boolean validateOrderType(String orderType) {

		if (orderType != null && !orderType.isEmpty() && contains(orderType)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contains(String test) {

		for (OrderType.OrderFXType type : OrderType.OrderFXType.values()) {
			if (type.name().equals(test)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unlikely-arg-type")
	private OrderFXType getOrderTypeFromString(String orderType) {

		if (orderType.equals(OrderType.OrderFXType.ASK)) {
			return OrderType.OrderFXType.ASK;
		} else {
			return OrderType.OrderFXType.BID;
		}
	}

	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public ResponseEntity<String> cancelOrder(@RequestParam int orderId) {

		if (tradeService.cancelOrder(orderId)) {
			return ResponseEntity.ok("Order cancelled successfully");
		} else {
			return ResponseEntity.badRequest().body("The order is already matched or doesn´t exist in the system and can´t be cancel");
		}
	}

	@RequestMapping(value = "/getMatchedOrders", method = RequestMethod.GET)
	public List<Order> getMatchedOrders() {
		return tradeService.obtainMatchedOrders();
	}

	@RequestMapping(value = "/getUnmatchedOrders", method = RequestMethod.GET)
	public List<Order> getUnmatchedOrders() {
		return tradeService.obtainUnMatchedOrders();
	}

}
