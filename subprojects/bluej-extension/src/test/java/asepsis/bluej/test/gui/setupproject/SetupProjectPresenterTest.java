package asepsis.bluej.test.gui.setupproject;

import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.setupproject.SetupProjectModel;
import asepsis.bluej.gui.setupproject.SetupProjectPresenter;
import asepsis.bluej.gui.setupproject.SetupProjectView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SetupProjectPresenterTest {
    private SetupProjectView view;
    private SetupProjectModel model;

    @Before
    public void setUp() throws Exception {
        view = mock(SetupProjectView.class);

        model = mock(SetupProjectModel.class);
        when(model.getCourseNames()).thenReturn(dummyCourses());

        new SetupProjectPresenter(view, model);
    }

    @Test
    public void UpdatesViewWithCoursesFromModel_WhenModelRequestsDialogShow() {
        // Capture presenters model listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(model).whenShowDialogRequested(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();

        verify(view).onNewCourses(dummyCourses());
    }

    @Test
    public void UpdatesViewAssignmentsFromModel_WhenViewSelectionChanges() {
        // Capture presenters view listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(view).whenCourseSelectionChanges(captor.capture());
        EventListener listener = captor.getValue();

        when(view.getSelectedCourse()).thenReturn("SomeCourse");
        when(model.getAssignmentNamesFor("SomeCourse")).thenReturn(dummyAssignments());
        listener.onEvent();

        verify(view).onNewAssignments(dummyAssignments());
    }

    @Test
    public void UpdatesModelWithSelectedCourseAndAssignment_WhenDialogIsAcceptedInView() {
        // Capture presenters view listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(view).whenDialogIsAccepted(captor.capture());
        EventListener listener = captor.getValue();

        when(view.getSelectedCourse()).thenReturn("SomeCourse");
        when(view.getSelectedAssignment()).thenReturn("SomeAssignment");
        listener.onEvent();

        verify(model).acceptDialog("SomeCourse", "SomeAssignment");
    }

    @Test
    public void NotifiesModel_WhenDialogIsCanceledInView() {
        // Capture presenters view listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(view).whenDialogIsCanceled(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();

        verify(model).cancelDialog();
    }

    @Test
    public void NotifiesView_WhenModelRequestsDialogShow() {
        // Capture presenters model listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(model).whenShowDialogRequested(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();

        verify(view).showDialog();
    }

    @Test
    public void NotifiesView_WhenModelRequestsDialogClose() {
        // Capture presenters model listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(model).whenCloseDialogRequested(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();

        verify(view).closeDialog();
    }

    private String[] dummyCourses() {
        return new String[] {"C1", "C2"};
    }

    private String[] dummyAssignments() {
        return new String[] {"A1", "A2"};
    }
}
