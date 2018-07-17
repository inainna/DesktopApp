package com.fortech;

import java.util.Date;
import com.google.gson.Gson;

public class ValidationKey {

    private String hostName;
    private String ipAddress;
    private String ipMac;
    private String timestamp;
    private String start_date;
    private String finish_date;
    private String client;

    public String getHostName() {
        return hostName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getIpMac() {
        return ipMac;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public String getClient() {
        return client;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setIpMac(String ipMac) {
        this.ipMac = ipMac;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }

    public void setClient(String client) {
        this.client = client;
    }

}
