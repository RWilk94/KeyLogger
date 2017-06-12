package rwilk.logic;

import java.io.*;

/**
 * Created by Rafal Wilk
 */
public class FileOperations {

    /**
     * Metoda dopisuje do pliku.
     * Jeśli nie ma pliku, program tworzy nowy plik i zapisuje do niego.
     *
     * @param file scieżka do pliku
     * @param text tekst, który chcemy dopisać
     */
    public static void writeToFile(String file, String text) {
        try {
            Writer output = new BufferedWriter(new FileWriter(file, false));
            output.append(text);
            output.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Metoda tworzy katalog, w którym będą przechowywane pliki stworzone przez program.
     *
     * @param file plik, w którym zapisana jest ścieżka do katalogu
     * @param path ścieżka do katalogu z filmami, jeśli katalog nie istnieje to zostanie stworzony
     * @return ścieżka do stworzonego katalogu
     */
    public static String readFile(String file, String path) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            try {
                path = br.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return path;
        } catch (FileNotFoundException e) {
            System.out.println(e);

            File folder = new File(path);
            if (!folder.exists()) {
                if (folder.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
            writeToFile(file, path);
            return path;
        }
    }
}