package asepsis.bluej.test.acceptance.support;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;

import javax.swing.*;
import java.io.*;

import static org.fest.reflect.core.Reflection.staticMethod;

public class NonBluejEdtOfficer extends FailOnThreadViolationRepaintManager {
    public static FailOnThreadViolationRepaintManager install() {
        Object m = currentRepaintManager();
        if (m instanceof NonBluejEdtOfficer) return (NonBluejEdtOfficer)m;
        return installNew();
    }

    @Override
    public void addDirtyRegion(JComponent component, int x, int y, int w, int h) {
        if (isFromBluej(component))
            return;
        super.addDirtyRegion(component, x, y, w, h);
    }

    @Override
    public synchronized void addInvalidComponent(JComponent component) {
        if (isFromBluej(component))
            return;
        super.addInvalidComponent(component);
    }

    private static Object currentRepaintManager() {
        try {
            return staticMethod("appContextGet").withReturnType(Object.class)
                    .withParameterTypes(Object.class)
                    .in(SwingUtilities.class)
                    .invoke(RepaintManager.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private static FailOnThreadViolationRepaintManager installNew() {
        NonBluejEdtOfficer m = new NonBluejEdtOfficer();
        setCurrentManager(m);
        return m;
    }


    private boolean isFromBluej(JComponent c) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            if (i <= 2)
                continue;
            if (stackTrace[i].getClassName().startsWith("bluej"))
                return true;
            if (stackTrace[i].getClassName().startsWith("asepsis.bluej"))
                return false;
        }
        return false;
    }

    private void logToFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("out.txt", true));
            pw.write("===================================\n");
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement st : stackTrace)
                pw.write(st.getClassName() + ":" + st.getMethodName() + "\n");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
