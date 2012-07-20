package de.slackspace.wfail2ban.firewall;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import de.slackspace.wfail2ban.firewall.impl.FirewallPreprocessor;

public class FirewallPreprocessorTest {

	@Test
	public void testPreprocessorEven() {
		FirewallPreprocessor processor = new FirewallPreprocessor(2);
		Set<String> ipSet = createIpSet();
		
		List<String> packages = processor.prepareIpSet(ipSet);
		Assert.assertEquals(5, packages.size());
		
		int counter = 1;
		for (String ipPackage : packages) {
			System.out.println("Package "+counter+": "+ipPackage);
			counter++;
		}
	}
	
	@Test
	public void testPreprocessorOdd() {
		FirewallPreprocessor processor = new FirewallPreprocessor(3);
		Set<String> ipSet = createIpSet();
		
		List<String> packages = processor.prepareIpSet(ipSet);
		Assert.assertEquals(4, packages.size());
		
		int counter = 1;
		for (String ipPackage : packages) {
			System.out.println("Package "+counter+": "+ipPackage);
			counter++;
		}
	}

	private Set<String> createIpSet() {
		Set<String> ipSet = new HashSet<String>();
		ipSet.add("a");
		ipSet.add("b");
		ipSet.add("c");
		ipSet.add("d");
		ipSet.add("e");
		ipSet.add("f");
		ipSet.add("g");
		ipSet.add("h");
		ipSet.add("i");
		ipSet.add("j");
		return ipSet;
	}
}
