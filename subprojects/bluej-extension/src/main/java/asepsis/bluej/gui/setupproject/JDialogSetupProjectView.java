package asepsis.bluej.gui.setupproject;

import asepsis.bluej.gui.Labeller;
import asepsis.bluej.gui.event.EventListener;
import com.jgoodies.forms.builder.ButtonBarBuilder;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

import static asepsis.bluej.gui.event.EventUtil.asActionListener;

public class JDialogSetupProjectView extends JDialog implements SetupProjectView {
    private Labeller labeller;
    private JLabel infoLabel;
    private JLabel courseLabel;
    private JComboBox<String> courseComboBox;
    private JButton refreshButton;
    private JLabel assignmentLabel;
    private JComboBox<String> assignmentComboBox;
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
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(newCourses);
        courseComboBox.setModel(model);
        courseComboBox.setSelectedIndex(0);
    }

    @Override
    public void onNewAssignments(String[] newAssignments) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(newAssignments);
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
        initRefreshButton();
        assignmentLabel = makeLabel("asepsis.setup.dialog.assignmentLabel");
        initAssignmentComboBox();
        initOkButton();
        initCancelButton();
    }

    private void initCourseComboBox() {
        courseComboBox = new JComboBox<String>();
        courseComboBox.setName("asepsis.setup.dialog.courseList");
    }

    private void initRefreshButton() {
        URL imagePath = getClass().getResource("/images/reload_icon.png");
        refreshButton = new JButton(new ImageIcon(imagePath));
        refreshButton.setPreferredSize(new Dimension(20,20));
        refreshButton.setFocusPainted(false);
    }

    private void initAssignmentComboBox() {
        assignmentComboBox = new JComboBox<String>();
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
//        FormLayout layout = new FormLayout(
//                "pref,pref,pref",
//                "40dlu, 40dlu, 40dlu"
//        );
//        DefaultFormBuilder builder = new DefaultFormBuilder(layout, new FormDebugPanel());
//        builder.border(Borders.DIALOG);
//
//        builder.setRowSpan(2);
//        builder.append(new JButton("1,1"));
//        builder.setRowSpan(1);
//        builder.append(new JButton("1,2"));
//        builder.nextLine();
//        builder.append(new JButton("2,1"));
//        builder.append(new JButton("2,2"));

        FormLayout layout = new FormLayout(
                "right:max(60dlu;pref), 4dlu, max(60dlu;pref)", // 3 columns
                "" );

        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.border(Borders.DIALOG);

        builder.append(infoLabel, 3);
        builder.nextLine();

        builder.appendRow("4dlu");
        builder.nextLine();

        builder.append(courseLabel); builder.append(courseComboBox);
        builder.nextLine();

        builder.append(assignmentLabel); builder.append(assignmentComboBox);
        builder.nextLine();

        builder.appendRow("10dlu");
        builder.nextLine();

        ButtonBarBuilder buttonBuilder = new ButtonBarBuilder();
        buttonBuilder.addGlue();
        buttonBuilder.addButton(okButton, cancelButton);
        buttonBuilder.addGlue();
        builder.append(buttonBuilder.build(), 3);

//        JPanel inputPanel  = new JPanel(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//
//
//        c.gridx = 0;
//        c.gridy = 0;
//        c.gridwidth = 1;
//        c.anchor = GridBagConstraints.LINE_END;
//        inputPanel.add(courseLabel, c);
//
//        c.gridx = 1;
//        c.gridy = 0;
//        c.gridwidth = 2;
//        c.insets = new Insets(0, 10, 0, 0);
//        c.anchor = GridBagConstraints.LINE_START;
//        inputPanel.add(courseComboBox, c);
//
//        c.gridx = 0;
//        c.gridy = 1;
//        c.gridwidth = 1;
//        c.anchor = GridBagConstraints.LINE_END;
//        inputPanel.add(assignmentLabel, c);
//
//        c.gridx = 1;
//        c.gridy = 1;
//        c.gridwidth = 2;
//        c.anchor = GridBagConstraints.LINE_START;
//        inputPanel.add(assignmentComboBox, c);
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
//        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        JPanel mainPanel = new JPanel();
//        mainPanel.setPreferredSize(new Dimension(100,200));
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        mainPanel.add(infoLabel);
//        mainPanel.add(inputPanel);
//
//        JPanel tP = new JPanel();
//        tP.add(new JButton("Test"));
//        mainPanel.add(tP);

        return builder.build();
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