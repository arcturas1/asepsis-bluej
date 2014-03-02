package asepsis.bluej.test.gui.main;

import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.main.MainModel;
import asepsis.bluej.gui.main.MainView;
import asepsis.bluej.gui.main.MainPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {
    private MainView view;
    private MainModel model;

    @Before
    public void setUp() throws Exception {
        view = mock(MainView.class);
        model = mock(MainModel.class);
        new MainPresenter(view, model);
    }

    @Test
    public void NotifiesModel_WhenViewRequestsProjectSetup() {
        // Capture presenters view listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(view).whenProjectSetupRequested(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();
        verify(model).setupProject();
    }

    @Test
    public void NotifiesView_WhenModelChangesSetup() {
        // Capture presenters model listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(model).whenSetupChanges(captor.capture());
        EventListener listener = captor.getValue();

        when(model.isSetup()).thenReturn(true);
        listener.onEvent();
        verify(view).onSetupChange(true);
    }

    @Test
    public void enablesView_WhenModelRequestsGuiEnable() {
        // Capture presenters model listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(model).whenEnableGuiRequested(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();

        verify(view).setEnable(true);
    }

    @Test
    public void disablesView_WhenModelRequestsGuiDisable() {
        // Capture presenters model listener
        ArgumentCaptor<EventListener> captor = ArgumentCaptor.forClass(EventListener.class);
        verify(model).whenDisableGuiRequested(captor.capture());
        EventListener listener = captor.getValue();

        listener.onEvent();

        verify(view).setEnable(false);
    }
}
