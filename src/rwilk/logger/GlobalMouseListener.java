package rwilk.logger;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by wilkr on 25.04.2017.
 * Klasa powinna implementować myszke
 */
public class GlobalMouseListener implements NativeMouseInputListener {

    //Z myszki to przechwytuje tylko klikniecie. I robie screenshot. Nie zapisuje tego nigdzie.

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
    private static final DateFormat dateFormatForPath = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSS");

    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    //robi screenshoty przy kliknieciu
    public void nativeMousePressed(NativeMouseEvent e) {
        /*try {
            Calendar calendar = Calendar.getInstance();
            Date date =  calendar.getTime(); //zapamietujemy czas, zeby byl taki sam w nazwie screenShota

            //robimy screenShot wszystkich ekranow, ktore sa do komputera podlaczone
            Rectangle screenRect = new Rectangle(0, 0, 0, 0);
            for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()) {
                screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
            }
            BufferedImage capture = new Robot().createScreenCapture(screenRect);

            Image cursor = null; //rysujemy obraz myszki
            try {
                //cursor = ImageIO.read(new File("pointer.png"));
                cursor = ImageIO.read(GlobalMouseListener.class.getResourceAsStream("pointer.png"));

                //URI
               // DocFlavor.URL url = new DocFlavor.URL("../resources/pointer.png");

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            int x = MouseInfo.getPointerInfo().getLocation().x; //pozycja myszki
            int y = MouseInfo.getPointerInfo().getLocation().y;
            Graphics2D g = capture.createGraphics();  //wstawiamy obrazek myszki
            if(cursor != null)g.drawImage(cursor,x,y,16,16,null);
            //zapisujemy
            ImageIO.write(capture, "JPG", new File(ImagesSettings.readFile() + "\\" + dateFormatForPath.format(date) + ".jpg"));
        }catch (Exception ex){
            ex.printStackTrace();
        }*/
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
    }

    //ruch myszki
    public void nativeMouseMoved(NativeMouseEvent e) {
    }

    //przeciaganie
    public void nativeMouseDragged(NativeMouseEvent e) {
    }
}
