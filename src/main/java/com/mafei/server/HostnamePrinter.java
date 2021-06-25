package com.mafei.server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author mafei
 * @Created 6/25/2021 7:42 PM
 */
public class HostnamePrinter {

    public static String print() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            System.out.println("Processed by host :: " + hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostName;
    }
}
