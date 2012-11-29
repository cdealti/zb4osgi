package it.cnr.isti.zigbee.zcl.library.api.general.commissioning;

public interface RestartDeviceOptions {

	/* startup mode
	 * 0b000: restart installing and using current startup parameters set
	 * 0b001: restart using and not replacing current set of stack attributes	 * 
	 */
	byte[] getStartupMode();
	
	/* immediate
	 * 1: 	restart either immediately on receipt of the RestartDeviceRequest frame if delay 0
	 * 		or immediately after prescribed delay and jitter has transpired if not
	 * 0: 	restart until after prescribed delay and jitter (if any) has transpired
	 * 		but can also wait for a "convenient" moment (i.e. all pending frames have been transmitted)
	 */
	byte getImmediate(); 
	// byte[] getReserved();
}
