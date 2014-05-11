package asepsis.bluej;

import asepsis.bluej.Labeller;
import asepsis.bluej.common.ClassComponentChooser;
import asepsis.bluej.data.AsepsisAdapter;
import asepsis.bluej.data.DefaultCourseRepository;
import asepsis.bluej.data.FakeAsepsisAdapter;
import asepsis.bluej.gui.main.*;
import asepsis.bluej.gui.setupproject.*;
import asepsis.bluej.domain.CourseRepository;
import com.google.common.eventbus.EventBus;
import org.netbeans.jemmy.util.NameComponentChooser;

import javax.swing.*;
import java.awt.*;

import static org.netbeans.jemmy.operators.JComponentOperator.findJComponent;

public class AsepsisBluejIntegrator {
    private Labeller labeller;

    public AsepsisBluejIntegrator(Labeller labeller) {
        this.labeller = labeller;
    }

    public void initAsepsisForBluejFrame(Frame bluejFrame, EventBus eventBus) {
        AsepsisAdapter dal = new FakeAsepsisAdapter();
        CourseRepository courseRepo = new DefaultCourseRepository(dal);

        SetupProjectModel setupModel = new DefaultSetupProjectModel(eventBus, courseRepo);
        eventBus.register(setupModel);
        SetupProjectView setupView = new JDialogSetupProjectView(bluejFrame, labeller);
        new SetupProjectPresenter(setupView, setupModel);

        MainModel mainModel = new DefaultMainModel(eventBus);
        eventBus.register(mainModel);
        MainView mainView = new JPanelMainView(labeller);
        addMainViewToBlueJ((JPanel) mainView, bluejFrame);
        new MainPresenter(mainView, mainModel);
    }

    public void removeAsepsisFromBluejFrame(Frame bluejFrame) {
        JComponent c = findJComponent(bluejFrame, new NameComponentChooser("asepsis.mainview"));
        c.getParent().remove(c);
    }

    private void addMainViewToBlueJ(JPanel mainView, Frame projectFrame) {
        Container toolPanel = getBluejToolPanel(projectFrame);
        int bottomIndex = toolPanel.getComponents().length - 1;
        toolPanel.add(mainView, bottomIndex);
    }

    private Container getBluejToolPanel(Frame projectFrame) {
        JComponent c = findJComponent(projectFrame, new ClassComponentChooser("bluej.pkgmgr.MachineIcon"));
        return c.getParent();
    }
}