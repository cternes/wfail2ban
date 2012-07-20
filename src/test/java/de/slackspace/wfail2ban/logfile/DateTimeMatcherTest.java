package de.slackspace.wfail2ban.logfile;

import java.text.ParseException;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;

import de.slackspace.wfail2ban.logfile.impl.DateTimeMatcher;

public class DateTimeMatcherTest {

	private DateTimeMatcher matcher = new DateTimeMatcher();
	
	@Test
	public void testDateTimeMatcherStandard() {
		Calendar foundDate = matcher.matchDateTime("Jan 23 21:59:59 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherAscTime() {
		Calendar foundDate = matcher.matchDateTime("Fr Jul 20 18:03:30 2012 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherAscTimeWithoutYear() {
		Calendar foundDate = matcher.matchDateTime("Fr Jul 20 18:03:30 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherSimpleDate() {
		Calendar foundDate = matcher.matchDateTime("2012/07/21 18:14:27 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherAnotherSimpleDate() {
		Calendar foundDate = matcher.matchDateTime("20/07/2012 18:17:19 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherAnotherSimpleDateShortYear() {
		Calendar foundDate = matcher.matchDateTime("20/07/12 18:17:19 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherApacheFormat() throws ParseException {
		Calendar foundDate = matcher.matchDateTime("31/Oct/2012:09:22:55 -0000 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherExim() throws ParseException {
		Calendar foundDate = matcher.matchDateTime("2006-12-21 06:43:20 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherSyslog() throws ParseException {
		Calendar foundDate = matcher.matchDateTime("2006.12.21 06:43:20 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherDayFirst() throws ParseException {
		Calendar foundDate = matcher.matchDateTime("17-07-2008 17:23:25 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherApache() throws ParseException {
		Calendar foundDate = matcher.matchDateTime("99.7.224.201 - - [18/Jul/2009:15:04:47 +0200] \"GET /some/path/favicon.ico HTTP/1.1\" 404 1063");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherLog4j() {
		Calendar foundDate = matcher.matchDateTime("2012-07-20 08:45:55,680 DEBUG: de.slackspace.wfail2ban.logfile.impl.DefaultDateTimePattern ");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
	@Test
	public void testDateTimeMatcherIso8601() {
		Calendar foundDate = matcher.matchDateTime("2008-02-01T09:00:22 [sshd] error: PAM: Authentication failure");
		Assert.assertNotNull(foundDate);
		System.out.println("Found date: "+foundDate.getTime().toString());
	}
	
}
