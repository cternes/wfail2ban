package de.slackspace.wfail2ban.main;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class Wfail2banTest {

	@Test
	public void testStartApp() {
		Wfail2ban wfail2ban = new Wfail2ban(false);
		wfail2ban.readConfiguration("target/test-classes/jail.conf");
		wfail2ban.startLogfileMonitoring();
		wfail2ban.startFirewallMonitoring();
	}
}
