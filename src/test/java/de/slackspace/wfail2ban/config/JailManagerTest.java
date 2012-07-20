package de.slackspace.wfail2ban.config;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import de.slackspace.wfail2ban.config.impl.DefaultConfiguration;
import de.slackspace.wfail2ban.config.impl.DefaultJailManager;
import de.slackspace.wfail2ban.filter.Filter;

public class JailManagerTest {
	
	private static final String FILTER_CONFIG_FOLDER = "target/test-classes/filter/";
	private static final File JAIL_CONFIG_FILE = new File("target/test-classes/jail.conf");
	private static final File JAIL_CONFIG_FILE_DEFAULT = new File("target/test-classes/jail-default.conf");
	private static final String ACCESS_LOG = "target/test-classes/apache-access.log";

	@Test
	public void testJailManager() {
		DefaultJailManager manager = new DefaultJailManager(JAIL_CONFIG_FILE);
		manager.setFilterDirectory(FILTER_CONFIG_FOLDER);
		DefaultConfiguration config = manager.readDefaultConfig();
		List<Filter> filterList = manager.readJails(config);

		Filter filter = filterList.get(0);
		Assert.assertEquals(5, filter.getMaxRetry());
		Assert.assertEquals("(?<host>(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})) - - \\[.*\\] \"GET http://.*", filter.getFailPattern().toString());
		Assert.assertEquals(ACCESS_LOG, filter.getLogfilePath());
	}
	
	@Test
	public void testDefaultConfig() {
		DefaultJailManager manager = new DefaultJailManager(JAIL_CONFIG_FILE);
		manager.setFilterDirectory(FILTER_CONFIG_FOLDER);
		DefaultConfiguration defaultConfig = manager.readDefaultConfig();
		Assert.assertEquals(10000L, defaultConfig.getDelayPeriod());
		Assert.assertEquals(40000L, defaultConfig.getRefreshPeriod());
		Assert.assertEquals(300L, defaultConfig.getFindtime());
		Assert.assertEquals(2, defaultConfig.getMaxRetry());
	}
	
	@Test
	public void testReadFiltersWithDefaultParams() {
		DefaultJailManager manager = new DefaultJailManager(JAIL_CONFIG_FILE_DEFAULT);
		manager.setFilterDirectory(FILTER_CONFIG_FOLDER);
		
		DefaultConfiguration defaultConfig = manager.readDefaultConfig();
		Assert.assertEquals(120L, defaultConfig.getFindtime());
		Assert.assertEquals(10, defaultConfig.getMaxRetry());
		
		List<Filter> filterList = manager.readJails(defaultConfig);

		Filter filter = filterList.get(0);
		Assert.assertEquals("(?<host>(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})) - - \\[.*\\] \"GET http://.*", filter.getFailPattern().toString());
		Assert.assertEquals(ACCESS_LOG, filter.getLogfilePath());
		Assert.assertEquals(filter.getFindtime(), 120L);
		Assert.assertEquals(filter.getMaxRetry(), 10);
	}
	
}
