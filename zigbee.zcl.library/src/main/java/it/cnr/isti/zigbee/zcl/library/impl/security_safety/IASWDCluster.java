package it.cnr.isti.zigbee.zcl.library.impl.security_safety;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASWD;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_wd.SquawkPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_wd.StartWarningPayload;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_wd.SquawkCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_wd.StartWarningCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IASWDCluster extends ZCLClusterBase implements IASWD {

	private final AttributeImpl maxDuration;
	private final Attribute[] attributes;

	private final Logger log = LoggerFactory.getLogger(IASWDCluster.class);

	public IASWDCluster(ZigBeeDevice zbDevice) {
		super(zbDevice);
		maxDuration = new AttributeImpl(zbDevice, this,Attributes.MAX_DURATION);
		attributes = new AttributeImpl[]{maxDuration};
	}

	public Attribute getAttributeMaxDuration() {
		return maxDuration;
	}

	public void startWarning(StartWarningPayload payload) throws ZigBeeClusterException {
		StartWarningCommand startwarningcmd = new StartWarningCommand(payload);
		invoke(startwarningcmd);
	}

	public void squawk(SquawkPayload payload) throws ZigBeeClusterException {
		SquawkCommand squawkcmd = new SquawkCommand(payload);
		invoke(squawkcmd); 
	}

	@Override
	public short getId() {
		return IASWD.ID;
	}

	@Override
	public String getName() {
		return IASWD.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}
}