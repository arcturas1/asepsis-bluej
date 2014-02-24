package asepsis.bluej;

import asepsis.bluej.common.ClassComponentChooser;
import bluej.extensions.BlueJ;
import bluej.extensions.event.ApplicationEvent;
import bluej.extensions.event.ApplicationListener;

import javax.swing.*;
import java.awt.*;

import static org.netbeans.jemmy.operators.ComponentOperator.findComponent;

public class GuiBuilder implements ApplicationListener {
    private BlueJ bluej;
    private Frame bluejFrame;

    public GuiBuilder(BlueJ bluej) {
        this.bluej = bluej;
    }

    @Override
    public void blueJReady(ApplicationEvent applicationEvent) {
        buildAndIntegrateGui();
    }

    public void buildAndIntegrateGui() {
        bluejFrame = bluej.getCurrentFrame();
        addSidebarToBluej();
        bluejFrame.pack();
    }

    private void addSidebarToBluej() {
        Container toolPanel = getBluejToolPanel();
        int bottomIndex = toolPanel.getComponents().length - 1;
        toolPanel.add(sidebar(), bottomIndex);
    }

    private Component sidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setOpaque(false);
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
        Component c = findComponent(bluejFrame, new ClassComponentChooser("bluej.pkgmgr.MachineIcon"));
        return c.getParent();
    }
}
