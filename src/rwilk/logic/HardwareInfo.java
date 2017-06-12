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
     * Metoda zwraca publiczny adres IP.
     *
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
            return null;
        }
        return publicIP;
    }

    /**
     * Metoda zwraca ilość zainstalowanej pamięci RAM.
     *
     * @return pamieć RAM w GB.
     */
    public Long getMemorySize() {
        long memorySize = ((com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
        memorySize /= GIGA_BYTES;
        return memorySize + 1;
    }

    /**
     * Metoda zwraca nazwę systemu operacyjnego użytkownika.
     *
     * @return nazwa systemu operacyjnego użytkownika.
     */
    public String getNameOS() {
        return System.getProperty("os.name");
    }

    /**
     * Metoda zwraca aktualną wersję systemu użytkownika.
     *
     * @return wersja systemu użytkownika.
     */
    public String getVersionOS() {
        return System.getProperty("os.version");
    }

    /**
     * Metoda zwraca informacje o architekturze systemu użytkownika.
     *
     * @return informacje o architekturze systemu.
     */
    public String getArchitectureOS() {
        return System.getProperty("os.arch");
    }
}
