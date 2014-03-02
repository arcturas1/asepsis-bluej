package asepsis.bluej.gui.main;

import asepsis.bluej.gui.event.EventListener;

public interface MainModel {
    void whenSetupChanges(EventListener listener);
    void whenEnableGuiRequested(EventListener listener);
    void whenDisableGuiRequested(EventListener listener);

    boolean isSetup();

    void setupProject();

}
