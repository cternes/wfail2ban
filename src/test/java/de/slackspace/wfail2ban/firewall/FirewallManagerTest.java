package de.slackspace.wfail2ban.firewall;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.slackspace.wfail2ban.firewall.impl.DefaultFirewallManager;

public class FirewallManagerTest {

	@Test
	public void testFirewall() {
		FirewallManager firewall = new DefaultFirewallManager();
		
		Set<String> ipSet = new HashSet<String>();
		ipSet.add("192.168.255.6");
		ipSet.add("192.168.255.77");
		ipSet.add("192.168.255.8");
		ipSet.add("192.168.255.9");
		ipSet.add("192.168.255.10");
		
		firewall.addFirewallRules("apache", ipSet);
	}
}
