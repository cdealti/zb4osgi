package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone;

public interface ZoneType {

	public class ZT{
		String attributeValue;
		String description;
		String alarm1;
		String alarm2;

		public ZT(String attributeValue, String description, String alarm1, String alarm2){
			this.attributeValue = attributeValue;
			this.description = description;
			this.alarm1 = alarm1;
			this.alarm2 = alarm2;
		}

		public String getAttributeValue(){ return this.attributeValue; }
		public String getDescription(){ return this.description; }
		public String getAlarm1(){ return this.alarm1; }
		public String getAlarm2(){ return this.alarm2; }
	}

	public final ZT STANDARD_CIE = new ZT("0000", "Standard CIE", "System Alarm", "");
	public final ZT MOTION_SENSOR = new ZT("000d", "Motion Sensor", "Intrusion detection", "Presence indication");
	public final ZT CONTACT_SWITCH = new ZT("0015", "Contact Switch", "First portal open-close", "Second portal open-close");
	public final ZT FIRE_SENSOR = new ZT("0028", "Fire Sensor", "Fire indication", "");
	public final ZT WATER_SENSOR = new ZT("002a", "Water Sensor", "Water overflow indication", "");
	public final ZT GAS_SENSOR = new ZT("002b", "Gas Sensor", "CO indication", "Cooking indication");
	public final ZT PERSONAL_EMERGENCY_DEVICE = new ZT("002c", "Personal Emergency Device", "Fall or concussion", "Emergency button");
	public final ZT VIBRATION_MOVEMENT_SENSOR = new ZT("002d", "Vibration / Movement Sensor", "Movement indication", "Vibration");
	public final ZT REMOTE_CONTROL = new ZT("010f", "Remote Control", "Panic", "Emergency");
	public final ZT KEY_FOB = new ZT("0115", "Key FOB", "Panic", "Emergency");
	public final ZT KEYPAD = new ZT("021d", "Keypad", "Panic", "Emergency");
	public final ZT STANDARD_WARNING_DEVICES = new ZT("0225", "Standard Warning Device", "", "");
}