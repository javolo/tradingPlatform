package com.project.WorldFirst.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.WorldFirst.model.Currency;
import com.project.WorldFirst.model.GlobalConfiguration;
import com.project.WorldFirst.model.Order;
import com.project.WorldFirst.model.OrderType;
import com.project.WorldFirst.model.OrderType.OrderFXType;

@Service
public class TradeServiceImpl implements TradeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);

	private List<Order> matchedOrderList;
	private List<Order> unmatchedOrderList;
	private GlobalConfiguration global;
	private int idGenerator = 0;

	@Autowired
	public TradeServiceImpl(List<Order> matchedOrderList, List<Order> unmatchedOrderList, GlobalConfiguration global) {
		this.global = global;
		this.matchedOrderList = matchedOrderList;
		this.unmatchedOrderList = unmatchedOrderList;
	}

	@Override
	public void makeAnOrder(OrderFXType orderType, float amount) {
		LOGGER.debug("Received makeOder request for userID, Currency and amount {}, {}", orderType, amount);

		// Generate the currency from the Global Properties file
		Currency curr = new Currency();
		curr.setChangeType(global.getConversion());
		curr.setPrice(global.getRate());

		// Generate the order object
		Order order = new Order();
		order.setCurrency(curr);
		order.setAmount(amount);
		order.setOrderType(orderType);
		order.setUserId(global.getUserId());
		idGenerator++;
		order.setOrderId(idGenerator);

		// Before to adding to any of the list (matched or unmatched) we need to see if 
		int matchedOrder = checkIfOrderCanBeMatched(order);
		if (matchedOrder == 0) {
			// That means there is no order that match the criteria
			order.setMatched(false);
			unmatchedOrderList.add(order);
		} else {
			order.setMatched(true);
			order.setMatchedOrderID(matchedOrder);
			matchedOrderList.add(order);

			// We remove the order from unmatched and plug it in the matched list
			cancelOrder(matchedOrder);
		}
	}

	/**
	 * Check if an existing order can be matched against the order the user want to do.
	 * 
	 * @param order we just created
	 * @param unmatchedOrders: list of orders pending to match
	 * @return the orderID (if exists) of an unmatched order already in the system
	 */
	private int checkIfOrderCanBeMatched(final Order order) {

		int result = 0;
		for (Order ord: unmatchedOrderList) {
			if (((order.getOrderType().equals(OrderType.OrderFXType.ASK) && ord.getOrderType().equals(OrderType.OrderFXType.BID)) 
					|| (order.getOrderType().equals(OrderType.OrderFXType.BID) && ord.getOrderType().equals(OrderType.OrderFXType.ASK))) && 
					ord.amount == order.amount) {
				// If the amount it´s the same we update the flag and the order id of the order
				// With a database I will call an update query for both orders
				
				// I´ll update the details of the order that now it´s matched
				ord.setMatched(true);
				ord.setMatchedOrderID(order.getOrderId());
				
				return ord.getOrderId();
			}
		}
		return result;
	}

	@Override
	/**
	 * Remove the order from the system. If the order is already matched the order can´t be cancel. Only if the order is present in the unmatched orders.
	 * 
	 *  @param orderId that we want to cancel
	 */
	public boolean cancelOrder(int orderId) {
		
		LOGGER.debug("Received cancel order request for orderId{}", orderId);

		Map<Integer, Order> unmatchedMap = unmatchedOrderList.stream().collect(Collectors.toMap(Order::getOrderId, item -> item));
		Order mappedOrder = unmatchedMap.getOrDefault(orderId, null);
		if (mappedOrder != null) {
			unmatchedMap.remove(mappedOrder.getOrderId());
			unmatchedOrderList = unmatchedMap.values().stream().collect(Collectors.toList());
			return true;
		} else {
			return false;
		}
	}

	@Override
	/**
	 * This method could be different with a database as I would query the DB to obtain the orders filter by the matched column.
	 * 
	 * Retrieves the unmatched orders of the system
	 * 
	 */
	public List<Order> obtainMatchedOrders() {
		LOGGER.debug("Received Obtain Matched orders request for orderId ");
		return matchedOrderList;
	}

	@Override
	/**
	 * Retrieves the unmatched orders of the system
	 */
	public List<Order> obtainUnMatchedOrders() {
		LOGGER.debug("Received Obtain Unmatched orders request for orderId ");
		return unmatchedOrderList;
	}
}
