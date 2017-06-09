package rwilk.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Rafal Wilk
 */
public class HardwareInfo {

    private static final long GIGA_BYTES = 1073741824;

    /**
     * Metoda zwraca prywatny adres IP oraz adres MAC.
     * @return lista zawierajaca prywatny adres IP i adres MAC
     */
    public ArrayList<String> getIpAddress(){
        ArrayList<String> ipAddressList = new ArrayList<>();
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            ipAddressList.add(ip.toString());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            if(mac != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
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
