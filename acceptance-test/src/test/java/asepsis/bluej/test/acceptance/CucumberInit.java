package asepsis.bluej.test.acceptance;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fest.swing.core.*;
import org.fest.swing.core.Robot;
import org.fest.swing.security.NoExitSecurityManagerInstaller;
import org.netbeans.jemmy.operators.JFrameOperator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * General setup for Cucumber.
 */
public class CucumberInit {
    private Robot cleaningRobot;
    private Thread t;
    private static JFrameOperator mainFrame;
    private NoExitSecurityManagerInstaller sec;

    public static JFrameOperator bluejFrame() {
        return mainFrame;
    }

    /**
     * Starts BlueJ in a new thread.
     */
    @Before
    public void before() throws InterruptedException, InvocationTargetException {
        cleaningRobot = BasicRobot.robotWithNewAwtHierarchy();
        sec = NoExitSecurityManagerInstaller.installNoExitSecurityManager();

        t = new Thread(new Runnable() {
            public void run() {
                File file = new File(System.getProperty("bluej-jar"));
                URLClassLoader cl = null;
                try {
                    cl = new URLClassLoader(new URL[]{file.toURI().toURL()}, null);
                } catch (MalformedURLException e) {
                }

                Class<?> clazz = null;
                try {
                    clazz = cl.loadClass("bluej.Boot");
                } catch (ClassNotFoundException e) {
                }

                Method main = null;
                try {
                    main = clazz.getMethod("main", String[].class);
                } catch (NoSuchMethodException e) {
                }

                try {
                    main.invoke(null, new Object[]{new String[]{}});
                } catch (Exception e) {
                }
            }
        });
        t.start();

        mainFrame = new JFrameOperator("BlueJ");
    }

    /**
     * Shuts down BlueJ and kills the starting thread.
     */
    @After
    public void after() throws InterruptedException {
        t.stop();
        t.join();
        cleaningRobot.cleanUp();
        sec.uninstall();
        System.gc(); // Allow JVM to cleanup disposed windows
    }
}
