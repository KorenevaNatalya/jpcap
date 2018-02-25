package com.bmstu.jpcap.model;

import java.util.Date;

/**
 *
 * Instance of this class represents pcap packet.
 *
 * @author Koreneva
 *
 */
public class PcapPacket {
	private Date date; // from pcap javadoc:
	private int caplen; // size of the capture packet data
	private int wirelen; // value of the hdr_wirelen field

	/**
	 *
	 * Consturcor.
	 *
	 * @param millis - packet time in millisecconds.
	 * @param caplen - packet caplen.
	 * @param wirelen - packet wirelen.
	 */
	public PcapPacket(long millis, int caplen, int wirelen) {
		this.caplen = caplen;
		this.wirelen = wirelen;
		date = new Date(millis);
	}

	/**
	 *
	 * Returns packet date.
	 *
	 * @return packet date.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 *
	 * Returns packet caplen.
	 *
	 * @return packet caplen.
	 */
	public int getCaplen() {
		return caplen;
	}

	/**
	 *
	 * Returns packet wirelen.
	 *
	 * @return packet wirelen.
	 */
	public int getWirelen() {
		return wirelen;
	}
}
