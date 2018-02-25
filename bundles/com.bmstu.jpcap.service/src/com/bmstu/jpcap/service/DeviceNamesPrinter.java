package com.bmstu.jpcap.service;

import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

/**
*
* Prints device names
*
* @author Koreneva
*
*/
public class DeviceNamesPrinter {

	public static void main(String[] args) {
		List<PcapIf> allDevices = new ArrayList<>();
		int result = Pcap.findAllDevs(allDevices, new StringBuilder());
		if (result != Pcap.OK || allDevices.isEmpty()) {
			return;
		}

		allDevices.stream().forEach(device -> System.out.println(device.getName()));
	}

}
