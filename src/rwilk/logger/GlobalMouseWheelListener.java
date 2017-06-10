package rwilk.logger;

import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import rwilk.view.ImageSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Rafal Wilk
 */
public class GlobalMouseWheelListener implements NativeMouseWheelListener {

    private static final DateFormat dateFormatForPath = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");

    /**
     * Metoda wywoływana w momencie przesuwania kółkiem od myszki.
     * Robiony jest screenshot wszystkich ekranów.
     *
     * @param e zdarzenie od myszki
     */
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        Calendar calendar = Calendar.getInstance();
            try {
                Date date =  calendar.getTime();
                Rectangle screenRect = new Rectangle(0, 0, 0, 0);
                for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                    screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
                }
                BufferedImage capture = new Robot().createScreenCapture(screenRect);
                ImageIO.write(capture, "JPG", new File(ImageSettings.readFile() + "\\" + dateFormatForPath.format(date) + "scroll" + ".jpg"));
            }catch (Exception ex){
                ex.printStackTrace();
            }
    }
}
