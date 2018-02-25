package com.bmstu.jpcap.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Executors;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;

import com.bmstu.jpcap.IJpcapClient;
import com.bmstu.jpcap.IJpcapService;

/**
 *
 * Implementation of {@link JpcapService}.
 *
 * @author Koreneva
 *
 */
public class JpcapService implements IJpcapService {
	private final PcapIf device;
	private Collection<IJpcapClient> clients;
	private boolean isRunning;

	/**
	 * Constructor.
	 *
	 * @param device - device to listen. Can't be <code>null</code>.
	 */
	public JpcapService(PcapIf device) {
		this.device = device;
		clients = new HashSet<>();
	}

	@Override
	public void start() {
		if (!isRunning) {
			isRunning = true;
			startPcap();
		}
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public void addClient(IJpcapClient client) {
		clients.add(client);
	}

	@Override
	public void removeClient(IJpcapClient client) {
		clients.remove(client);
	}

	private void startPcap() {
		Executors.newFixedThreadPool(1).submit(new JPcapLoop(new StringBuilder(), device));
	}

	private class JPcapLoop implements Runnable {
		private StringBuilder errorBuffer;
		private PcapIf device;

		public JPcapLoop(StringBuilder errorBuffer, PcapIf device) {
			this.errorBuffer = errorBuffer;
			this.device = device;
		}

		@Override
		public void run() {
			int snaplen = 64 * 1024; // Capture all packets, no trucation
			int flags = Pcap.MODE_PROMISCUOUS; // capture all packets
			int timeout = 10 * 1000; // 10 seconds in millis
			Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errorBuffer);
			if (pcap == null) {
				System.err.printf("Error while opening device for capture: " + errorBuffer.toString());
				return;
			}

			while (isRunning) {
				pcap.loop(1, new JPcapPacketHandler(), "");
			}
			pcap.close();
		}
	}

	private class JPcapPacketHandler implements PcapPacketHandler<String> {

		@Override
		public void nextPacket(PcapPacket packet, String userString) {
			sendEvent(new com.bmstu.jpcap.model.PcapPacket(packet.getCaptureHeader().timestampInMillis(), packet.getCaptureHeader().caplen(), packet.getCaptureHeader().wirelen()));
		}

		private void sendEvent(com.bmstu.jpcap.model.PcapPacket packet) {
			clients.stream().forEach(client -> client.processPacket(packet));
		}
	}
}
