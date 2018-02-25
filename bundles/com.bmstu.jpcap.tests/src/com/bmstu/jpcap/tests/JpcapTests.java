package com.bmstu.jpcap.tests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import com.bmstu.jpcap.IJpcapClient;
import com.bmstu.jpcap.IJpcapService;
import com.bmstu.jpcap.IJpcapServiceFactory;
import com.bmstu.jpcap.model.PcapPacket;

/**
 *
 * Tests for jpcap service
 *
 * @author Koreneva
 *
 */
public class JpcapTests {

	private static final String DEVICE_NAME = "\\Device\\NPF_{8C5556F7-ACBA-40BB-8D62-1287D077D536}";

	@Test
	public void serviceTest() {
		IJpcapServiceFactory serviceFactory = getService(IJpcapServiceFactory.class);
		assertNotNull("Jpcap service factory is null!", serviceFactory);

		IJpcapService service = serviceFactory.getService(DEVICE_NAME);
		assertNotNull("Jpcap service is null!", service);

		service.start();
		service.stop();
	}

	@Test
	public void clientTest() {
		IJpcapClient client = getService(IJpcapClient.class);
		assertNotNull("Jpcap client is null!", client);

		client.processPacket(new PcapPacket(System.currentTimeMillis(), 100, 100));
	}

	static <T> T getService(Class<T> clazz) {
		Bundle bundle = FrameworkUtil.getBundle(JpcapTests.class);
		if (bundle != null) {
			ServiceTracker<T, T> st = new ServiceTracker<T, T>(bundle.getBundleContext(), clazz, null);
			st.open();

			try {
				// give the runtime some time to startup
				return st.waitForService(500);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
