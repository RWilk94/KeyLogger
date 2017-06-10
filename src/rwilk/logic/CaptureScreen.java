package rwilk.logic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import rwilk.logger.GlobalMouseListener;
import rwilk.view.Main;


/**
 * Created by Rafał Wilk
 */
public class CaptureScreen implements ActionListener {

    public boolean running = false;

    private Robot robot;
    private Rectangle capBounds;
    private IMediaWriter writer;
    private static ICodec.ID codec = ICodec.ID.CODEC_ID_H264;
    private String videoName;
    private int delayBetweenFrames = 35;
    private Timer timer = new Timer(delayBetweenFrames, this);
    public static Integer durationInMinutes = 30;
    private Timer cycleOfTime = new Timer((int) Duration.ofMinutes(durationInMinutes).getSeconds() * 100, this);
    private long start = 0;
    private CaptureAudio audio = new CaptureAudio();
    private long audioLastFrame;
    private long frameCount;

    /**
     * Konstruktor, w którym ustawiamy parametry nowego filmu.
     *
     * @param videoName nazwa filmu
     * @param width     szerokosc nagrywanego obrazu
     * @param height    wysokosc nagrywanego obrazu
     */
    public CaptureScreen(String videoName, int width, int height) {
        this.videoName = videoName;
        capBounds = new Rectangle(width, height);
    }

    /**
     * Metoda rozpoczyna nagrywanie. Nagrywanie może być z dźwiekem lub bez.
     *
     * @throws AWTException wyjątek, który może wystąpić w przypadku obiektu klasy Robot
     */
    public void startCapture() throws AWTException {
        robot = new Robot();
        writer = ToolFactory.makeWriter(videoName);
        writer.addVideoStream(0, 0, codec, capBounds.width, capBounds.height);
        if (CaptureAudio.recordAudio) {
            writer.addAudioStream(1, 0, 1, 44100);
            audio = new CaptureAudio();
            audioLastFrame = 0;
            Thread t = new Thread(audio);
            t.start();
        }
        start = System.currentTimeMillis();
        frameCount = 0;
        timer.start();
        cycleOfTime.start();
        running = true;
    }

    /**
     * Metoda zatrzymuje nagrywanie obrazu.
     */
    public void stopCapture() {
        running = false;
        if (CaptureAudio.recordAudio) {
            audio.stop();
            addAudioData();
        }
        timer.stop();
        cycleOfTime.stop();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writer.close();
        start = 0;
    }

    /**
     * Słuchacz zdarzeń.
     * @param e zdarzenie od słuchacza zdarzeń.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            BufferedImage screen = getCaptureImage();
            writer.encodeVideo(0, screen, System.currentTimeMillis() - start,
                    TimeUnit.MILLISECONDS
            );
            if (CaptureAudio.recordAudio)
                addAudioData();
        }
        if (e.getSource() == cycleOfTime) {
            Main.newVideo = true;
        }
    }

    /**
     * Metoda dodaje strumień audio do pliku wyjściowego.
     */
    private void addAudioData() {
        byte[] audioData = audio.getData();
        if (audioData.length > 0) {
            writer.encodeAudio(1, audioByteToShort(audioData),
                    audioLastFrame * delayBetweenFrames, TimeUnit.MILLISECONDS
            );
            audioLastFrame = frameCount;
        }
    }

    /**
     * Metoda zamienia tablice typu byte na tablicę typu short.
     * @param b tablica typu byte
     * @return tablica typu short
     */
    private short[] audioByteToShort(byte[] b) {
        short[] saudio = new short[b.length / 2];
        for (int i = 0; i < saudio.length; i++)
            saudio[i] = (short) (((b[2 * i] << 8) & 0xFF00) | (b[2 * i + 1] & 0xFF));
        return saudio;
    }

    /**
     * Metoda robi screenshot ekranu i dodaje go jako kolejną klatkę do filmu.
     * Metoda dodaje ikonkę kursora myszki.
     * @return zdjęcie (screenshot) ekranu
     */
    private BufferedImage getCaptureImage() {
        BufferedImage screen = robot.createScreenCapture(capBounds);
        BufferedImage image = new BufferedImage(
                screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_3BYTE_BGR
        );
        Image cursor = null;
        try {
            cursor = ImageIO.read(GlobalMouseListener.class.getResourceAsStream("pointer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        Graphics2D g = image.createGraphics();
        g.drawImage(screen, 0, 0, null);
        screen = image;
        if (cursor != null) g.drawImage(cursor, x, y, 16, 16, null);
        g.dispose();
        return screen;
    }
}
