package com.project.WorldFirst.model;

import com.google.common.base.Objects;
import com.project.WorldFirst.model.OrderType.OrderFXType;

public class Order {
	
	public int orderId = 0;
	public int userId = 0;
	public OrderFXType orderType = null;
	public Currency currency = null;
	public double amount = 0.0;
	public boolean matched = false;
	public int matchedOrderID = 0;

	/**
	 * @return the matchedOrderID
	 */
	public int getMatchedOrderID() {
		return matchedOrderID;
	}
	/**
	 * @param matchedOrderID the matchedOrderID to set
	 */
	public void setMatchedOrderID(int matchedOrderID) {
		this.matchedOrderID = matchedOrderID;
	}
	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @return the orderType
	 */
	public OrderFXType getOrderType() {
		return orderType;
	}
	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @return the matched
	 */
	public boolean isMatched() {
		return matched;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(OrderFXType orderType) {
		this.orderType = orderType;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @param matched the matched to set
	 */
	public void setMatched(boolean matched) {
		this.matched = matched;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return userId == order.getUserId() &&
        		orderType.equals(order.getOrderType()) &&
        		currency.equals(order.getCurrency()) &&
        		amount == order.getAmount();
    }

	@Override
    public int hashCode() {
        return Objects.hashCode(userId, orderType, currency, amount);
    }
	

}
