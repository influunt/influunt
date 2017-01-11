package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigosol on 1/11/17.
 */
public class IPUtils {
    private IPUtils() {
    }

    public static List<String> listIps() {
        List<String> ips = new ArrayList<>();
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println(" IP Addr: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 0) {
                for (int i = 0; i < allMyIps.length; i++) {
                    ips.add(allMyIps[i].toString());
                }
            }
        } catch (UnknownHostException e) {

        }


        URL whatismyip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

            String ip = in.readLine();
            ips.add(ip);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ips;

    }
}
