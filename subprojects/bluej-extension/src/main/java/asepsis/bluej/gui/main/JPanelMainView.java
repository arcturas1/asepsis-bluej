package asepsis.bluej.gui.main;

import asepsis.bluej.gui.Labeller;
import asepsis.bluej.gui.event.EventListener;

import javax.swing.*;
import java.awt.*;

import static asepsis.bluej.gui.event.EventUtil.asActionListener;

public class JPanelMainView extends JPanel implements MainView {
    private Labeller labeller;
    private JLabel statusLabel;
    private JButton setupButton;

    public JPanelMainView(Labeller labeller) {
        this.labeller = labeller;
        initComponents();
        buildContent();
    }

    @Override
    public void whenProjectSetupRequested(EventListener e) {
        setupButton.addActionListener( asActionListener(e) );
    }

    @Override
    public void onSetupChange(boolean isSetup) {
        String txt = isSetup ? labeller.getLabel("asepsis.sidebar.status.setup") : labeller.getLabel("asepsis.sidebar.status.notSetup");
        statusLabel.setText(txt);
    }

    private void initComponents() {
        initSelf();
        initStatusLabel();
        initSetupButton();
    }

    private void initSelf() {
        setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("ASEPSiS"));
    }

    private void initStatusLabel() {
        statusLabel = new JLabel(labeller.getLabel("asepsis.sidebar.status.notSetup"));
        statusLabel.setName("asepsis.sidebar.status");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void initSetupButton() {
        setupButton = new JButton(labeller.getLabel("asepsis.sidebar.setupBtn"));
        setupButton.setName("asepsis.sidebar.setupBtn");
        setupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setupButton.setFocusable(false); // Mimic normal BlueJ buttons
    }

    private void buildContent() {
        add(statusLabel);
        add(setupButton);
    }
}
