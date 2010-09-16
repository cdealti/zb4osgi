package it.cnr.isti.basedriver.stub;

import it.cnr.isti.basedriver.stub.api.impl.CurtainActuatorDevice;
import it.cnr.isti.basedriver.stub.api.impl.FloodDetectorDevice;
import it.cnr.isti.basedriver.stub.api.impl.OnOffDevice;
import it.cnr.isti.basedriver.stub.api.impl.OnOffLightDevice;
import it.cnr.isti.basedriver.stub.api.impl.StubZigBeeNode;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		
		ZigBeeNode alpha = new StubZigBeeNode("11:22:33:44:55:66:77:88",0x0100);
		ZigBeeNode beta = new StubZigBeeNode("01:02:03:04:05:06:07:08",0x0010);
		ZigBeeNode gamma = new StubZigBeeNode("01:02:03:04:05:06:07:09",0x0010);
		
		OnOffDevice[] devices = new OnOffDevice[3];
		OnOffLightDevice[] lights = new OnOffLightDevice[4];
		int  i = 0;
		while ( i < devices.length && i < lights.length ){
			devices[i] = new OnOffDevice((i+2)*3,alpha); 
			context.registerService(
					ZigBeeDevice.class.getName(),
					devices[i],
					devices[i].getDescription()
			);
			
			lights[i] = new OnOffLightDevice((i+2)*3+1,alpha);
			context.registerService(
					ZigBeeDevice.class.getName(),
					lights[i],
					lights[i].getDescription()
			);
			i++;
		}
		while ( i < devices.length ){
			devices[i] = new OnOffDevice(i*3,beta);
			context.registerService(
					ZigBeeDevice.class.getName(),
					devices[i],
					devices[i].getDescription()
			);
			i++;
		}
		while ( i < lights.length ){
			lights[i] = new OnOffLightDevice(i*3,beta);
			context.registerService(
					ZigBeeDevice.class.getName(),
					lights[i],
					lights[i].getDescription()
			);
			i++;
		}
		
		CurtainActuatorDevice curtain= new CurtainActuatorDevice(77,gamma);
		context.registerService(
				ZigBeeDevice.class.getName(),
				curtain,
				curtain.getDescription()
		);
		FloodDetectorDevice device= new FloodDetectorDevice(78,gamma);
		context.registerService(
				ZigBeeDevice.class.getName(),
				device,
				device.getDescription()
		);
		
	}

	public void stop(BundleContext arg0) throws Exception {
	}

}
