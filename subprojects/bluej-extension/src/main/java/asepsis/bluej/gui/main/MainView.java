package asepsis.bluej.gui.main;

import asepsis.bluej.gui.event.EventListener;

public interface MainView {
    void whenProjectSetupRequested(EventListener e);

    void onSetupChange(boolean isSetup);

    void setEnable(boolean enabled);
}
