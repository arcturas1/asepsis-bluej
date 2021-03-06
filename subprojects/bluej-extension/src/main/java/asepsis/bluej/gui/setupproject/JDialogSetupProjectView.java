package asepsis.bluej.gui.setupproject;

import asepsis.bluej.Labeller;
import asepsis.bluej.gui.event.EventListener;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static asepsis.bluej.gui.event.EventUtil.asActionListener;

public class JDialogSetupProjectView extends JDialog implements SetupProjectView {
    private Labeller labeller;
    private JLabel infoLabel;
    private JLabel courseLabel;
    private JComboBox courseComboBox;
    private JLabel assignmentLabel;
    private JComboBox assignmentComboBox;
    private JButton okButton, cancelButton;

    public JDialogSetupProjectView(Frame owner, Labeller labeller) {
        super(owner, labeller.getLabel("asepsis.setup.dialog.title"), true);
        this.labeller = labeller;
        initComponents();
        getContentPane().add(buildContent());
    }

    @Override
    public void whenCourseSelectionChanges(final EventListener listener) {
        courseComboBox.addActionListener(asActionListener(listener));
        registerAsWindowClosingListener(listener);
    }

    @Override
    public void onNewCourses(String[] newCourses) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(newCourses);
        courseComboBox.setModel(model);
        courseComboBox.setSelectedIndex(0);
    }

    @Override
    public void onNewAssignments(String[] newAssignments) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(newAssignments);
        assignmentComboBox.setModel(model);
    }

    @Override
    public String getSelectedCourse() {
        return courseComboBox.getSelectedItem().toString();
    }

    @Override
    public String getSelectedAssignment() {
        return assignmentComboBox.getSelectedItem().toString();
    }

    @Override
    public void whenDialogIsAccepted(EventListener listener) {
        okButton.addActionListener( asActionListener(listener) );
    }

    @Override
    public void whenDialogIsCanceled(EventListener listener) {
        cancelButton.addActionListener( asActionListener(listener) );
    }

    @Override
    public void showDialog() {
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
        courseLabel.requestFocusInWindow(); // Avoids initial focus on course combobox
        setVisible(true);
    }

    @Override
    public void closeDialog() {
        setVisible(false);
    }

    private void initComponents() {
        infoLabel = makeLabel("asepsis.setup.dialog.info");
        courseLabel = makeLabel("asepsis.setup.dialog.courseLabel");
        initCourseComboBox();
        assignmentLabel = makeLabel("asepsis.setup.dialog.assignmentLabel");
        initAssignmentComboBox();
        initOkButton();
        initCancelButton();
    }

    private void initCourseComboBox() {
        courseComboBox = new JComboBox();
        courseComboBox.setName("asepsis.setup.dialog.courseList");
    }

    private void initAssignmentComboBox() {
        assignmentComboBox = new JComboBox();
        assignmentComboBox.setName("asepsis.setup.dialog.assignmentList");
    }

    private void initOkButton() {
        okButton = new JButton(labeller.getLabel("asepsis.general.ok"));
        okButton.setName("asepsis.setup.dialog.okBtn");
    }

    private void initCancelButton() {
        cancelButton = new JButton(labeller.getLabel("asepsis.general.cancel"));
        cancelButton.setName("asepsis.setup.dialog.cancelBtn");
    }

    private JLabel makeLabel(String name) {
        JLabel c = new JLabel(makeBreakableLabelText(name));
        c.setName(name);
        return c;
    }

    private String makeBreakableLabelText(String name) {
        return "<html><p>" + labeller.getLabel(name) + "</p></html>";
    }

    private JComponent buildContent() {
        FormLayout layout = new FormLayout(
                "right:max(60dlu;pref), 4dlu, max(60dlu;pref)", // 3 columns
                "" );

        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.border(Borders.DIALOG);

        builder.append(infoLabel, 3);
        builder.nextLine();

        builder.appendRow("4dlu");
        builder.nextLine();

        builder.append(courseLabel, courseComboBox);
        builder.nextLine();

        builder.append(assignmentLabel,assignmentComboBox);
        builder.nextLine();

        builder.appendRow("10dlu");
        builder.nextLine();

        ButtonBarBuilder buttonBuilder = new ButtonBarBuilder();
        buttonBuilder.addGlue();
        buttonBuilder.addButton(okButton, cancelButton);
        buttonBuilder.addGlue();
        builder.append(buttonBuilder.build(), 3);
        return builder.build();

//        JPanel inputPanel  = new JPanel(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
//        c.insets = new Insets(0,0,10,0);
//        infoLabel.setPreferredSize(new Dimension(240,32));
//        inputPanel.add(infoLabel, c);
//
//        c.gridx = 0; c.gridy = 1; c.gridwidth = 1;
//        c.insets = new Insets(0,20,0,0);
//        c.anchor = GridBagConstraints.LINE_END;
//        inputPanel.add(courseLabel, c);
//
//        c.gridx = 1; c.gridy = 1; c.gridwidth = 2;
//        c.insets = new Insets(0,6,0,0);
//        c.anchor = GridBagConstraints.LINE_START;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        inputPanel.add(courseComboBox, c);
//        c.fill = GridBagConstraints.NONE;
//
//        c.gridx = 0; c.gridy = 2; c.gridwidth = 1;
//        c.insets = new Insets(0,20,0,0);
//        c.anchor = GridBagConstraints.LINE_END;
//        inputPanel.add(assignmentLabel, c);
//
//        c.gridx = 1; c.gridy = 2; c.gridwidth = 2;
//        c.insets = new Insets(0,6,0,0);
//        c.anchor = GridBagConstraints.LINE_START;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        inputPanel.add(assignmentComboBox, c);
//        c.fill = GridBagConstraints.NONE;
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(okButton);
//        buttonPanel.add(cancelButton);
//        c.gridx = 0; c.gridy = 3; c.gridwidth = GridBagConstraints.REMAINDER;
//        c.insets = new Insets(10,0,0,0);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        inputPanel.add(buttonPanel, c);
//
//        return inputPanel;
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.add(okButton);
//        buttonPanel.add(cancelButton);
//
//        c.gridx = 0;
//        c.gridy = 2;
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridwidth = GridBagConstraints.REMAINDER;
//        inputPanel.add(buttonPanel, c);
//
//       infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//       JPanel mainPanel = new JPanel();
//       mainPanel.setPreferredSize(new Dimension(100,200));
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        mainPanel.add(infoLabel);
//        mainPanel.add(inputPanel);
//
//        JPanel tP = new JPanel();
//        tP.add(new JButton("Test"));
//        mainPanel.add(tP);

//        return builder.build();
    }

    private void registerAsWindowClosingListener(final EventListener listener) {
        addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent e) {}
            public void windowClosing(WindowEvent e) {
                listener.onEvent();
            }
            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });
    }
}