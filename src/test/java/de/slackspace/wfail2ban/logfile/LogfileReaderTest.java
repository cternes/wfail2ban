package de.slackspace.wfail2ban.logfile;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResult;
import de.slackspace.wfail2ban.filter.FilterResultHandler;
import de.slackspace.wfail2ban.filter.impl.RegexFilter;
import de.slackspace.wfail2ban.logfile.impl.SimpleLogfileReader;

public class LogfileReaderTest {

	private static final String LOGFILE = "target/test-classes/apache-access.log"; 
	private LogfileReader reader = new SimpleLogfileReader();
	
	@Test
	public void testReader() {
		final Set<String> ipsToBlock = new HashSet<String>();
		
		//78.191.162.16 - - [18/Jul/2012:10:07:36 +0200] "GET http://images.google.com/ HTTP/1.1" 302 230
		Filter filter = new RegexFilter(LOGFILE, "<HOST> - - \\[.*\\] \"GET http://.*"); //(.*) - - \\[.*\\].*
		filter.setMaxRetry(1);
		filter.setFindtime(3600);
		reader.parseLogfile(filter, new FilterResultHandler() {
			
			@Override
			public void handleFilterResult(FilterResult filterResult) {
				ipsToBlock.addAll(filterResult.getIpsToBlock());
			}
		});
		Assert.assertEquals(5, ipsToBlock.size());
	}
	
	/**
	 * ==
	 * maxretry = 3
	 * findtime = 600
	 * ==
	 *  
	 * Testing proxy access of IP's in test-ticket.log.
	 * IP 78.191.162.16 does 3 retries in <600s and needs to be blocked
	 * IP 99.191.162.30 does 4 retries, but time between retries is >600s, so must not blocked 
	 */
	@Test
	public void testTicketManager() {
		final Set<String> ipsToBlock = new HashSet<String>();
		
		Filter filter = new RegexFilter("target/test-classes/test-ticket.log", "<HOST> - - \\[.*\\] \"GET http://.*");
		reader.parseLogfile(filter, new FilterResultHandler() {
			
			@Override
			public void handleFilterResult(FilterResult filterResult) {
				ipsToBlock.addAll(filterResult.getIpsToBlock());
			}
		});
		Assert.assertEquals(1, ipsToBlock.size());
	}
	
	/**
	 * ==
	 * maxretry = 2
	 * findtime = 600
	 * ==
	 *  
	 * Testing proxy access of IP's in test-ticket.log.
	 * IP 78.191.162.16 does 3 retries in <600s and needs to be blocked
	 * IP 99.191.162.30 does 2 retries in <600s and needs to be blocked 
	 */
	@Test
	public void testTicketManagerMaxRetryTwo() {
		final Set<String> ipsToBlock = new HashSet<String>();
		
		Filter filter = new RegexFilter("target/test-classes/test-ticket.log", "<HOST> - - \\[.*\\] \"GET http://.*");
		filter.setMaxRetry(2);
		reader.parseLogfile(filter, new FilterResultHandler() {
			
			@Override
			public void handleFilterResult(FilterResult filterResult) {
				ipsToBlock.addAll(filterResult.getIpsToBlock());
			}
		});
		Assert.assertEquals(2, ipsToBlock.size());
	}
	
	/**
	 * ==
	 * maxretry = 3
	 * findtime = 3600 //60 minutes
	 * ==
	 *  
	 * Testing proxy access of IP's in test-ticket.log.
	 * IP 78.191.162.16 does 3 retries in <1800s and needs to be blocked
	 * IP 99.191.162.30 does 4 retries in <1800s and needs to be blocked 
	 */
	@Test
	public void testTicketManagerFindTime30Minutes() {
		final Set<String> ipsToBlock = new HashSet<String>();
		
		Filter filter = new RegexFilter("target/test-classes/test-ticket.log", "<HOST> - - \\[.*\\] \"GET http://.*");
		filter.setFindtime(3600);
		reader.parseLogfile(filter, new FilterResultHandler() {
			
			@Override
			public void handleFilterResult(FilterResult filterResult) {
				ipsToBlock.addAll(filterResult.getIpsToBlock());
			}
		});
		Assert.assertEquals(2, ipsToBlock.size());
	}
}
