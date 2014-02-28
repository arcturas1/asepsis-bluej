package asepsis.bluej.gui;


import asepsis.bluej.common.ClassComponentChooser;
import asepsis.bluej.datalayer.AsepsisAdapter;
import asepsis.bluej.datalayer.DefaultCourseRepository;
import asepsis.bluej.datalayer.FakeAsepsisAdapter;
import asepsis.bluej.gui.main.*;
import asepsis.bluej.gui.setupproject.*;
import asepsis.bluej.repository.CourseRepository;
import bluej.extensions.BlueJ;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;
import com.google.common.eventbus.EventBus;

import javax.swing.*;
import java.awt.*;

import static org.netbeans.jemmy.operators.JComponentOperator.findJComponent;

public class ExtensionController {
    private BlueJ bluej;
    private BluejLabeller labeller;



    public ExtensionController(BlueJ bluej) {
        this.bluej = bluej;
        bluej.addApplicationListener(new ApplicationListener() {
            @Override
            public void blueJReady(ApplicationEvent applicationEvent) {
                display();
            }
        });
    }

    private void display() {
        labeller = new BluejLabeller(bluej);

        AsepsisAdapter dal = new FakeAsepsisAdapter();
        CourseRepository courseRepo = new DefaultCourseRepository(dal);
        EventBus eventBus = new EventBus();

        SetupProjectModel setupModel = new DefaultSetupProjectModel(eventBus, courseRepo);
        eventBus.register(setupModel);
        SetupProjectView setupView = new JDialogSetupProjectView(bluej.getCurrentFrame(), labeller);
        new SetupProjectPresenter(setupView, setupModel);

        MainModel mainModel = new DefaultMainModel(eventBus);
        eventBus.register(mainModel);
        MainView mainView = new JPanelMainView(labeller);
        addMainViewToBlueJ((JPanel) mainView);
        new MainPresenter(mainView, mainModel);
    }

    private void addMainViewToBlueJ(JPanel mainView) {
        Container toolPanel = getBluejToolPanel();
        int bottomIndex = toolPanel.getComponents().length - 1;
        toolPanel.add(mainView, bottomIndex);
    }


    private Container getBluejToolPanel() {
        JComponent c = findJComponent(bluej.getCurrentFrame(), new ClassComponentChooser("bluej.pkgmgr.MachineIcon"));
        return c.getParent();
    }
}
