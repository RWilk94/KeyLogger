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

    public static final long GIGA_BYTES = 1073741824;

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
     * Metoda zwraca publiczny adres IP.
     * @return publiczny adres IP.
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
     * Metoda zwraca ilość zainstalowanej pamięci RAM.
     * @return pamieć RAM w GB.
     */
    public Long getMemorySize(){
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        memorySize /= GIGA_BYTES;
        return memorySize + 1;
    }

    /**
     * Metoda zwraca liczbę rdzeni procesowa.
     * @return liczba rdzeni procesora.
     */
    public long getAvaibleProcessorsCores(){
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * Metoda odczytuje dane o dysku użytkownika takie jak partycje, ilość całkowitego miejsca oraz ilość wolnego miejsca.
     * @return tablica z informacjami o dysku
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
     * Metoda zwraca nazwę systemu operacyjnego użytkownika.
     * @return nazwa systemu operacyjnego użytkownika.
     */
    public String getNameOS(){
        return System.getProperty("os.name");
    }

    /**
     * Metoda zwraca aktualną wersję systemu użytkownika.
     * @return wersja systemu użytkownika.
     */
    public String getVersionOS(){
        return System.getProperty("os.version");
    }

    /**
     * Metoda zwraca informacje o architekturze systemu użytkownika.
     * @return informacje o architekturze systemu.
     */
    public String getArchitectureOS(){
        return System.getProperty("os.arch");
    }
}
