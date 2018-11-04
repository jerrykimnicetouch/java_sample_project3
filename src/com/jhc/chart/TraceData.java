package com.jhc.chart;

public class TraceData {
	
	private String sourceIP;
	private String desIP;
	int sourceVol;
	int desVol;
	 
	
	public TraceData() {
		
	}
	
	public void setSourceIP(String ip) {
		sourceIP = ip;
	}
	public void setDesIP(String ip) {
		desIP = ip;
	}
	public void setSourceVol(int vol) {
		sourceVol = vol;
	}
	public void setDesVol(int vol) {
		desVol = vol;
	}
	
	public String getSourceIP() {
		return sourceIP;
	}
	public String getDesIP() {
		return desIP;
	}
	public int getSourceVol() {
		return sourceVol;
	}
	public int getDesVol() {
		return desVol;
	}
	

}
