package asepsis.bluej.test.gui.setupproject;

import asepsis.bluej.domain.Assignment;
import asepsis.bluej.domain.Course;
import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.eventbus.SetupProjectCanceledEvent;
import asepsis.bluej.gui.eventbus.SetupProjectCompletedEvent;
import asepsis.bluej.gui.eventbus.SetupProjectRequestEvent;
import asepsis.bluej.gui.setupproject.DefaultSetupProjectModel;
import asepsis.bluej.domain.CourseRepository;
import asepsis.bluej.test.util.EventBusCaptor;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

public class DefaultSetupProjectModelTest {
    private DefaultSetupProjectModel model;
    private EventBus eventBus;
    private CourseRepository repo;

    @Before
    public void setUp() throws Exception {
        eventBus = new EventBus();
        repo = mock(CourseRepository.class);
        model = new DefaultSetupProjectModel(eventBus, repo);
    }

    @Test
    public void getCourseNames_ReturnsCourseNames_FromRepository() {
        when(repo.findAll()).thenReturn(dummyCourses());

        assertThat(model.getCourseNames(), is(new String[] {"C1", "C2"}));
    }

    @Test
    public void getAssignmentNamesFor_ReturnsAssignmentNames_FromRepository() {
        when(repo.find("C1")).thenReturn(dummyC1Course());

        assertThat(model.getAssignmentNamesFor("C1"), is(new String[] {"C1A1", "C1A2"}));
    }

    @Test
    public void acceptDialog_RequestsDialogClose_WhenCalled() {
        EventListener listener = mock(EventListener.class);
        model.whenCloseDialogRequested(listener);

        model.acceptDialog("SomeCourse", "SomeAssignment");

        verify(listener).onEvent();
    }

    @Test
    public void cancelDialog_RequestsDialogClose_WhenCalled() {
        EventListener listener = mock(EventListener.class);
        model.whenCloseDialogRequested(listener);

        model.cancelDialog();

        verify(listener).onEvent();
    }

    @Test
    public void cancelDialog_SendsSetupProjectCanceledOnEventBus() {
        EventBusCaptor<SetupProjectCanceledEvent> captor = new EventBusCaptor<SetupProjectCanceledEvent>(SetupProjectCanceledEvent.class);
        eventBus.register(captor);

        model.cancelDialog();

        captor.assertCapture();
    }

    @Test
    public void acceptDialog_SendsSetupProjectFinishedOnEventBus() {
        EventBusCaptor<SetupProjectCompletedEvent> captor = new EventBusCaptor<SetupProjectCompletedEvent>(SetupProjectCompletedEvent.class);
        eventBus.register(captor);

        model.acceptDialog("SomeCourse", "SomeAssignment");

        captor.assertCapture();
        assertThat(captor.event, is(notNullValue()));
        assertThat(captor.event.getCourseName(), is("SomeCourse"));
        assertThat(captor.event.getAssignmentName(), is("SomeAssignment"));
    }

    @Test
    public void requestsDialogShow_WhenSetupProjectRequestArrivesOnEventBus() {
        EventListener listener = mock(EventListener.class);
        model.whenShowDialogRequested(listener);

        EventBus e = new EventBus();
        e.register(model);
        e.post(new SetupProjectRequestEvent());

        verify(listener).onEvent();
    }

    private List<Course> dummyCourses() {
        List<Assignment> c2ass = new ArrayList<Assignment>();
        c2ass.add(new Assignment("C2A1"));
        c2ass.add(new Assignment("C2A2"));

        List<Course> courses = new ArrayList<Course>();
        courses.add(dummyC1Course());
        courses.add(new Course("C2", c2ass));

        return courses;
    }

    private Course dummyC1Course() {
        List<Assignment> c1ass = new ArrayList<Assignment>();
        c1ass.add(new Assignment("C1A1"));
        c1ass.add(new Assignment("C1A2"));
        return new Course("C1", c1ass);
    }
}
