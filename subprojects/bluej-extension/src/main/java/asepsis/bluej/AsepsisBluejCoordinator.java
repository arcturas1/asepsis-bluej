package asepsis.bluej;

import asepsis.bluej.gui.eventbus.ProjectClosedEvent;
import asepsis.bluej.gui.eventbus.ProjectOpenedEvent;
import bluej.extensions.*;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;
import com.google.common.eventbus.EventBus;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AsepsisBluejCoordinator implements ApplicationListener, PackageListener {
    private Map<Frame, EventBus> eventBusMap = new HashMap<Frame, EventBus>();
    private BlueJ bluej;
    private AsepsisBluejIntegrator integrator;

    public AsepsisBluejCoordinator(BlueJ bluej, AsepsisBluejIntegrator integrator) {
        this.bluej = bluej;
        this.integrator = integrator;
        bluej.addPackageListener(this);
        bluej.addApplicationListener(this);
    }

    @Override
    public void blueJReady(ApplicationEvent applicationEvent) {
        addAsepsisToBluejFrame(bluej.getCurrentFrame());
    }

    @Override
    public void packageOpened(PackageEvent packageEvent) {
        try {
            Frame frame = packageEvent.getPackage().getFrame();
            if (!eventBusMap.containsKey(frame))
                addAsepsisToBluejFrame(frame);

            EventBus eventBus = eventBusMap.get(frame);
            eventBus.post(new ProjectOpenedEvent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void packageClosing(PackageEvent packageEvent) {
        EventBus eventBus = eventBusMap.get(bluej.getCurrentFrame());
        eventBus.post(new ProjectClosedEvent());
    }

    private void addAsepsisToBluejFrame(Frame frame) {
        EventBus eventBus = new EventBus();
        integrator.initAsepsisForBluejFrame(frame, eventBus);
        eventBusMap.put(frame, eventBus);
    }
}
