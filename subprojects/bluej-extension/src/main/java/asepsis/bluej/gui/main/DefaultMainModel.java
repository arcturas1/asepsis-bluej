package asepsis.bluej.gui.main;

import asepsis.bluej.gui.event.EventDispatcher;
import asepsis.bluej.gui.event.EventListener;
import asepsis.bluej.gui.eventbus.SetupProjectCompleted;
import asepsis.bluej.gui.eventbus.SetupProjectRequest;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class DefaultMainModel implements MainModel {
    private boolean setup;
    private EventDispatcher setupChanged = new EventDispatcher();
    private EventBus eventBus;

    public DefaultMainModel(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void whenSetupChanges(EventListener listener) {
        setupChanged.heardBy(listener);
    }

    public boolean isSetup() {
        return setup;
    }

    @Override
    public void setupProject() {
        eventBus.post(new SetupProjectRequest());
    }

    @Subscribe
    public void onSetupProjectCompleted(SetupProjectCompleted info) {
        setSetup(true);
    }

    private void setSetup(boolean newValue) {
        setup = newValue;
        setupChanged.tellListeners();
    }
}
