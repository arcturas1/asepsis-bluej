package asepsis.bluej.test.acceptance;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class MainClass {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Hello from main!");
            }
        });

        Thread.sleep(5000);
        System.exit(0);
    }
}
