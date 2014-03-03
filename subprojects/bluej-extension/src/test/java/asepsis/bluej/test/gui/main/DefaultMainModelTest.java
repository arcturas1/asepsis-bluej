package asepsis.bluej.test.gui.main;

import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.eventbus.ProjectClosedEvent;
import asepsis.bluej.gui.eventbus.ProjectOpenedEvent;
import asepsis.bluej.gui.eventbus.SetupProjectCompletedEvent;
import asepsis.bluej.gui.eventbus.SetupProjectRequestEvent;
import asepsis.bluej.gui.main.DefaultMainModel;
import asepsis.bluej.test.util.EventBusCaptor;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DefaultMainModelTest {
    private DefaultMainModel model;
    private EventBus eventBus;

    @Before
    public void setUp() throws Exception {
        eventBus = new EventBus();
        model = new DefaultMainModel(eventBus);
        eventBus.register(model);
    }

    @Test
    public void isSetup_ReturnsTrue_WhenSetupProjectCompletedArrivesOnEventBus() {
        eventBus.post(new SetupProjectCompletedEvent("SomeCourse", "SomeAssignment"));

        assertThat(model.isSetup(), is(true));
    }

    @Test
    public void setupProject_SendsSetupProjectRequestOnEventBus_WhenCalled() {
        EventBusCaptor<SetupProjectRequestEvent> captor = new EventBusCaptor<SetupProjectRequestEvent>(SetupProjectRequestEvent.class);
        eventBus.register(captor);

        model.setupProject();

        captor.assertCapture();
    }

    @Test
    public void notifiesListeners_WhenSetupChanges() {
        EventListener listener = mock(EventListener.class);
        model.whenSetupChanges(listener);

        model.onSetupProjectCompleted(new SetupProjectCompletedEvent("SomeCourse", "SomeName"));

        verify(listener).onEvent();
    }

    @Test
    public void requestsGuiEnable_WhenProjectOpenedArrivesOnEventBus() {
        EventListener listener = mock(EventListener.class);
        model.whenEnableGuiRequested(listener);

        eventBus.post(new ProjectOpenedEvent());

        verify(listener).onEvent();
    }

    @Test
    public void requestsGuiDisable_WhenProjectClosedArrivesOnEventBus() {
        EventListener listener = mock(EventListener.class);
        model.whenDisableGuiRequested(listener);

        eventBus.post(new ProjectClosedEvent());

        verify(listener).onEvent();
    }

    @Test
    public void resetsProjectSetupStatus_WhenProjectClosedArrivesOnEventBus() {
        eventBus.post(new SetupProjectCompletedEvent("SomeCourse", "SomeAssignment"));
        eventBus.post(new ProjectClosedEvent());

        assertThat(model.isSetup(), is(false));
    }
}