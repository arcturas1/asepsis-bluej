package asepsis.bluej.test.acceptance;

import asepsis.bluej.common.Util;
import asepsis.bluej.test.acceptance.support.BluejExtensionEdtOfficer;
import com.google.common.io.Files;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.security.NoExitSecurityManagerInstaller;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.drivers.InputDriverInstaller;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static java.lang.ClassLoader.getSystemClassLoader;

/**
 * General setup for Cucumber and Jemmy.
 */
public class Init {
    private Thread starterThread;
    private NoExitSecurityManagerInstaller sec;
    private static JFrameOperator mainFrame;
    private static File tempDir1;
    private static File tempDir2;

    public static void setBluejFrame(String newWindowTitle) {
        mainFrame = new JFrameOperator(newWindowTitle);
    }

    public static JFrameOperator bluejFrame() {
        return mainFrame;
    }

    public static String getTempDir1() {
        return tempDir1.getAbsolutePath();
    }

    public static String getTempDir2() {
        return tempDir2.getAbsolutePath();
    }

    /**
     * Starts BlueJ in a new thread.
     */
    @Before
    public void before() {
        createTempDirs();
        configureJemmy();
        sec = NoExitSecurityManagerInstaller.installNoExitSecurityManager();
        BluejExtensionEdtOfficer.install();
        starterThread = startBluej();
        mainFrame = new JFrameOperator("BlueJ");
    }

    /**
     * Shuts down BlueJ and kills the starting thread.
     */
    @After
    public void after(){
        starterThread.stop();
        quitBluej();
        sec.uninstall();
        System.gc();
        deleteTempDirs();
    }

    private void createTempDirs() {
        tempDir1 = Files.createTempDir();
        tempDir2 = Files.createTempDir();
    }

    private void deleteTempDirs() {
        Util.deleteDirectory(tempDir1);
        Util.deleteDirectory(tempDir2);
    }

    private void configureJemmy() {
        if (System.getProperty("slowSwing") != null) {
            InputDriverInstaller i = new InputDriverInstaller(false, true);
            i.install();
            JemmyProperties.setCurrentTimeout("Waiter.TimeDelta", 1000);
        }

        // Most of these are lowered considerably compared to default values.
        // ~60 seconds is just too long to wait before test fail.
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", 5000);
        JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 5000);
    }

    private static Thread startBluej() {
        Thread starterThread = new Thread(new Runnable() {
            public void run() {
                File file = new File(System.getProperty("bluej-jar"));
                try {
                    URLClassLoader cl = new URLClassLoader(new URL[]{file.toURI().toURL()});
                    Class<?> clazz = cl.loadClass("bluej.Boot");
                    Method main = clazz.getMethod("main", String[].class);
                    main.invoke(null, new Object[]{new String[]{}});
                } catch (Exception e) {
                }
            }
        });
        starterThread.start();
        return starterThread;
    }

    private static void quitBluej() {
        for (Window w : Window.getWindows()) {
            if (!w.getClass().getSimpleName().equals("PkgMgrFrame"))
                w.dispose();
        }

        new JMenuBarOperator(mainFrame).pushMenuNoBlock("Project|Quit");
        JemmyProperties.setCurrentTimeout("DialogWaiter.WaitDialogTimeout", 1000);
        try {
            JDialogOperator dlg = new JDialogOperator("BlueJ:  Question");
            new JButtonOperator(dlg, "Quit All").push();
        } catch (TimeoutExpiredException ignored) {}
        finally {
            JemmyProperties.setCurrentTimeout("DialogWaiter.WaitDialogTimeout", 5000);
        }
    }
}
