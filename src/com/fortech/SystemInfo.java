package com.fortech;

import java.net.InetAddress;
        import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class SystemInfo {
    public static String getSystemName() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost(); //Get LocalHost refrence
            String name = inetaddress.getHostName();   //Get Host Name
            return name;   //return Host Name
        } catch (Exception E) {
            E.printStackTrace();  //print Exception StackTrace
            return null;
        }
    }

    public static String getIPAddress() {
        try {
            InetAddress inetaddress = InetAddress.getLocalHost();  //Get LocalHost refrence
            String ip = inetaddress.getHostAddress();  // Get Host IP Address
            return ip;   // return IP Address
        } catch (Exception E) {
            E.printStackTrace();  //print Exception StackTrace
            return null;
        }

    }

    public static String getMAC() throws SocketException {


        Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
        NetworkInterface inter;
        StringBuilder str = new StringBuilder();
        while (networks.hasMoreElements()) {
            inter = networks.nextElement();
            byte[] mac = inter.getHardwareAddress();

            if (mac != null) {
                for (int i = 0; i < mac.length; i++) {
                    str.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }


            }

        }
        return str.toString();

    }
}

