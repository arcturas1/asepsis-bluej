package asepsis.bluej.gui.setupproject;

import asepsis.bluej.gui.event.EventListener;

public interface SetupProjectView {
    void whenCourseSelectionChanges(EventListener listener);

    void onNewCourses(String[] newCourses);
    void onNewAssignments(String[] newAssignments);

    String getSelectedCourse();
    String getSelectedAssignment();

    void whenDialogIsAccepted(EventListener listener);
    void whenDialogIsCanceled(EventListener listener);
    void showDialog();
    void closeDialog();
}
