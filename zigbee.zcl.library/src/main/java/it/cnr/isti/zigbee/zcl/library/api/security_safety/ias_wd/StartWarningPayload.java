package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_wd;

public interface StartWarningPayload {

	public short getWarningMode();
	public short getStrobe();
	public int getWarningDuration();
}