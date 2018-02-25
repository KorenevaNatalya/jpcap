package com.bmstu.jpcap;

/**
 *
 * Jpcap service. Throws package events to clients.
 *
 * @author Koreneva
 *
 */
public interface IJpcapService {
	/**
	 *
	 * Start jpcap service.
	 *
	 */
	void start();

	/**
	 *
	 * Stops jpcap service.
	 *
	 */
	void stop();

	/**
	 *
	 * Adds client to this service.
	 *
	 * @param client - client to add. Can't be <code>null</code>.
	 */
	void addClient(IJpcapClient client);

	/**
	 *
	 * Removes client from this service.
	 *
	 * @param client - client to remove. Can't be <code>null</code>.
	 */
	void removeClient(IJpcapClient client);
}
