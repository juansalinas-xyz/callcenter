package com.salinas.matias.juan;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.salinas.matias.juan.entity.Call;

public class CallTest {
	
	private static final Logger logger = LoggerFactory.getLogger(CallTest.class);

	@Test
	void randomMinutes() {
		
		int min = 5;
		int max = 10;
		  
		for (int i = 0; i < 1000 ; i++) {
			Call c = new Call();
			logger.info("Segundos : " + c.getDurationInSeconds());
			assertTrue("Error, random muy corto",  min  <= c.getDurationInSeconds());
			assertTrue("Error, random muy largo", max >= c.getDurationInSeconds());
		}
	}
}
