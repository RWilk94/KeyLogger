package rwilk.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by R. Wilk on 28.04.2017.
 * This class gives info about operating system, hardware and network.
 */
public class HardwareInfo {

    public static final long GIGA_BYTES = 1073741824;

    /**
     *   /**
     * Method return list contains our private IP address and MAC.
     * If we don't have Internet connection, method return localhost address and null mac.
     * @return contains our private IP address and MAC
     */
    public ArrayList<String> getIpAddress(){
        ArrayList<String> ipAddressList = new ArrayList<>();
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost(); //read IP address
            ipAddressList.add(ip.toString()); //add to list

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            if(mac != null) { //if we don't have Internet connection, mac is null
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                } //%two letters and one char between
                ipAddressList.add(sb.toString());
            }
        } catch (UnknownHostException | SocketException e) {
            return null;
        }
        return ipAddressList;
    }

    /**
     * If we are connected to Internet, method return public IP address.
     * Otherwise generate exception.
     */
    public String getPublicIpAddress() {
        URL myIPAddress;
        String publicIP;
        try {
            myIPAddress = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myIPAddress.openStream()));
             publicIP = in.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
        return publicIP;
    }

    /**
     * Return RAM size in gigaBytes
     */
    public Long getMemorySize(){
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        memorySize /= GIGA_BYTES;
        return memorySize + 1;
    }

    /**
     * Method return count of processor cores.
     */
    public long getAvaibleProcessorsCores(){
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Method return info about disks in system, like name, total space and free space.
     * The values are in GB.
     */
    public File[] getInfoAboutDisc(){
        //to jest ciekawe, jak to wyswietlic?
        File[] roots = File.listRoots();

        for (File root : roots) {
            System.out.println("File system root: " + root.getAbsolutePath());
            System.out.println("Total space (GB): " + root.getTotalSpace()/GIGA_BYTES);
            System.out.println("Free space (GB): " + root.getFreeSpace()/GIGA_BYTES + "\n");
        }
        return roots;
    }

    /**
     * Method return info about name operating system.
     */
    public String getNameOS(){
        return System.getProperty("os.name");
    }

    /**
     * Method return info about version operating system.
     */
    public String getVersionOS(){
        return System.getProperty("os.version");
    }

    /**
     * Method return info about architecture operating system.
     */
    public String getArchitectureOS(){
        return System.getProperty("os.arch");
    }
}
