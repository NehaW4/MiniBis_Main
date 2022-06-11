package com.example.minibis;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CommonUtility {
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {}
        return false;
    }
}
