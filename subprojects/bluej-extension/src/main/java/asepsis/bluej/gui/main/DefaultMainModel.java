package asepsis.bluej.gui.main;

import asepsis.bluej.gui.event.EventDispatcher;
import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.eventbus.ProjectClosedEvent;
import asepsis.bluej.gui.eventbus.ProjectOpenedEvent;
import asepsis.bluej.gui.eventbus.SetupProjectCompletedEvent;
import asepsis.bluej.gui.eventbus.SetupProjectRequestEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class DefaultMainModel implements MainModel {
    private boolean setup;
    private EventDispatcher setupChanged = new EventDispatcher();
    private EventDispatcher enableGuiRequest = new EventDispatcher();
    private EventDispatcher disableGuiRequest = new EventDispatcher();

    private EventBus eventBus;

    public DefaultMainModel(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void whenSetupChanges(EventListener listener) {
        setupChanged.heardBy(listener);
    }

    @Override
    public void whenEnableGuiRequested(EventListener listener) {
        enableGuiRequest.heardBy(listener);
    }

    @Override
    public void whenDisableGuiRequested(EventListener listener) {
        disableGuiRequest.heardBy(listener);
    }

    public boolean isSetup() {
        return setup;
    }

    @Override
    public void setupProject() {
        eventBus.post(new SetupProjectRequestEvent());
    }

    @Subscribe
    public void onSetupProjectCompleted(SetupProjectCompletedEvent info) {
        setSetup(true);
    }

    @Subscribe
    public void onProjectOpened(ProjectOpenedEvent ignored) {
        enableGuiRequest.tellListeners();
    }

    @Subscribe
    public void onProjectClosed(ProjectClosedEvent ignored) {
        disableGuiRequest.tellListeners();
    }

    private void setSetup(boolean newValue) {
        setup = newValue;
        setupChanged.tellListeners();
    }
}
