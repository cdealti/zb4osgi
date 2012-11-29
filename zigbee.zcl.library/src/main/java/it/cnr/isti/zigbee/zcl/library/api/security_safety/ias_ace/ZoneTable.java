package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace;

public interface ZoneTable {

	class Zone{

		Short zoneID;
		Short zoneType;
		String zoneAddress;	

		public Zone(Short zoneID, Short zoneType, String zoneAddress){
			this.zoneID = zoneID;
			this.zoneType = zoneType;
			this.zoneAddress = zoneAddress;
		}

		public Short getZoneID() {
			return zoneID;
		}
		public void setZoneID(Short zoneID) {
			this.zoneID = zoneID;
		}
		public Short getZoneType() {
			return zoneType;
		}
		public void setZoneType(Short zoneType) {
			this.zoneType = zoneType;
		}
		public String getZoneAddress() {
			return zoneAddress;
		}
		public void setZoneAddress(String zoneAddress) {
			this.zoneAddress = zoneAddress;
		}
	}

	public Zone[] getZoneTable();

	public void addZone(Short zoneID, Short zoneType, String zoneAddress);

	public void removeZone(Short zoneID);
}