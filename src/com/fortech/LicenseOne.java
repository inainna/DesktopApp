package com.fortech;

import java.sql.Timestamp;

public class LicenseOne {
    private String hostName;
    private String ipAddress;
    private String ipMac;
    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public String getHostName() {
        return hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getIpMac() {
        return ipMac;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setHostName(String sysInfo) {
        this.hostName = sysInfo;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setIpMac(String ipMac) {
        this.ipMac = ipMac;
    }

    public void initization(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.hostName=SystemInfo.getSystemName();
        this.ipAddress=SystemInfo.getIPAddress();
        this.ipMac=SystemInfo.getMAC();
        this.timestamp=Long.toString(timestamp.getTime());
    }
}
