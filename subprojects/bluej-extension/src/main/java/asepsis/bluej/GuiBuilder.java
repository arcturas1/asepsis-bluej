package asepsis.bluej;

import asepsis.bluej.common.ClassComponentChooser;
import bluej.extensions.BlueJ;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;
import org.netbeans.jemmy.operators.FrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;

import javax.swing.*;
import java.awt.*;

public class GuiBuilder implements ApplicationListener {
    private BlueJ bluej;
    private FrameOperator bluejOperator;

    public GuiBuilder(BlueJ bluej) {
        this.bluej = bluej;
    }

    @Override
    public void blueJReady(ApplicationEvent applicationEvent) {
        buildAndIntegrateGui();
    }

    public void buildAndIntegrateGui() {
        bluejOperator = new FrameOperator(bluej.getCurrentFrame());
        addSidebarToBluej();
        bluejOperator.pack();
    }

    private void addSidebarToBluej() {
        Container toolPanel = getBluejToolPanel();
        int bottomIndex = toolPanel.getComponents().length - 1;
        toolPanel.add(sidebar(), bottomIndex);
    }

    private Component sidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBorder( BorderFactory.createTitledBorder("ASEPSiS") );

        sidebar.add(statusLabel());

        return sidebar;
    }

    private Component statusLabel() {
        JLabel lbl = new JLabel(bluej.getLabel("asepsis.sidebar.status.notSetup"));
        lbl.setName("asepsis.sidebar.status");
        return lbl;
    }

    private Container getBluejToolPanel() {
        JLabelOperator op = new JLabelOperator(bluejOperator, new ClassComponentChooser("bluej.pkgmgr.MachineIcon"));
        return op.getParent();
    }
}
