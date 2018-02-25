package com.bmstu.jpcap.client;

import org.osgi.service.component.annotations.Component;

import com.bmstu.jpcap.IJpcapClient;
import com.bmstu.jpcap.model.PcapPacket;

/**
 *
 * Default implementation of {@link JpcapClient}. Prints packet info.
 *
 * @author Koreneva
 *
 */
@Component(property= {"jpcap.device.name:String=\\Device\\NPF_{8C5556F7-ACBA-40BB-8D62-1287D077D536}"})
public class JpcapClient implements IJpcapClient {

	@Override
	public void processPacket(PcapPacket packet) {
		System.out.printf("Received packet at %s caplen=%-4d len=%-4d\n", packet.getDate(), packet.getCaplen(), packet.getWirelen());
	}
}
