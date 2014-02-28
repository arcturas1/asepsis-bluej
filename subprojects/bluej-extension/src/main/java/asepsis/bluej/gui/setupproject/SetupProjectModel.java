package asepsis.bluej.gui.setupproject;

import asepsis.bluej.gui.event.EventListener;

public interface SetupProjectModel {
    String[] getCourseNames();
    String[] getAssignmentNamesFor(String course);

    void acceptDialog(String course, String assignment);
    void cancelDialog();

    void whenShowDialogRequested(EventListener listener);
    void whenCloseDialogRequested(EventListener listener);
}
