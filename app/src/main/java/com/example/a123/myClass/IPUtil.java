package com.example.a123.myClass;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {

    /**
     * 解析域名获取IP数组
     * @param host
     * @return
     */
    public static String[] parseHostGetIPAddress(String host) {
        String[] ipAddressArr = null;
        try {
            InetAddress[] inetAddressArr = InetAddress.getAllByName(host);
            if (inetAddressArr != null && inetAddressArr.length > 0) {
                ipAddressArr = new String[inetAddressArr.length];
                for (int i = 0; i < inetAddressArr.length; i++) {
                    ipAddressArr[i] = inetAddressArr[i].getHostAddress();
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        return ipAddressArr;
    }

    public static String hostAddr2IPAddr(String hostAddr) {
        String[] parts = hostAddr.split("/");
        for(int i=0;i<parts.length;i++)
            Log.i(""+i, parts[i]);
        String[] IPs = parseHostGetIPAddress(parts[2]);
        for(String s: IPs)
            Log.i("IPs", s);
        return hostAddr.replaceAll(parts[2], IPs[0]);
    }

}
