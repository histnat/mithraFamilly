package net.mithra.familly.log;

import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.slf4j.Logger;

public class LogTest {
	
	private static final Logger logger= LoggerFactory.getLogger(LogTest.class);
	
	
	@Test
	public void logTest()
	{
		logger.debug("test");
	}

}
