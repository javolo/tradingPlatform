package com.project.WorldFirst.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:globalConfig.properties")
@ConfigurationProperties
public class GlobalConfiguration {
	
	@Value("${trade-conversion}")
    private String conversion;

    @Value("${trade-rate}")
    private float rate;
    
    @Value("${userId}")
    private int userId;

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the conversion
	 */
	public String getConversion() {
		return conversion;
	}

	/**
	 * @param conversion the conversion to set
	 */
	public void setConversion(String conversion) {
		this.conversion = conversion;
	}

	/**
	 * @return the rate
	 */
	public float getRate() {
		return rate;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(float rate) {
		this.rate = rate;
	}
    
    
    
    
}
