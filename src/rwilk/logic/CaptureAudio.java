package rwilk.logic;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Rafał Wilk
 */
public class CaptureAudio implements Runnable {

    public static boolean recordAudio = false;
    private AudioFormat af = new AudioFormat(
            44100, 16, 1, true, true
    );
    private ByteArrayOutputStream bosAudio;
    private boolean running, capture;

    /**
     * Konstruktor. Tworzymy nowy stream na dane audio.
     */
    public CaptureAudio() {
        bosAudio = new ByteArrayOutputStream();
    }

    /**
     * Metoda zatrzymuje nagrywanie dźwięku.
     */
    public void stop() {
        running = false;
    }

    /**
     * Metoda zwraca nagrany dźwięk.
     *
     * @return tablica byte[] zawierająca dane audio
     */
    public byte[] getData() {
        byte[] erg = bosAudio.toByteArray();
        bosAudio.reset();
        return erg;
    }

    /**
     * Metoda z interfejsu Runnable. Odpowiada za nagrywanie dźwięku.
     */
    @Override
    public void run() {
        DataLine.Info info = new DataLine.Info(
                TargetDataLine.class, af
        );
        TargetDataLine line = null;
        running = true;
        capture = true;
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            byte[] buffer = new byte[400];
            line.open(af);
            line.start();
            while (running) {
                int count = line.read(buffer, 0, buffer.length);
                if (count > 0 && capture) //zeby nie bylo petli
                    bosAudio.write(buffer, 0, count);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            try {
                bosAudio.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line != null) {
                line.flush();
                line.stop();
                line.close();
            }
        }
    }
}
