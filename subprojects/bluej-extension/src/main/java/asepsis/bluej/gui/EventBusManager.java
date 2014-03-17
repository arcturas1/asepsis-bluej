package asepsis.bluej.gui;

import asepsis.bluej.gui.eventbus.ProjectClosedEvent;
import asepsis.bluej.gui.eventbus.ProjectOpenedEvent;
import bluej.extensions.BPackage;
import bluej.extensions.BlueJ;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;
import com.google.common.eventbus.EventBus;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Unfortunately BlueJ has a bug which makes it impossible to access the BlueJ frame of the package
 * in the packageClosing event. Therefore we need to keep track of both the frame and the package
 * itself so we can use the package to identify the frame when its closing.
 */
public class EventBusManager implements ApplicationListener, PackageListener {
    private Map<Frame, EventBus> frameBusMap = new HashMap<Frame, EventBus>();
    private Map<BPackage, EventBus> pkgMap = new HashMap<BPackage, EventBus>();
    private BlueJ bluej;
    private AsepsisBluejIntegrator integrator;

    public EventBusManager(BlueJ bluej, AsepsisBluejIntegrator integrator) {
        this.bluej = bluej;
        this.integrator = integrator;
        bluej.addApplicationListener(this);
        bluej.addPackageListener(this);
    }

    @Override
    public void blueJReady(ApplicationEvent applicationEvent) {
        addNewEventBusFor(bluej.getCurrentFrame());
    }

    @Override
    public void packageOpened(PackageEvent packageEvent) {
        BPackage curPkg = null;
        Frame curFrame = null;
        try {
            curPkg = packageEvent.getPackage();
            curFrame = curPkg.getFrame();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (!frameBusMap.containsKey(curFrame))
            addNewEventBusFor(curFrame);

        EventBus eventBus = frameBusMap.get(curFrame);
        pkgMap.put(curPkg, eventBus);

        eventBus.post(new ProjectOpenedEvent());
        /*
        try {
            Editor obj = curPkg.getClasses()[0].getEditor();
            Field f = obj.getClass().getDeclaredField("bjEditor");
            f.setAccessible(true);
            MoeEditor e = (MoeEditor)f.get(obj);
            e.setVisible(true);
            Thread.sleep(500);
            JComponent c = findJComponent(e, new ClassComponentChooser("bluej.editor.moe.NaviView"));
            Container panel = c.getParent();

            JPanel newPanel = new JPanel();
            newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
            newPanel.add(new JButton("This"));
            newPanel.add(new JButton("Is"));
            newPanel.add(new JButton("A"));
            newPanel.add(new JButton("Test"));

            panel.add(newPanel, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void packageClosing(PackageEvent packageEvent) {
        BPackage curPkg = packageEvent.getPackage();
        EventBus eventBus = pkgMap.get(curPkg);
        eventBus.post(new ProjectClosedEvent());
        pkgMap.remove(curPkg);
    }

    private void addNewEventBusFor(Frame frame) {
        EventBus eventBus = new EventBus();
        integrator.initAsepsisForBluejFrame(frame, eventBus);
        frameBusMap.put(frame, eventBus);
    }
}
