package asepsis.bluej.gui.main;

import asepsis.bluej.gui.event.EventListener;

public interface MainModel {
    void whenSetupChanges(EventListener e);

    boolean isSetup();

    void setupProject();
}
