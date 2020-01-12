package com.salinas.matias.juan.entity;

import java.util.Random;

/**
 * 
 * @author Juan M. Salinas 
 * Modela una llamada.
 * 
 */

public class Call {

	private static final Integer MIN_DURATION_IN_SECONDS = 5;
	private static final Integer MAX_DURATION_IN_SECONDS = 10;
	
	private Integer durationInSeconds;
	
	public Call() {
		Random rn = new Random();
		this.durationInSeconds = rn.nextInt(MAX_DURATION_IN_SECONDS - MIN_DURATION_IN_SECONDS + 1 ) + MIN_DURATION_IN_SECONDS;
	}

	public Integer getDurationInSeconds() {
		return durationInSeconds;
	}

	public void setDurationInSeconds(Integer durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}
	
}
