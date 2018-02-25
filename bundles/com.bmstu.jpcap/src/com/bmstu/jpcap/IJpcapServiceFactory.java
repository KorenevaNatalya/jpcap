package com.bmstu.jpcap;

/**
 *
 * Factory for {@link IJpcapService}.
 * Jpcap factory searches {@link IJpcapClient jpcap clients} and launches jpcap services.
 *
 * @author Koreneva
 *
 */
public interface IJpcapServiceFactory {

	/**
	 *
	 * Returns service for given device name.
	 *
	 * @param deviceName - device name to get service. Can't be <code>null</code>.
	 *
	 * @return service for given device name. Can return <code>null</code>.
	 */
	IJpcapService getService(String deviceName);

}
