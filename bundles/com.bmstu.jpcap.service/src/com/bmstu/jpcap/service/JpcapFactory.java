package com.bmstu.jpcap.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.bmstu.jpcap.IJpcapClient;
import com.bmstu.jpcap.IJpcapService;
import com.bmstu.jpcap.IJpcapServiceFactory;

/**
 *
 * Implementation of {@link IJpcapServiceFactory}
 *
 * @author Koreneva
 *
 */
@Component(immediate = true)
public class JpcapFactory implements IJpcapServiceFactory {
	private static final String DEVICE_NAME_KEY = "jpcap.device.name";

	private Collection<IJpcapClient> clients;
	private Map<String, IJpcapService> deviceNameToServiceMap;

	/**
	 * Constructor.
	 */
	public JpcapFactory() {
		clients = new HashSet<>();
		deviceNameToServiceMap = new HashMap<>();
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void bindClient(IJpcapClient client, Map<String, String> properties) {
		String deviceName = properties.get(DEVICE_NAME_KEY);
		IJpcapService service = getService(deviceName);
		if (service != null) {
			service.addClient(client);
			service.start();
			clients.add(client);
		}
		else {
			System.err.println("There is no device with name: " + deviceName);
		}
	}

	public void unbindClient(IJpcapClient client) {
		clients.remove(client);
	}

	@Override
	public IJpcapService getService(String deviceName) {
		IJpcapService service = deviceNameToServiceMap.get(deviceName);
		if (service == null) {
			PcapIf device = getPcapDevice(deviceName);
			if (device != null) {
				service = deviceNameToServiceMap.computeIfAbsent(deviceName, key -> new JpcapService(device));
			}
		}

		return service;
	}

	private PcapIf getPcapDevice(String deviceName) {
		List<PcapIf> allDevices = new ArrayList<>();
		int result = Pcap.findAllDevs(allDevices, new StringBuilder());
		if (result != Pcap.OK || allDevices.isEmpty()) {
			return null;
		}

		return allDevices.stream().filter(device -> device.getName().equals(deviceName)).findAny().orElse(null);
	}
}
