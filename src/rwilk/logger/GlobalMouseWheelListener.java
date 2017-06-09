package rwilk.logger;

import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by wilkr on 25.04.2017.
 * Scrool myszki
 */
public class GlobalMouseWheelListener implements NativeMouseWheelListener {

    private static final DateFormat dateFormatForPath = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");

    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        /*Calendar calendar = Calendar.getInstance();
            try {
                Date date =  calendar.getTime(); //zapamietujemy czas, zeby byl taki sam w nazwie screenShota
                //robimy screenShot wszystkich ekranow, ktore sa do komputera podlaczone
                Rectangle screenRect = new Rectangle(0, 0, 0, 0);
                for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                    screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
                }
                BufferedImage capture = new Robot().createScreenCapture(screenRect);
                ImageIO.write(capture, "JPG", new File(ImagesSettings.readFile() + "\\" + dateFormatForPath.format(date) + "scroll" + ".jpg"));
            }catch (Exception ex){
                ex.printStackTrace();
            }*/
    }
}
