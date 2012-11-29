package it.cnr.isti.zigbee.zcl.library.api.general.commissioning;

public interface ResetStartupOptions {

	byte getResetCurrent();
	byte getResetAll();
	byte getEraseIndex();
	// byte[] getReserved();
}