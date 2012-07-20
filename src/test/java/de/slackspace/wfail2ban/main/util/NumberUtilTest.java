package de.slackspace.wfail2ban.main.util;

import junit.framework.Assert;

import org.junit.Test;

import de.slackspace.wfail2ban.util.NumberUtil;

public class NumberUtilTest {

	@Test
	public void testIsInteger() {
		Assert.assertEquals(true, NumberUtil.isInteger("1"));
		Assert.assertEquals(false, NumberUtil.isInteger("1.1"));
		Assert.assertEquals(false, NumberUtil.isInteger("abc2"));
		Assert.assertEquals(false, NumberUtil.isInteger(""));
		Assert.assertEquals(false, NumberUtil.isInteger(null));
	}
	
	@Test
	public void testToInteger() {
		Assert.assertEquals(1, NumberUtil.toInteger("1"));
		Assert.assertEquals(-1, NumberUtil.toInteger("1.1"));
		Assert.assertEquals(-1, NumberUtil.toInteger("abc2"));
		Assert.assertEquals(-1, NumberUtil.toInteger(""));
		Assert.assertEquals(-1, NumberUtil.toInteger(null));
	}
	
	@Test
	public void testIsLong() {
		Assert.assertEquals(true, NumberUtil.isLong("1"));
		Assert.assertEquals(false, NumberUtil.isLong("1.1"));
		Assert.assertEquals(false, NumberUtil.isLong("abc2"));
		Assert.assertEquals(false, NumberUtil.isLong(""));
		Assert.assertEquals(false, NumberUtil.isLong(null));
	}
	
	@Test
	public void testToLong() {
		Assert.assertEquals(1, NumberUtil.toLong("1"));
		Assert.assertEquals(-1, NumberUtil.toLong("1.1"));
		Assert.assertEquals(-1, NumberUtil.toLong("abc2"));
		Assert.assertEquals(-1, NumberUtil.toLong(""));
		Assert.assertEquals(-1, NumberUtil.toLong(null));
	}
}
