package com.project.WorldFirst.service;

import java.util.List;

import com.project.WorldFirst.model.Order;
import com.project.WorldFirst.model.OrderType.OrderFXType;

public interface TradeService {

	/**
	 * Trade service interface provides the operations to make 
	 * the order (BID or ASK).
	 * 
	 * * Also provides operations for storing and retrieving
	 * orders from the overall list.
	 */

	/**
	 * Register and order (BID or ASK) into the system.
	 * 
	 * @param userID of the person making the order
	 * @param currency conversion (default GBP/USD)
	 * @param amount to be traded
	 */
	void makeAnOrder(final OrderFXType orderTypeConversion, final float amount);
	
	/**
	 * Cancel and order that has not been already matched.
	 * 
	 * @param orderId of the transaction selected to cancel
	 */
	boolean cancelOrder(final int orderId);
	
	/**
	 * Retrieves all the orders that are matched.
	 * 
	 * @return
	 */
	List<Order> obtainMatchedOrders();
	
	/**
	 * Retrieves all the orders that are pending to be matched.
	 * 
	 * @return
	 */
	List<Order> obtainUnMatchedOrders();


}
