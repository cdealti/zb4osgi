package it.cnr.isti.basedriver.stub.api.impl;

public class NetworkBinding {
	
	private static NetworkBinding table = new NetworkBinding();
	
	public static final NetworkBinding getInstance(){
		return table;
	}
	

}
