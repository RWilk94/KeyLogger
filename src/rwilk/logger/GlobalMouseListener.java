package rwilk.logger;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import rwilk.view.ImageSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rafal Wilk
 */
public class GlobalMouseListener implements NativeMouseInputListener {

    private static final DateFormat dateFormatForPath = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");

    /**
     * Metoda wywoływana w momencie kliknięcia myszką.
     *
     * @param e zdarzenie od myszki
     */
    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    /**
     * Metoda wywołuje się w momencie naciśnięcia przycisków myszki.
     *
     * @param e zdarzenie od myszki
     */
    public void nativeMousePressed(NativeMouseEvent e) {
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime(); //zapamietujemy czas, zeby byl taki sam w nazwie screenShota

            //robimy screenShot wszystkich ekranow, ktore sa do komputera podlaczone
            Rectangle screenRect = new Rectangle(0, 0, 0, 0);
            for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
            }
            BufferedImage capture = new Robot().createScreenCapture(screenRect);

            Image cursor = null; //rysujemy obraz myszki
            try {
                cursor = ImageIO.read(GlobalMouseListener.class.getResourceAsStream("pointer.png"));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;
            Graphics2D g = capture.createGraphics();
            if (cursor != null) g.drawImage(cursor, x, y, 16, 16, null);
            ImageIO.write(capture, "JPG", new File(ImageSettings.readFile() + "\\" + dateFormatForPath.format(date) + ".jpg"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Metoda wywoływana w momencie puszczenia przycisków myszki.
     *
     * @param e zdarzenie od myszki
     */
    public void nativeMouseReleased(NativeMouseEvent e) {
    }

    /**
     * Metoda wywoływana w momencie ruchu myszką.
     *
     * @param e zdarzenie od myszki
     */
    public void nativeMouseMoved(NativeMouseEvent e) {
    }

    /**
     * Metoda wywoływana w momencie przeciągania myszką.
     *
     * @param e zdarzenie od myszki
     */
    public void nativeMouseDragged(NativeMouseEvent e) {
    }
}
