package rwilk.logic;

import java.io.*;

/**
 * Created by Rafal Wilk
 */
public class FileOperations {

    /**
     * Metoda dopisuje do pliku.
     * Jesli nie ma pliku, program tworzy nowy plik i zapisuje do niego.
     *
     * @param file sciezka do pliku
     * @param text tekst, ktory chcemy dopisac
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
     * Metoda tworzy katalog, w ktorym beda przechowywane pliki stworzone przez program.
     * @param file plik, w ktorym zapisana jest sciezka do katalogu
     * @return sciezka do stworzonego katalogu
     */
    public static String readFile(String file) {
        BufferedReader br;
        String path = "";
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

            File folder = new File("C:\\KeyLogger");
            if (!folder.exists()) {
                if (folder.mkdir()) {
                    System.out.println("Directory is created!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            }
            writeToFile(file, "C:\\KeyLogger");
            return "C:\\KeyLogger";
        }
    }


}