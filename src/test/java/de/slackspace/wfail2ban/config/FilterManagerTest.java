package de.slackspace.wfail2ban.config;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import de.slackspace.wfail2ban.config.impl.DefaultFilterManager;
import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResult;
import de.slackspace.wfail2ban.filter.FilterResultHandler;
import de.slackspace.wfail2ban.logfile.impl.SimpleLogfileReader;

public class FilterManagerTest {

	private static final String APACHE_ACCESS_LOG = "target/test-classes/apache-access.log";
	private static final File FILTER_FILE = new File("target/test-classes/filter/apache-proxy.conf");
	private FilterManager manager = new DefaultFilterManager();
	
	@Test
	public void testFilterManager() {
		Filter filter = manager.readFilterFile(FILTER_FILE);
		Assert.assertEquals("(?<host>(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})) - - \\[.*\\] \"GET http://.*", filter.getFailPattern().toString());
	}
	
	@Test
	public void testReadConfigAndMatch() {
		Filter filter = manager.readFilterFile(FILTER_FILE);
		Assert.assertEquals("(?<host>(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})) - - \\[.*\\] \"GET http://.*", filter.getFailPattern().toString());
		filter.setLogfilePath(APACHE_ACCESS_LOG);
		filter.setMaxRetry(1);
		
		final Set<String> ipsToBlock = new HashSet<String>();
		
		SimpleLogfileReader logfileReader = new SimpleLogfileReader();
		logfileReader.parseLogfile(filter, new FilterResultHandler() {
			
			@Override
			public void handleFilterResult(FilterResult filterResult) {
				ipsToBlock.addAll(filterResult.getIpsToBlock());
			}
		});
		
		Assert.assertEquals(5, ipsToBlock.size());
	}
}
