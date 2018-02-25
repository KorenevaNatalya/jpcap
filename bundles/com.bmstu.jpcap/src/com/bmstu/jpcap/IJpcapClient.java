package com.bmstu.jpcap;

import com.bmstu.jpcap.model.PcapPacket;

/**
 *
 * Jpcap client. Processes jpcap service events.
 *
 * @author Koreneva
 *
 */
public interface IJpcapClient {

	/**
	 *
	 * Processes pcap packet.
	 *
	 * @param packet - pcap packet. Can't be <code>null</code>.
	 */
	void processPacket(PcapPacket packet);
}
