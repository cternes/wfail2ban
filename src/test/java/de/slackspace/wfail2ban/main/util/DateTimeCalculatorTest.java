package de.slackspace.wfail2ban.main.util;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import de.slackspace.wfail2ban.util.DateTimeCalculator;

public class DateTimeCalculatorTest {

	@Test
	public void testDiff300Seconds() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2012, 0, 1, 8, 10, 0); //08:10:00
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2012, 0, 1, 8, 15, 0); //08:15:00
		
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(cal1, cal2);
		Assert.assertEquals(300, diffInSeconds);
	}
	
	@Test
	public void testDiff60Seconds() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2012, 0, 1, 8, 10, 0); //08:10:00
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2012, 0, 1, 8, 9, 0); //08:09:00
		
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(cal1, cal2);
		Assert.assertEquals(60, diffInSeconds);
	}
	
	@Test
	public void testDiff2Seconds() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2012, 0, 1, 8, 10, 7); //08:10:07
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2012, 0, 1, 8, 10, 9); //08:10:09
		
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(cal1, cal2);
		Assert.assertEquals(2, diffInSeconds);
	}
	
	@Test
	public void testDiffNullSecond() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2012, 0, 1, 8, 10, 7); //08:10:07
		
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(cal1, null);
		Assert.assertEquals(-1, diffInSeconds);
	}
	
	@Test
	public void testDiffNullFirst() {
		Calendar cal1 = Calendar.getInstance();
		cal1.set(2012, 0, 1, 8, 10, 7); //08:10:07
		
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(null, cal1);
		Assert.assertEquals(-1, diffInSeconds);
	}
	
	@Test
	public void testDiffNullBoth() {
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(null, null);
		Assert.assertEquals(-1, diffInSeconds);
	}
}
