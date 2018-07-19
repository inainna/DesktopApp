package com.fortech;

import java.io.IOException;
import java.sql.Timestamp;

public class GeneratedKey {
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
        try {
            this.ipMac = SystemInfo.getMAC();
        }catch(IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.getIpMac());
        this.timestamp=Long.toString(timestamp.getTime());
    }

    public boolean compare (ValidationKey validationKey)
    {
        if(this.hostName==null || this.ipAddress==null || this.ipMac==null || this.timestamp==null)
            return false;
        if(this.getHostName().equals(validationKey.getHostName())&&this.getIpAddress().equals(validationKey.getIpAddress())&&this.getIpMac().equals(validationKey.getIpMac()))
            return true;
        else return false;
    }
}
