package com.project.WorldFirst.model;

import com.google.common.base.Objects;

public class Currency {
	
	private String changeType = null;
	private float price = 0f;
	
	/**
	 * @return the changeType
	 */
	public String getChangeType() {
		return changeType;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param changeType the changeType to set
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Currency curr = (Currency) o;
        return changeType == curr.getChangeType() &&
        		price == curr.getPrice();
    }

	@Override
    public int hashCode() {
        return Objects.hashCode(changeType, price);
    }
	

}
