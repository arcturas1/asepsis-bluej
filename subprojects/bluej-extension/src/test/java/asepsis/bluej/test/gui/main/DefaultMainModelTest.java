package asepsis.bluej.test.gui.main;

import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.eventbus.SetupProjectCompleted;
import asepsis.bluej.gui.eventbus.SetupProjectRequest;
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
        eventBus.post(new SetupProjectCompleted("SomeCourse", "SomeAssignment"));

        assertThat(model.isSetup(), is(true));
    }

    @Test
    public void setupProject_SendsSetupProjectRequestOnEventBus_WhenCalled() {
        EventBusCaptor<SetupProjectRequest> captor = new EventBusCaptor<SetupProjectRequest>(SetupProjectRequest.class);
        eventBus.register(captor);

        model.setupProject();

        captor.assertCapture();
    }

    @Test
    public void notifiesListeners_WhenSetupChanges() {
        EventListener listener = mock(EventListener.class);
        model.whenSetupChanges(listener);

        model.onSetupProjectCompleted(new SetupProjectCompleted("SomeCourse", "SomeName"));

        verify(listener).onEvent();
    }
}