package asepsis.bluej.gui.setupproject;

import asepsis.bluej.gui.event.EventListener;

public class SetupProjectPresenter {
    private final SetupProjectView view;
    private final SetupProjectModel model;

    public SetupProjectPresenter(SetupProjectView view, SetupProjectModel model) {
        this.view = view;
        this.model = model;
        initPresentationLogic();
    }

    private void initPresentationLogic() {
        view.whenCourseSelectionChanges(new View_OnCourseSelectionChange());
        view.whenDialogIsAccepted(new View_OnDialogAccepted());
        view.whenDialogIsCanceled(new View_OnDialogCanceled());
        model.whenShowDialogRequested(new Model_OnShowDialogRequest());
        model.whenCloseDialogRequested(new Model_OnCloseDialogRequest());
    }

    private class View_OnCourseSelectionChange implements EventListener {
        public void onEvent() {
            String[] assignments = model.getAssignmentNamesFor(view.getSelectedCourse());
            view.onNewAssignments(assignments);
        }
    }

    private class View_OnDialogAccepted implements EventListener {
        public void onEvent() {
            String course = view.getSelectedCourse();
            String assignment = view.getSelectedAssignment();
            model.acceptDialog(course, assignment);
        }
    }

    private class View_OnDialogCanceled implements EventListener {
        public void onEvent() { model.cancelDialog(); }
    }

    private class Model_OnShowDialogRequest implements EventListener {
        public void onEvent() {
            view.onNewCourses(model.getCourseNames());
            view.showDialog();
        }
    }

    private class Model_OnCloseDialogRequest implements EventListener {
        public void onEvent() { view.closeDialog(); }
    }
}